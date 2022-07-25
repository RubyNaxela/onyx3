package com.rubynaxela.onyx.util;

public final class MathUtils {

    private MathUtils() {
    }

    public static float clampf(float x, float min, float max) {
        return Math.max(min, Math.min(x, max));
    }
}
