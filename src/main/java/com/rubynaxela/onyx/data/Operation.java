package com.rubynaxela.onyx.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ClassCanBeRecord")
public class Operation implements Comparable<Operation> {

    private final Date date;
    private final int ordinalInWeek;
    private final String contractor;
    private final Vector<Fragment> fragments;

    public Operation(@NotNull Date date, int ordinalInWeek,
                     @NotNull String contractor, @NotNull Vector<@NotNull Fragment> fragments) {
        this.date = date;
        this.ordinalInWeek = ordinalInWeek;
        this.contractor = contractor;
        this.fragments = fragments;
    }

    public static Operation from(@NotNull Raw operation) {
        return new Operation(Date.valueOf(operation.date), operation.ordinalInWeek, operation.contractor,
                             new Vector<>(Arrays.stream(operation.fragments).map(Fragment::from).toList()));
    }

    public Raw raw() {
        return new Raw(date.toString(), ordinalInWeek, contractor,
                       fragments.stream().map(Fragment::raw).toArray(Raw.Fragment[]::new));
    }

    @NotNull
    public Date getDate() {
        return date;
    }

    public int getOrdinalInWeek() {
        return ordinalInWeek;
    }

    @NotNull
    public String getContractor() {
        return contractor;
    }

    @NotNull
    public Vector<Fragment> getFragments() {
        return fragments;
    }

    @NotNull
    public Stream<Fragment> getSFragments() {
        return fragments.stream();
    }

    public Monetary wGetTotalAmount() {
        return Monetary.sum(fragments.stream().map(Fragment::wGetTotalAmount));
    }

    public boolean positive() {
        return wGetTotalAmount().compareTo(Monetary.ZERO) > 0;
    }

    public boolean negative() {
        return wGetTotalAmount().compareTo(Monetary.ZERO) < 0;
    }

    public String wGetId() {
        return "#" + ("" + date.getYear()).substring(2) +
               String.format("%02d", date.getWeekOfYear()) + String.format("%02x", ordinalInWeek);
    }

    public String wGetDescription() {
        return fragments.stream().map(Fragment::getDescription).collect(Collectors.joining("\n"));
    }

    @Override
    public int compareTo(@NotNull Operation other) {
        return wGetId().compareTo(other.wGetId());
    }

    public boolean wIsInternalTransfer() {
        return fragments.get(0).branches.get(0).category == Category.INTERNAL_TRANSFER;
    }

    public boolean wIsSingleBranch() {
        return fragments.size() == 1 && fragments.get(0).branches.size() == 1;
    }

    public static class Fragment {

        private final Form form;
        private final String description;
        private final Vector<Branch> branches;

        public Fragment(@NotNull Form form, @NotNull String description, @NotNull Vector<@NotNull Branch> branches) {
            this.form = form;
            this.description = description;
            this.branches = branches;
        }

        public static Fragment from(@NotNull Raw.Fragment fragment) {
            return new Fragment(Form.valueOf(fragment.form.toUpperCase()), fragment.description,
                                new Vector<>(Arrays.stream(fragment.branches).map(Branch::from).toList()));
        }

        public Raw.Fragment raw() {
            return new Raw.Fragment(form.toString().toLowerCase(), description,
                                    branches.stream().map(Branch::raw).toArray(Raw.Branch[]::new));
        }

        @NotNull
        public Form getForm() {
            return form;
        }

        @NotNull
        public String getDescription() {
            return description;
        }

        @NotNull
        public Vector<Branch> getBranches() {
            return branches;
        }

        @NotNull
        public Stream<Branch> getSBranches() {
            return branches.stream();
        }

        public Monetary wGetTotalAmount() {
            return Monetary.sum(branches.stream().map(Branch::getAmount));
        }

        public boolean wIsCashOperation() {
            return form == Form.CASH_PAYMENT || form == Form.CASH_REVENUE;
        }

        public boolean wIsSingleBranch() {
            return branches.size() == 1;
        }
    }

    public static class Branch {

        private final Category category;
        private final Monetary amount;

        public Branch(@NotNull Category category, @NotNull Monetary amount) {
            this.category = category;
            this.amount = amount;
        }

        public static Branch from(@NotNull Raw.Branch branch) {
            return new Branch(Category.valueOf(branch.category.toUpperCase()), Monetary.valueOf(branch.amount));
        }

        public Raw.Branch raw() {
            return new Raw.Branch(category.toString().toLowerCase(), amount.toDouble());
        }

        @NotNull
        public Category getCategory() {
            return category;
        }

        @NotNull
        public Monetary getAmount() {
            return amount;
        }
    }

    @SuppressWarnings("unused")
    static class Raw {

        @JsonProperty("d")
        public String date;
        @JsonProperty("c")
        public String contractor;
        @JsonProperty("o")
        public int ordinalInWeek;
        @JsonProperty("f")
        public Fragment[] fragments;

        public Raw() {
        }

        public Raw(@NotNull String date, int ordinalInWeek,
                   @NotNull String contractor, @NotNull Fragment @NotNull [] fragments) {
            this.date = date;
            this.ordinalInWeek = ordinalInWeek;
            this.contractor = contractor;
            this.fragments = fragments;
        }

        static class Fragment {

            @JsonProperty("f")
            public String form;
            @JsonProperty("d")
            public String description;
            @JsonProperty("b")
            public Branch[] branches;

            public Fragment() {
            }

            public Fragment(@NotNull String form, @NotNull String description, @NotNull Branch @NotNull [] branches) {
                this.form = form;
                this.description = description;
                this.branches = branches;
            }
        }

        static class Branch {

            @JsonProperty("c")
            public String category;
            @JsonProperty("a")
            public double amount;

            public Branch() {
            }

            public Branch(@NotNull String category, double amount) {
                this.category = category;
                this.amount = amount;
            }
        }
    }
}
