package com.rubynaxela.onyx.gui.animation;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Represents objects that can be drawn with {@link Graphics2D} on a {@link JComponent}.
 */
public abstract class Drawable {

    protected Vector2f position = Vector2f.ZERO;
    protected Color color = Color.WHITE;

    public void setPosition(@NotNull Vector2f position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position = new Vector2f(x, y);
    }

    public float getPositionX() {
        return position.x;
    }

    public void setPositionX(float x) {
        this.position = new Vector2f(x, this.position.y);
    }

    public void setPositionY(float y) {
        this.position = new Vector2f(this.position.x, y);
    }

    public void setColor(@NotNull Color color) {
        this.color = color;
    }

    /**
     * Draws this object with the specified graphics.
     *
     * @param graphics a {@link Graphics2D} object that draws this object
     */
    public abstract void draw(@NotNull Graphics2D graphics);
}
