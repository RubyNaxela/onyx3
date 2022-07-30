package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.gui.chart.Charts;
import com.rubynaxela.onyx.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class ChartPanel extends JPanel {

    private Function<Dimension, BufferedImage> chartBuilder;

    public ChartPanel() {
        setBackground(Colors.TRANSPARENT);
    }

    public void draw(@NotNull Charts chart) {
        this.chartBuilder = chart.builder;
        repaint();
    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        super.paintComponent(g);
        final var size = getBounds().getSize();
        if (chartBuilder != null) g.drawImage(chartBuilder.apply(size), 0, 0, null);
    }
}
