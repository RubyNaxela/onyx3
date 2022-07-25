package com.rubynaxela.onyx.gui.animation;

import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.CubicBezierEasing;
import com.rubynaxela.onyx.util.FloatRange;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class Animation<T> {

    protected Consumer<T> update;
    protected Animator.Interpolator interpolator = t -> t;

    public static CubicBezierEasing easeInOut(float x1, float x2) {
        return new CubicBezierEasing(x1, 0f, x2, 1f);
    }

    /**
     * Sets the interpolator for this animation. The interpolator is used by animation to change the timing fraction.
     *
     * @param interpolator the interpolator for this animation
     */
    public void setInterpolator(@NotNull Animator.Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    /**
     * Calls the update function with the specified animation progress value.
     *
     * @param progress the animation progress
     */
    public abstract void update(@FloatRange(from = 0, to = 1) float progress);
}
