package com.rubynaxela.onyx.gui.chart;

import com.rubynaxela.jadeite.pointers.IntPointer;
import com.rubynaxela.jadeite.swing.graphics.RectangleShape;
import com.rubynaxela.jadeite.util.Pair;
import com.rubynaxela.onyx.data.Database;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

class BarChart {

    static BufferedImage createRevenuesBreakdownChart(@NotNull Dimension size) {

        final var image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        final List<Pair<Double, Color>> dataset = new ArrayList<>();
        Database.INSTANCE.wGetRevenuesByCategory().forEach((c, a) -> dataset.add(Pair.of(a.toDouble(), c.color)));
        dataset.sort(Comparator.<Pair<Double, Color>, Double>comparing(pair -> pair.v1).reversed());

        if (dataset.size() > 0) {

            final Function<Double, Double> scale = Math::log1p;
            final List<RectangleShape> bars = new ArrayList<>();
            final var heightFactor = size.height / scale.apply(dataset.get(0).v1);
            final var gapWidth = 10d;
            final var barWidth = (size.width + gapWidth) / dataset.size() - gapWidth;
            final var i = IntPointer.to(0);
            dataset.forEach(pair -> {
                final var height = (float) (scale.apply(pair.v1) * heightFactor);
                final var bar = new RectangleShape((float) barWidth, height);
                bar.setPosition((float) (i.incrementPostfix() * (barWidth + gapWidth)), size.height - height);
                bar.setColor(pair.v2);
                bar.setCornerRadius(4);
                bars.add(bar);
            });

            bars.forEach(r -> r.draw((Graphics2D) image.getGraphics()));
        }

        return image;
    }
}
