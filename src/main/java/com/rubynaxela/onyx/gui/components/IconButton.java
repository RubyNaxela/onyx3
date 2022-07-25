package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.util.ComponentUtils;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class IconButton extends JButton {

    public IconButton(@NotNull String text, @NotNull IconCode icon, @NotNull Insets padding, @NotNull Insets border) {
        super(" " + text, IconFontSwing.buildIcon(icon, 24f, Colors.LOGO_LIGHT_GRAY));
        setBorder(new CompoundBorder(new MatteBorder(border, UIManager.getColor("Button.focusedBorderColor")),
                                     new EmptyBorder(padding)));
        ComponentUtils.setFontSize(this, 14f);
    }

    public IconButton(@NotNull String text, @NotNull IconCode icon, @NotNull Insets border) {
        this(text, icon, ComponentUtils.ZERO_INSETS, border);
    }
}
