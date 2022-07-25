package com.rubynaxela.onyx.util;

import java.lang.annotation.*;

/**
 * An annotation which allows to specify for floating-point type (float, double) an
 * allowed values range. Applying this annotation to other types is not correct.
 * <p>
 * Example:
 * <pre>{@code public @Range(from = 0, to = Float.MAX_VALUE) float weight() {
 *   return this.weight; // returns a non-negative float
 * }}</pre>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
public @interface FloatRange {

    /**
     * @return minimal allowed value (inclusive)
     */
    double from();

    /**
     * @return maximal allowed value (inclusive)
     */
    double to();
}
