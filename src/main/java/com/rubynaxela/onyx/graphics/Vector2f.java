package com.rubynaxela.onyx.graphics;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ClassCanBeRecord")
public class Vector2f {

    public static final Vector2f ZERO = new Vector2f(0, 0);

    public final float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f add(@NotNull Vector2f a, @NotNull Vector2f b) {
        return new Vector2f(a.x + b.x, a.y + b.y);
    }

    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f subtract(@NotNull Vector2f a, @NotNull Vector2f b) {
        return new Vector2f(a.x - b.x, a.y - b.y);
    }
}
