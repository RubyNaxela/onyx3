package com.rubynaxela.onyx.graphics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class Text extends Drawable {

    private static final Map<String, Rectangle2D> metricsCache = new HashMap<>();

    private final String text;
    private Font font;

    /**
     * Constructs a new text object with the specified string.
     *
     * @param text the string to be displayed by this object
     */
    public Text(@NotNull String text) {
        this.text = text;
    }

    private static Rectangle2D getStringSize(@NotNull String text) {
        if (metricsCache.containsKey(text)) return metricsCache.get(text);
        final var g = new JLabel().getGraphics();
        final var bounds = g.getFontMetrics().getStringBounds(text, g);
        metricsCache.put(text, bounds);
        return bounds;
    }

    public void setFont(@Nullable Font font) {
        this.font = font;
    }

    @Override
    public void draw(@NotNull Graphics2D g) {
        final var graphics = (Graphics2D) g.create();
        graphics.setColor(color);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (font != null) graphics.setFont(font);
        graphics.drawString(text, (int) position.x, (int) position.y);
    }

    @Override
    public Rectangle2D.Float getGlobalBounds() {
        final var bounds = getStringSize(text);
        return new Rectangle2D.Float(position.x, position.y, (float) bounds.getWidth(), (float) bounds.getHeight());
    }
}
