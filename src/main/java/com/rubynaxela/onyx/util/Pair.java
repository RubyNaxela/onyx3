package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.Nullable;

public final class Pair<T, U> {

    public final T v1;
    public final U v2;

    private Pair(@Nullable T v1, @Nullable U v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public static <T, U> Pair<T, U> of(@Nullable T v1, @Nullable U v2) {
        return new Pair<>(v1, v2);
    }
}
