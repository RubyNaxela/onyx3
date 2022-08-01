package com.rubynaxela.onyx.graphics;

import com.rubynaxela.onyx.util.FloatRange;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectangleShape extends Drawable {

    private static final double PI_180 = Math.PI / 180;
    private Vector2f size;
    private float rotation = 0, cornerRadius = 0;

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

    public void setCornerRadius(@FloatRange(from = 0, to = Float.MAX_VALUE) float cornerRadius) {
        if (cornerRadius < 0) throw new IllegalArgumentException("cornerRadius must be a positive number");
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void draw(@NotNull Graphics2D g) {
        final var graphics = (Graphics2D) g.create();
        if (rotation != 0) {
            final var transform = graphics.getTransform();
            transform.rotate(rotation * PI_180, position.x, position.y);
            graphics.setTransform(transform);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graphics.setColor(color);
        if (cornerRadius == 0) graphics.fillRect((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        else {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.fillRoundRect((int) position.x, (int) position.y, (int) size.x, (int) size.y,
                                   (int) (2 * cornerRadius), (int) (2 * cornerRadius));
        }
    }

    @Override
    public Rectangle2D.Float getGlobalBounds() {
        return new Rectangle2D.Float(position.x, position.y, size.x, size.y);
    }
}
