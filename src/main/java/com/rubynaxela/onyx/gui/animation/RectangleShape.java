package com.rubynaxela.onyx.gui.animation;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RectangleShape extends Drawable {

    private static final double PI_180 = Math.PI / 180;
    private Vector2f size;
    private float rotation = 0;

    public RectangleShape(float width, float height) {
        this.size = new Vector2f(width, height);
    }

    @NotNull
    public Vector2f getSize() {
        return size;
    }

    public void setSizeX(float x) {
        this.size = new Vector2f(x, this.size.y);
    }

    public void setSizeY(float y) {
        this.size = new Vector2f(this.size.x, y);
    }

    public void setRotation(float rotation) {
        this.rotation = (rotation + 180) % 360 - 180;
    }

    @Override
    public void draw(@NotNull Graphics2D graphics) {
        final var previousColor = graphics.getColor();
        final var previousTransform = graphics.getTransform().clone();
        final var previousAntiAliasing = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        if (rotation != 0) {
            final var transform = graphics.getTransform();
            transform.rotate(rotation * PI_180, position.x, position.y);
            graphics.setTransform(transform);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setColor(color);
        graphics.fillRect((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        graphics.setColor(previousColor);
        if (rotation != 0) {
            graphics.setTransform((AffineTransform) previousTransform);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, previousAntiAliasing);
        }
    }
}
