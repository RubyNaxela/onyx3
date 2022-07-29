package com.rubynaxela.onyx.gui.animation;

import com.rubynaxela.onyx.graphics.Vector3i;
import com.rubynaxela.onyx.util.FloatRange;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents a single {@link Color} animation.
 */
public class ColorAnimation extends Animation<Color> {

    private final Vector3i startColor, offset;

    /**
     * Constructs a new floating-point vector animation.
     *
     * @param startColor the initial color
     * @param endColor   the end color
     * @param update     a function that takes the color corresponding to the current animation progress (which is
     *                   a color on a line segment in the RGB space between {@code startColor} and {@code endColor})
     *                   and makes an update accordingly (for instance, sets a component fill or stroke color)
     */
    public ColorAnimation(@NotNull Color startColor, @NotNull Color endColor, @NotNull Consumer<Color> update) {
        this.startColor = Vector3i.fromColor(startColor);
        this.offset = Vector3i.subtract(Vector3i.fromColor(endColor), this.startColor);
        this.update = update;
    }

    public void update(@FloatRange(from = 0, to = 1) float progress) {
        update.accept(new Vector3i(startColor.x + interpolator.interpolate(progress) * offset.x,
                                   startColor.y + interpolator.interpolate(progress) * offset.y,
                                   startColor.z + interpolator.interpolate(progress) * offset.z).toColor());
    }
}
