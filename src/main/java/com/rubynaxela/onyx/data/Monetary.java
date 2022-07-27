package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.io.I18n;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

public class Monetary implements Comparable<Monetary> {

    /**
     * The zero amount (0.00 PLN).
     */
    public static final Monetary ZERO = new Monetary(0);

    private long units;
    private short decimalPlaces;

    private Monetary(long units, short decimalPlaces) {
        this.units = units;
        this.decimalPlaces = decimalPlaces;
        normalize();
    }

    private Monetary(double value) {
        this((long) value, (short) Math.round((value - (long) value) * 100));
    }

    /**
     * Creates a new {@code Monetary} object representing the specified amount of money.
     *
     * @param amount the amount of money
     * @return a {@code Monetary} object representing the amount of money
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Monetary valueOf(double amount) {
        return new Monetary(amount);
    }

    /**
     * Parses the argument as {@code double} and creates a new {@code Monetary}
     * object representing the amount of money equal to the parsed number.
     *
     * @param amount the amount of money to parse
     * @return a {@code Monetary} object representing the amount of money
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Monetary valueOf(@NotNull String amount) {
        return new Monetary(Double.parseDouble(amount));
    }

    /**
     * Adds two {@code Monetary} objects and returns the result.
     *
     * @param a the first amount
     * @param b the second amount
     * @return the sum of the specified amounts
     */
    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Monetary add(@NotNull Monetary a, @NotNull Monetary b) {
        return new Monetary(a.units + b.units, (short) (a.decimalPlaces + b.decimalPlaces));
    }

    /**
     * Sums the stream of {@code Monetary} objects and returns the result.
     *
     * @param amounts the stream of amounts
     * @return the sum of the amounts
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Monetary sum(@NotNull Stream<Monetary> amounts) {
        return amounts.reduce(ZERO, Monetary::add);
    }

    /**
     * Sums the list of {@code Monetary} objects and returns the result.
     *
     * @param amounts the list of amounts
     * @return the sum of the amounts
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static Monetary sum(@NotNull List<Monetary> amounts) {
        return sum(amounts.stream());
    }

    @NotNull
    public static String getSymbol() {
        return Currency.getInstance(I18n.getLocale()).getCurrencyCode();
    }

    private void normalize() {
        while (decimalPlaces >= 100) {
            decimalPlaces -= 100;
            units++;
        }
        while (decimalPlaces <= -100) {
            decimalPlaces += 100;
            units--;
        }
    }

    /**
     * Converts this {@code Monetary} object to a {@code double} value.
     *
     * @return a {@code double} value representing the amount of this {@code Monetary} object
     */
    public double toDouble() {
        return units + 0.01 * decimalPlaces;
    }

    /**
     * Returns this amount with the following format:<br>{@code sX+.XX $}, where:
     * <ul>
     *     <li>{@code s} is the character {@code '-'} ({@code \u005cu002d}) if this amount is negative</li>
     *     <li>{@code X} are decimal digits (the {@code +} means one or more)</li>
     *     <li>{@code $} is the currenct code returned by {@link #getSymbol}</li>
     * </ul>
     * This method is equivalent to calling<br>{@code toString(" PLN")} on this object.
     *
     * @return the formatted amount
     */
    @Override
    public String toString() {
        return toString(" " + getSymbol());
    }

    /**
     * Returns this amount with the following format:<br>{@code sX+.XX$}, where:
     * <ul>
     *     <li>{@code s} is the character {@code '-'} ({@code \u005cu002d}) if this amount is negative</li>
     *     <li>{@code X} are decimal digits (the {@code +} means one or more)</li>
     *     <li>{@code $} is the {@code suffix} argument</li>
     * </ul>
     *
     * @param suffix the suffix appended to the numerical value of this amount
     * @return the formatted amount
     */
    public String toString(@NotNull String suffix) {
        return String.format("%s%d.%02d%s", units < 0 || decimalPlaces < 0 ? "-" : "",
                             Math.abs(units), Math.abs(decimalPlaces), suffix);
    }

    /**
     * Compares this amount with the specified amount numerically.
     *
     * @param other the {@code Monetary} to be compared
     * @return {@code 0} if this {@code Monetary} is numerically equal to {@code other};
     * a negative integer if this {@code Monetary} is numerically less than {@code other};
     * and a positive integer if this {@code Monetary} is numerically greater than {@code other}.
     */
    @Override
    public int compareTo(@NotNull Monetary other) {
        return Double.compare(toDouble(), other.toDouble());
    }
}
