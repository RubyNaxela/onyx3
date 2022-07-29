package com.rubynaxela.onyx.gui.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class SizeController extends JPanel {

    public SizeController(@NotNull Dimension size) {
        setPreferredSize(size);
    }
}
