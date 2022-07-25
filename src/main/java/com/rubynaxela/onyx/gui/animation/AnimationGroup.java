package com.rubynaxela.onyx.gui.animation;

import com.formdev.flatlaf.util.Animator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;

/**
 * A simple extension of {@code ArrayList<Animation>} used to create compound animations.
 */
public class AnimationGroup<T extends Animation<?>> extends ArrayList<T> {

    /**
     * Constructs an empty {@code AnimationGroup} with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the {@code AnimationGroup}
     * @throws IllegalArgumentException if the specified initial capacity is negative
     */
    public AnimationGroup(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Sets the interpolator for this {@code AnimationGroup}.
     * The interpolator is used by animations to change the timing fraction.
     *
     * @param interpolator the interpolator for the animations
     */
    public void setInterpolator(@NotNull Animator.Interpolator interpolator) {
        forEach(a -> a.setInterpolator(interpolator));
    }

    /**
     * Calls the {@link FloatAnimation#update(float)} method on every {@code Animation} that is a part of this group.
     *
     * @param progress the animation progress value
     */
    public void update(float progress) {
        forEach(a -> a.update(progress));
    }
}
