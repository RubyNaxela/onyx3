package com.rubynaxela.onyx.gui.animation;

import com.rubynaxela.jadeite.annotations.FloatRange;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Represents a single floating-point value animation whose edge values are set when the animation is started.
 */
public class DynamicFloatAnimation extends Animation<Float> {

    private final Supplier<Float> startValueGetter, endValueGetter;
    private float startValue, difference;
    private boolean edgeValuesAssigned = false;

    /**
     * Constructs a new dynamic floating-point value animation. The edge values are assigned
     * using the specified getters when {@link #update(float)} is called for the first time.
     *
     * @param startValueGetter the initial value getter function
     * @param endValueGetter   the end value getter function
     * @param update           a function that takes the value corresponding to the current animation progress
     *                         (which is in range from {@code startValue} to {@code endValue} inclusively) and
     *                         makes an update accordingly (for instance, sets a component position or color)
     */
    public DynamicFloatAnimation(@NotNull Supplier<Float> startValueGetter, @NotNull Supplier<Float> endValueGetter,
                                 @NotNull Consumer<Float> update) {
        this.startValueGetter = startValueGetter;
        this.endValueGetter = endValueGetter;
        this.update = update;
    }

    /**
     * Constructs a new dynamic floating-point value animation. The start value is assigned
     * using the specified getter when {@link #update(float)} is called for the first time.
     *
     * @param startValueGetter the initial value getter function
     * @param offset           the offset value (added to the initial value)
     * @param update           a function that takes the value corresponding to the current animation progress
     *                         (which is in range from {@code startValue} to {@code endValue} inclusively) and
     *                         makes an update accordingly (for instance, sets a component position or color)
     */
    public DynamicFloatAnimation(@NotNull Supplier<Float> startValueGetter, float offset, @NotNull Consumer<Float> update) {
        this.startValueGetter = startValueGetter;
        this.endValueGetter = () -> startValueGetter.get() + offset;
        this.update = update;
    }

    /**
     * Updates the animation edge values using getters specified in this animation's initialization.
     */
    public void getEdgeValues() {
        startValue = startValueGetter.get();
        difference = endValueGetter.get() - startValue;
        edgeValuesAssigned = true;
    }

    @Override
    public void update(@FloatRange(from = 0, to = 1) float progress) {
        if (!edgeValuesAssigned) getEdgeValues();
        update.accept(startValue + interpolator.interpolate(progress) * difference);
    }
}
