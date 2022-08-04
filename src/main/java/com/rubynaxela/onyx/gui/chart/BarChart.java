package com.rubynaxela.onyx.gui.chart;

import com.rubynaxela.jadeite.awt.JColor;
import com.rubynaxela.jadeite.awt.graphics.RectangleShape;
import com.rubynaxela.jadeite.pointers.IntPointer;
import com.rubynaxela.jadeite.pointers.Pointer;
import com.rubynaxela.onyx.data.Database;
import com.rubynaxela.onyx.data.Monetary;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;
import java.util.function.Function;

class BarChart {

    static BufferedImage createRevenuesBreakdownChart(@NotNull Dimension size) {

        final var image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        final SortedMap<Double, Color> dataset = new TreeMap<>(Comparator.reverseOrder());
        final var refunds = Pointer.to(Monetary.ZERO);
        final List<JColor> refundsColors = new ArrayList<>();
        Database.INSTANCE.wGetRevenuesByCategory().forEach((c, a) -> {
            if (!c.incomesAreRedunds) dataset.put(a.toDouble(), c.color);
            else {
                refunds.set(Monetary.add(refunds.get(), a));
                refundsColors.add(c.color);
            }
        });
        if (!refunds.get().equals(Monetary.ZERO)) {
            final var refundsColor = JColor.average(refundsColors);
            dataset.put(refunds.get().toDouble(), refundsColor.withSaturation(Math.max(refundsColor.getSaturation(), 0.5f)));
        }

        if (dataset.size() > 0) {

            final Function<Double, Double> scale = Math::log1p;
            final List<RectangleShape> bars = new ArrayList<>();
            final var heightFactor = size.height / scale.apply(dataset.firstKey());
            final var gapWidth = 10d;
            final var barWidth = (size.width + gapWidth) / dataset.size() - gapWidth;
            final var i = IntPointer.to(0);
            dataset.forEach((amount, color) -> {
                final var height = (float) (scale.apply(amount) * heightFactor);
                final var bar = new RectangleShape((float) barWidth, height);
                bar.setPosition((float) (i.incrementPostfix() * (barWidth + gapWidth)), size.height - height);
                bar.setColor(color);
                bar.setCornerRadius(4);
                bars.add(bar);
            });

            bars.forEach(r -> r.draw((Graphics2D) image.getGraphics()));
        }

        return image;
    }
}
