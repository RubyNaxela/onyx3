package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntFunction;

public final class CommonUtils {

    private CommonUtils() {
    }

    /**
     * Checks that the specified object reference is not {@code null}, and casts it
     * to the specified type, in which differs from {@link Objects#requireNonNull}.
     *
     * @param ref the object reference to check for nullity and cast to the specified type
     * @param <T> the target type of the reference
     * @return {@code ref} casted to {@code <T>}, if not null
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public static <T> T requireNonNull(Object ref) {
        if (ref == null) throw new NullPointerException();
        return (T) ref;
    }

    /**
     * Returns a sorted copy of the specified array. The array is sorted using the specified comparator.
     *
     * @param array      the array to be sorted
     * @param comparator the comparator used to sort the array
     * @param <T>        the type of the array
     * @return a sorted copy of the specified array
     */
    public static <T> T[] sortedCopy(@NotNull T[] array, @NotNull IntFunction<T[]> generator,
                                     @NotNull Comparator<T> comparator) {
        final T[] sortedArray = generator.apply(array.length);
        System.arraycopy(array, 0, sortedArray, 0, array.length);
        Arrays.sort(sortedArray, comparator);
        return sortedArray;
    }

    /**
     * Creates and initializes a new {@code Insets} object with the specified bottom inset value.
     *
     * @param value the bottom inset value
     * @return a new {@code Insets} object with the specified bottom inset value
     */
    public static Insets insetBottom(int value) {
        return new Insets(0, 0, value, 0);
    }
}
