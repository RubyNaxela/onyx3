package com.rubynaxela.onyx.gui.chart;

import com.rubynaxela.onyx.data.Database;
import com.rubynaxela.onyx.gui.components.Card;
import org.jetbrains.annotations.NotNull;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.SortOrder;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.image.BufferedImage;

class RingChart {

    static BufferedImage createExpensesBreakdownChart(@NotNull Dimension size) {

        final var dataset = new DefaultPieDataset<String>();
        final var plot = new RingPlot(dataset);

        Database.INSTANCE.wGetExpensesByCategory().forEach((c, a) -> {
            final String key = c.toString();
            dataset.setValue(key, a.absolute().toDouble());
            plot.setSectionPaint(key, c.color);
        });
        dataset.sortByValues(SortOrder.DESCENDING);

        plot.setInsets(RectangleInsets.ZERO_INSETS);
        plot.setSectionDepth(0.25f);
        plot.setBackgroundPaint(Card.DEFAULT_COLOR);
        plot.setCenterText(null);
        plot.setLabelGenerator(null);
        plot.setOutlineVisible(false);
        plot.setShadowGenerator(null);
        plot.setSeparatorsVisible(false);
        plot.setShadowPaint(null);
        plot.setSectionOutlinesVisible(false);
        plot.setOuterSeparatorExtension(0);
        plot.setInnerSeparatorExtension(0);

        return new JFreeChart(null, null, plot, false).createBufferedImage(size.width, size.height);
    }
}
