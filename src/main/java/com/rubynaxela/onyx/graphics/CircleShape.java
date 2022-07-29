package com.rubynaxela.onyx.graphics;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class CircleShape extends Drawable {

    private float radius;

    public CircleShape(float radius) {
        this.radius = radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(@NotNull Graphics2D g) {
        final var graphics = (Graphics2D) g.create();
        graphics.setColor(color);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillOval((int) position.x, (int) position.y, (int) (2 * radius), (int) (2 * radius));
    }

    @Override
    public Rectangle2D.Float getGlobalBounds() {
        return new Rectangle2D.Float(position.x, position.y, radius * 2, radius * 2);
    }
}
