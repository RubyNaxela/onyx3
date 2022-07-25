package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Color definitions.
 */
public final class Colors {

    /**
     * The fully transparent color.
     */
    public static Color TRANSPARENT = new Color(0, 0, 0, 0);
    /**
     * The color used in macOS for the window close button.
     */
    public static Color MACOS_RED = new Color(254, 82, 74);
    /**
     * The color used in macOS for the window minimize button.
     */
    public static Color MACOS_YELLOW = new Color(255, 190, 51);
    public static Color LOGO_LIGHT_GRAY = new Color(224, 224, 224);
    public static Color LOGO_LIGHT_GRAY_SHADE = new Color(160, 160, 160);
    public static Color LOGO_DARK_GRAY = new Color(32, 32, 32);

    private Colors() {
    }

    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Color withAlpha(@NotNull Color color, @FloatRange(from = 0, to = 1) float alpha) {
        return new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);
    }
}
