package com.rubynaxela.onyx.util;

import com.rubynaxela.onyx.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class GraphicsUtils {

    private GraphicsUtils() {
    }

    public static void applyQualityRenderingHints(@NotNull Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static Image createCircleImage(@NotNull String resource, int width, int height) {

        final Image image = Objects.requireNonNull(IOUtils.tryCreateImageFromResource(resource))
                                   .getScaledInstance(width, height, Image.SCALE_SMOOTH);

        final int diameter = Math.min(image.getWidth(null), image.getHeight(null));

        final BufferedImage mask = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();

        final BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        applyQualityRenderingHints(g2d);

        final int x = (diameter - image.getWidth(null)) / 2, y = (diameter - image.getHeight(null)) / 2;
        g2d.drawImage(image, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();

        return masked;
    }
}
