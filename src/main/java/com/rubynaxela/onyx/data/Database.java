package com.rubynaxela.onyx.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubynaxela.onyx.io.S31N;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

public class Database {

    private static final File DATABASE_FILE = new File("database.json");

    public static final Database INSTANCE = from(S31N.deserialize(Raw.class, DATABASE_FILE));
    private final Vector<Operation> operations;
    private final Vector<PendingOperation> pendingOperations;
    private Monetary initialAmount, cash, savings;

    private Database(@NotNull Monetary initialAmount, @NotNull Monetary cash, @NotNull Monetary savings,
                     @NotNull Vector<Operation> operations, @NotNull Vector<PendingOperation> pendingOperations) {
        this.initialAmount = initialAmount;
        this.cash = cash;
        this.savings = savings;
        this.operations = operations;
        this.pendingOperations = pendingOperations;
    }

    public static Database from(@NotNull Database.Raw database) {
        return new Database(Monetary.valueOf(database.initialAmount),
                            Monetary.valueOf(database.cash), Monetary.valueOf(database.savings),
                            new Vector<>(Arrays.stream(database.operations).map(Operation::from).toList()),
                            new Vector<>(Arrays.stream(database.pendingOperations).map(PendingOperation::from).toList()));
    }

    private void update() {
        S31N.serialize(raw(), DATABASE_FILE);
    }

    public Raw raw() {
        return new Raw(initialAmount.toDouble(), cash.toDouble(), savings.toDouble(),
                       operations.stream().map(Operation::raw).toArray(Operation.Raw[]::new),
                       pendingOperations.stream().map(PendingOperation::raw).toArray(PendingOperation.Raw[]::new));
    }

    public Monetary getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(@NotNull Monetary initialAmount) {
        this.initialAmount = initialAmount;
        update();
    }

    public Monetary getCash() {
        return cash;
    }

    public void setCash(@NotNull Monetary cash) {
        this.cash = cash;
        update();
    }

    public Monetary getSavings() {
        return savings;
    }

    public void setSavings(@NotNull Monetary savings) {
        this.savings = savings;
        update();
    }

    public Vector<Operation> getOperations() {
        return operations;
    }

    public void addOperation(@NotNull Operation operation) {
        operations.add(operation);
        operations.sort(Comparator.reverseOrder());
        update();
    }

    public void removeOperation(@NotNull Operation operation) {
        operations.remove(operation);
        update();
    }

    public void replaceOperation(@NotNull Operation oldOperation, @NotNull Operation newOperation) {
        operations.remove(oldOperation);
        operations.add(newOperation);
        update();
    }

    public Vector<PendingOperation> getPendingOperations() {
        return pendingOperations;
    }

    public void addPendingOperation(@NotNull PendingOperation operation) {
        pendingOperations.add(operation);
        update();
    }

    public void removePendingOperation(@NotNull PendingOperation operation) {
        pendingOperations.remove(operation);
        update();
    }

    public void replacePendingOperation(@NotNull PendingOperation oldOperation, @NotNull PendingOperation newOperation) {
        pendingOperations.remove(oldOperation);
        pendingOperations.add(newOperation);
        update();
    }

    public Monetary wGetOperationsBalance() {
        return Monetary.sum(operations.stream().map(Operation::wGetTotalAmount));
    }

    public Monetary wGetPendingOperationsBalance() {
        return Monetary.sum(pendingOperations.stream().map(PendingOperation::getAmount));
    }

    static class Raw {

        @JsonProperty("initial_amount")
        public double initialAmount;
        public double cash, savings;
        public Operation.Raw[] operations;
        @JsonProperty("pending_operations")
        public PendingOperation.Raw[] pendingOperations;

        public Raw() {
        }

        public Raw(double initialAmount, double cash, double savings,
                   @NotNull Operation.Raw @NotNull [] operations, @NotNull PendingOperation.Raw @NotNull [] pendingOperations) {
            this.initialAmount = initialAmount;
            this.cash = cash;
            this.savings = savings;
            this.operations = operations;
            this.pendingOperations = pendingOperations;
        }
    }
}
