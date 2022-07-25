package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Colors;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Icon extends JLabel {

    public Icon(@NotNull IconCode icon, float size, @NotNull Color color) {
        super(IconFontSwing.buildIcon(icon, size, color));
    }

    public Icon(@NotNull IconCode icon, float size) {
        this(icon, size, Colors.LOGO_LIGHT_GRAY);
    }

    public Icon(@NotNull IconCode icon) {
        this(icon, 24f);
    }
}
