package com.rubynaxela.onyx.gui.animation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("ClassCanBeRecord")
public class Vector3i {

    public final int x, y, z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(float x, float y, float z) {
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
    }

    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vector3i fromColor(@NotNull Color color) {
        return new Vector3i(color.getRed(), color.getGreen(), color.getBlue());
    }

    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i subtract(@NotNull Vector3i a, @NotNull Vector3i b) {
        return new Vector3i(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    @NotNull
    @Contract(pure = true, value = "-> new")
    public Color toColor() {
        return new Color(x, y, z);
    }
}
