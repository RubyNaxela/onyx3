package com.rubynaxela.onyx.gui.animation;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CircleShape extends Drawable {

    private float radius;

    public CircleShape(float radius) {
        this.radius = radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(@NotNull Graphics2D graphics) {
        final var previousColor = graphics.getColor();
        final var previousAntiAliasing = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        graphics.setColor(color);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillOval((int) position.x, (int) position.y, (int) (2 * radius), (int) (2 * radius));
        graphics.setColor(previousColor);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, previousAntiAliasing);
    }
}
