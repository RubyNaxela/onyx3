package com.rubynaxela.onyx.gui.animation;

import com.rubynaxela.jadeite.annotations.FloatRange;
import com.rubynaxela.jadeite.awt.graphics.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a single floating-point vector animation.
 */
public class Vector2fAnimation extends Animation<Vector2f> {

    private final Vector2f startPoint, offset;

    /**
     * Constructs a new floating-point vector animation.
     *
     * @param startPoint the initial point
     * @param endPoint   the end point
     * @param update     a function that takes the point corresponding to the current animation progress
     *                   (which is a point on a line segment between {@code startPoint} and {@code endValue})
     *                   and makes an update accordingly (for instance, sets a component position or size)
     */
    public Vector2fAnimation(@NotNull Vector2f startPoint, @NotNull Vector2f endPoint, @NotNull Consumer<Vector2f> update) {
        this.startPoint = startPoint;
        this.offset = Vector2f.subtract(endPoint, startPoint);
        this.update = update;
    }

    public void update(@FloatRange(from = 0, to = 1) float progress) {
        update.accept(new Vector2f(startPoint.x + interpolator.interpolate(progress) * offset.x,
                                   startPoint.y + interpolator.interpolate(progress) * offset.y));
    }
}
