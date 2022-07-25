package com.rubynaxela.onyx.data;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ClassCanBeRecord")
public class PendingOperation {

    private final String contractor, description;
    private final Monetary amount;

    public PendingOperation(@NotNull String contractor, @NotNull String description, @NotNull Monetary amount) {
        this.contractor = contractor;
        this.description = description;
        this.amount = amount;
    }

    public static PendingOperation from(@NotNull Raw operation) {
        return new PendingOperation(operation.contractor, operation.description, Monetary.valueOf(operation.amount));
    }

    public String getContractor() {
        return contractor;
    }

    public String getDescription() {
        return description;
    }

    public Monetary getAmount() {
        return amount;
    }

    public Raw raw() {
        return new Raw(contractor, description, amount.toDouble());
    }

    static class Raw {

        public String contractor, description;
        public double amount;

        public Raw() {
        }

        public Raw(@NotNull String contractor, @NotNull String description, double amount) {
            this.contractor = contractor;
            this.description = description;
            this.amount = amount;
        }
    }
}
