package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Colors;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Icon extends JLabel {

    /**
     * Constructs an {@code Icon} with an {@link ImageIcon} created from the specified {@link ImageIcon}, size and color.
     *
     * @param icon  the icon to be used
     * @param size  the icon size
     * @param color the icon color
     */
    public Icon(@NotNull IconCode icon, float size, @NotNull Color color) {
        super(IconFontSwing.buildIcon(icon, size, color));
    }

    /**
     * Constructs an {@code Icon} with an {@link ImageIcon} created from the specified
     * {@link ImageIcon} and size. The icon's color is {@link Colors#LOGO_LIGHT_GRAY}.
     *
     * @param icon the icon to be used
     * @param size the icon size
     */
    public Icon(@NotNull IconCode icon, float size) {
        this(icon, size, Colors.LOGO_LIGHT_GRAY);
    }

    /**
     * Constructs an {@code Icon} with an {@link ImageIcon} created from the specified
     * {@link IconCode}. The icon's size is 24pt and the color is {@link Colors#LOGO_LIGHT_GRAY}.
     *
     * @param icon the icon to be used
     */
    public Icon(@NotNull IconCode icon) {
        this(icon, 24f);
    }
}
