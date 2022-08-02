package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.jadeite.awt.AWTConstants;
import com.rubynaxela.onyx.util.ComponentUtils;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class IconButton extends JButton {

    private IconCode icon;
    private float iconSize;
    private Color iconColor;

    public IconButton(@NotNull String text, @NotNull IconCode icon, @NotNull Insets padding, @NotNull Insets border) {
        super(" " + text);
        this.icon = icon;
        this.iconSize = 24f;
        this.iconColor = getForeground();
        setIcon(IconFontSwing.buildIcon(icon, iconSize, iconColor));
        setBorder(new CompoundBorder(new MatteBorder(border, UIManager.getColor("Button.focusedBorderColor")),
                                     new EmptyBorder(padding)));
        ComponentUtils.setFontSize(this, 14f);
    }

    public IconButton(@NotNull String text, @NotNull IconCode icon, @NotNull Insets border) {
        this(text, icon, AWTConstants.ZERO_INSETS, border);
    }

    /**
     * Sets the icon. If {@code icon} is {@code null}, the icon will be removed.
     *
     * @param icon the new icon
     */
    public void setIcon(@Nullable IconCode icon) {
        this.icon = icon;
        setIcon(icon != null ? IconFontSwing.buildIcon(icon, iconSize, iconColor) : null);
    }

    /**
     * Sets the icon size.
     *
     * @param size the new icon size
     */
    public void setIconSize(float size) {
        this.iconSize = size;
        if (icon != null) setIcon(IconFontSwing.buildIcon(icon, size, iconColor));
    }

    /**
     * Sets the icon color. If {@code null}, the color returned by {@link #getForeground} is used.
     *
     * @param iconColor the desired icon color
     */
    public void setIconColor(@Nullable Color iconColor) {
        this.iconColor = iconColor != null ? iconColor : getForeground();
        if (icon != null) setIcon(IconFontSwing.buildIcon(icon, iconSize, iconColor));
    }
}
