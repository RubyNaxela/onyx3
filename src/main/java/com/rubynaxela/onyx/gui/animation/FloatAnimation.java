package com.rubynaxela.onyx.gui.animation;

import com.rubynaxela.onyx.util.FloatRange;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents a single floating-point value animation.
 */
public class FloatAnimation extends Animation<Float> {

    private final float startValue, difference;

    /**
     * Constructs a new floating-point value animation.
     *
     * @param startValue the initial value
     * @param endValue   the end value
     * @param update     a function that takes the value corresponding to the current animation progress
     *                   (which is in range from {@code startValue} to {@code endValue} inclusively) and
     *                   makes an update accordingly (for instance, sets a component position or color)
     */
    public FloatAnimation(float startValue, float endValue, @NotNull Consumer<Float> update) {
        this.startValue = startValue;
        this.difference = endValue - startValue;
        this.update = update;
    }

    public void update(@FloatRange(from = 0, to = 1) float progress) {
        update.accept(startValue + interpolator.interpolate(progress) * difference);
    }
}
