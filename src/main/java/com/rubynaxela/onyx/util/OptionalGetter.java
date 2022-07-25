package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public class OptionalGetter<T> {

    private final T value;

    private OptionalGetter(T value) {
        this.value = value;
    }

    public static <T> OptionalGetter<T> of(T value) {
        return new OptionalGetter<>(value);
    }

    public <R> Optional<R> get(@NotNull Function<T, R> getter) {
        if (value == null) return Optional.empty();
        else return Optional.of(getter.apply(value));
    }

    public <R> Optional<String> getString(@NotNull Function<T, R> getter) {
        if (value == null) return Optional.empty();
        else return Optional.of(getter.apply(value).toString());
    }
}
