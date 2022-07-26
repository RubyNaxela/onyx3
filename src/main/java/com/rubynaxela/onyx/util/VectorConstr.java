package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class VectorConstr {

    private VectorConstr() {
    }

    @SafeVarargs
    public static <T> Vector<T> of(@Nullable T... values) {
        final Vector<T> result = new Vector<>(values.length);
        result.addAll(Arrays.asList(values));
        return result;
    }

    public static <T> Collector<T, ?, Vector<T>> collector() {
        return new VectorCollector<>();
    }

    private static class VectorCollector<T, R> implements Collector<T, Vector<T>, R> {

        @Override
        public Supplier<Vector<T>> supplier() {
            return Vector::new;
        }

        @Override
        public BiConsumer<Vector<T>, T> accumulator() {
            return Vector::add;
        }

        @Override
        public BinaryOperator<Vector<T>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @SuppressWarnings("unchecked")
        @Override
        public Function<Vector<T>, R> finisher() {
            return i -> (R) i;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
        }
    }
}
