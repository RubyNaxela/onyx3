package com.rubynaxela.onyx.gui.chart;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public enum Charts {

    EXPENSES_BREAKDOWN(RingChart::createExpensesBreakdownChart),
    REVENUES_BREAKDOWN(BarChart::createRevenuesBreakdownChart);

    public final Function<Dimension, BufferedImage> builder;

    Charts(@NotNull Function<Dimension, BufferedImage> builder) {
        this.builder = builder;
    }
}
