package com.rubynaxela.onyx.util;

import com.rubynaxela.jadeite.awt.graphics.GraphicsUtils;
import com.rubynaxela.onyx.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static Image createCircleImage(@NotNull String resource, int width, int height) {

        final Image image = Objects.requireNonNull(IOUtils.tryCreateImageFromResource(resource))
                                   .getScaledInstance(width, height, Image.SCALE_SMOOTH);

        final int diameter = Math.min(image.getWidth(null), image.getHeight(null));

        final BufferedImage mask = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mask.createGraphics();
        GraphicsUtils.applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();

        final BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        GraphicsUtils.applyQualityRenderingHints(g2d);

        final int x = (diameter - image.getWidth(null)) / 2, y = (diameter - image.getHeight(null)) / 2;
        g2d.drawImage(image, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();

        return masked;
    }
}
