package com.rubynaxela.onyx.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class Operation {

    private final Date date;
    private final int ordinalInWeek;
    private final String contractor;
    private final List<Fragment> fragments;

    public Operation(@NotNull Date date, int ordinalInWeek,
                     @NotNull String contractor, @NotNull List<@NotNull Fragment> fragments) {
        this.date = date;
        this.ordinalInWeek = ordinalInWeek;
        this.contractor = contractor;
        this.fragments = fragments;
    }

    public static Operation from(@NotNull Raw operation) {
        return new Operation(Date.valueOf(operation.date), operation.ordinalInWeek, operation.contractor,
                             new ArrayList<>(Arrays.stream(operation.fragments).map(Fragment::from).toList()));
    }

    public Raw raw() {
        return new Raw(date.toString(), ordinalInWeek, contractor,
                       fragments.stream().map(Fragment::raw).toArray(Raw.Fragment[]::new));
    }

    @NotNull
    public Date getDate() {
        return date;
    }

    @NotNull
    public String getContractor() {
        return contractor;
    }

    @NotNull
    public List<Fragment> getFragments() {
        return Collections.unmodifiableList(fragments);
    }

    public Monetary wGetTotalAmount() {
        return Monetary.sum(fragments.stream().flatMap(f -> f.branches.stream().map(Branch::getAmount)));
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

    public static class Fragment {

        private final Type type;
        private final String description;
        private final List<Branch> branches;

        public Fragment(@NotNull Type type, @NotNull String description, @NotNull List<@NotNull Branch> branches) {
            this.type = type;
            this.description = description;
            this.branches = branches;
        }

        public static Fragment from(@NotNull Raw.Fragment fragment) {
            return new Fragment(Type.valueOf(fragment.type.toUpperCase()), fragment.description,
                                new ArrayList<>(Arrays.stream(fragment.branches).map(Branch::from).toList()));
        }

        public Raw.Fragment raw() {
            return new Raw.Fragment(type.toString().toLowerCase(), description,
                                    branches.stream().map(Branch::raw).toArray(Raw.Branch[]::new));
        }

        @NotNull
        public Type getType() {
            return type;
        }

        @NotNull
        public String getDescription() {
            return description;
        }

        @NotNull
        public List<Branch> getBranches() {
            return Collections.unmodifiableList(branches);
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

            @JsonProperty("t")
            public String type;
            @JsonProperty("d")
            public String description;
            @JsonProperty("b")
            public Branch[] branches;

            public Fragment() {
            }

            public Fragment(@NotNull String type, @NotNull String description, @NotNull Branch @NotNull [] branches) {
                this.type = type;
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
