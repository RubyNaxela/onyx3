package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Colors;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Icon extends JLabel {

    public Icon(@NotNull IconCode icon, float size) {
        super(IconFontSwing.buildIcon(icon, size, Colors.LOGO_LIGHT_GRAY));
    }

    public Icon(@NotNull IconCode icon) {
        this(icon, 24f);
    }
}
