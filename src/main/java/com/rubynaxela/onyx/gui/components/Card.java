package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

    private final int padding;

    public Card(int padding) {
        setBackground(Colors.TRANSPARENT);
        this.padding = padding;
    }

    public Card() {
        this(16);
    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        final var insets = super.getInsets();
        final var graphics = (Graphics2D) g.create();
        graphics.setColor(UIManager.getColor("Desktop.background").brighter());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(insets.left, insets.top, getWidth() - insets.left - insets.right,
                               getHeight() - insets.top - insets.bottom, 16, 16);
        graphics.dispose();
        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        final var insets = super.getInsets();
        return new Insets(insets.top + padding, insets.left + padding, insets.bottom + padding, insets.right + padding);
    }
}
