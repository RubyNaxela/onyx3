package com.rubynaxela.onyx.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubynaxela.onyx.io.S31N;
import com.rubynaxela.onyx.util.CachedGetter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Database {

    private static final File DATABASE_FILE = new File("database.json");

    public static final Database INSTANCE = from(S31N.deserialize(Raw.class, DATABASE_FILE));
    private final Vector<Operation> operations;
    private final Vector<PendingOperation> pendingOperations;
    private final CachedGetter<Monetary> operationsBalance, totalCash, pendingOperationsBalance,
            accountBalance, availableBalance, totalBalance;
    private Monetary initialAmount, savings;
    private int modCount;

    private Database(@NotNull Monetary initialAmount, @NotNull Monetary savings,
                     @NotNull Vector<Operation> operations, @NotNull Vector<PendingOperation> pendingOperations) {
        this.initialAmount = initialAmount;
        this.savings = savings;
        this.operations = operations;
        this.pendingOperations = pendingOperations;
        this.modCount = 0;

        this.operationsBalance = new CachedGetter<>(() -> Monetary.sum(operations.stream().map(Operation::wGetTotalAmount)), 0);
        this.totalCash = new CachedGetter<>(() -> Monetary.sum(operations.stream().flatMap(o -> o.getFragments().stream())
                                                                          .filter(Operation.Fragment::wIsCashOperation)
                                                                          .map(Operation.Fragment::wGetTotalAmount)), 0);
        this.pendingOperationsBalance = new CachedGetter<>(() -> Monetary.sum(pendingOperations.stream()
                                                                                                .map(PendingOperation::getAmount)), 0);
        this.accountBalance = new CachedGetter<>(() -> Monetary.subtract(Monetary.add(initialAmount, wGetOperationsBalance()),
                                                                          Monetary.add(savings, wGetTotalCash())), 0);
        this.availableBalance = new CachedGetter<>(() -> Monetary.add(initialAmount, wGetOperationsBalance()), 0);
        this.totalBalance = new CachedGetter<>(() -> Monetary.add(wGetAvailableBalance(), wGetPendingOperationsBalance()), 0);
    }

    public static Database from(@NotNull Database.Raw database) {
        return new Database(Monetary.valueOf(database.initialAmount), Monetary.valueOf(database.savings),
                            new Vector<>(Arrays.stream(database.operations).map(Operation::from).toList()),
                            new Vector<>(Arrays.stream(database.pendingOperations).map(PendingOperation::from).toList()));
    }

    private void update() {
        modCount++;
        S31N.serialize(raw(), DATABASE_FILE);
    }

    public Raw raw() {
        return new Raw(initialAmount.toDouble(), savings.toDouble(),
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

    private Monetary wGetOperationsBalance() {
        return operationsBalance.get(modCount);
    }

    public Monetary wGetTotalCash() {
        return totalCash.get(modCount);
    }

    public Monetary wGetPendingOperationsBalance() {
        return pendingOperationsBalance.get(modCount);
    }

    public Monetary wGetAccountBalance() {
        return accountBalance.get(modCount);
    }

    public Monetary wGetAvailableBalance() {
        return availableBalance.get(modCount);
    }

    public Monetary wGetTotalBalance() {
        return totalBalance.get(modCount);
    }

    static class Raw {

        @JsonProperty("initial_amount")
        public double initialAmount;
        public double savings;
        public Operation.Raw[] operations;
        @JsonProperty("pending_operations")
        public PendingOperation.Raw[] pendingOperations;

        public Raw() {
        }

        public Raw(double initialAmount, double savings, @NotNull Operation.Raw @NotNull [] operations,
                   @NotNull PendingOperation.Raw @NotNull [] pendingOperations) {
            this.initialAmount = initialAmount;
            this.savings = savings;
            this.operations = operations;
            this.pendingOperations = pendingOperations;
        }
    }
}
