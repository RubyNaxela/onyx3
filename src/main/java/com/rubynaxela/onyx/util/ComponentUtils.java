package com.rubynaxela.onyx.util;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class ComponentUtils {

    private ComponentUtils() {
    }

    /**
     * Sets the font style and size of the specified component.
     *
     * @param component the target component
     * @param style     the new font style
     * @param size      the new font size
     */
    public static void setFontParams(@NotNull JComponent component,
                                     @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style, float size) {
        component.setFont(component.getFont().deriveFont(style, size));
    }

    /**
     * Sets the font size of the specified component.
     *
     * @param component the target component
     * @param size      the new font size
     */
    public static void setFontSize(@NotNull JComponent component, float size) {
        component.setFont(component.getFont().deriveFont(size));
    }

    /**
     * Adds a margin around the component by adding an {@link EmptyBorder} to it. If the component already
     * has a border, a {@link CompoundBorder} is created with the previous border as the inside border.
     *
     * @param component the target component
     * @param top       the top margin size (in pixels)
     * @param right     the right margin size (in pixels)
     * @param bottom    the bottom margin size (in pixels)
     * @param left      the left margin size (in pixels)
     */
    public static void addMargin(@NotNull JComponent component, int top, int right, int bottom, int left) {
        final Border current = component.getBorder(), empty = new EmptyBorder(top, left, bottom, right);
        component.setBorder(current == null ? empty : new CompoundBorder(empty, current));
    }

    /**
     * Adds a margin around the component by adding an {@link EmptyBorder} to it. If the component already
     * has a border, a {@link CompoundBorder} is created with the previous border as the inside border.
     *
     * @param component the target component
     * @param margin    the margin size (in pixels)
     */
    public static void addMargin(@NotNull JComponent component, int margin) {
        addMargin(component, margin, margin, margin, margin);
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
