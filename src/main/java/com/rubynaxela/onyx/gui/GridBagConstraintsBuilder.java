package com.rubynaxela.onyx.gui;

import com.rubynaxela.onyx.util.FloatRange;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;

import static java.awt.GridBagConstraints.*;

public final class GridBagConstraintsBuilder {

    private final GridBagConstraints constraints;

    private GridBagConstraintsBuilder(@NotNull GridBagConstraints constraints) {
        this.constraints = constraints;
    }

    /**
     * Creates a new {@link GridBagConstraintsBuilder} with default parameters.
     *
     * @return a {@link GridBagConstraintsBuilder} with default parameters
     */
    @NotNull
    @Contract(value = "-> new", pure = true)
    public static GridBagConstraintsBuilder gbc() {
        return new GridBagConstraintsBuilder(new GridBagConstraints());
    }

    /**
     * Creates a new {@link GridBagConstraintsBuilder} with the parameters of the specified
     * {@link GridBagConstraints} object. The builder will not modify the argument.
     *
     * @return a {@link GridBagConstraintsBuilder} with the parameters of the argument
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static GridBagConstraintsBuilder gbc(@NotNull GridBagConstraints constraints) {
        return new GridBagConstraintsBuilder((GridBagConstraints) constraints.clone());
    }

    /**
     * Sets the component's row index (the {@link GridBagConstraints#gridy} value).
     *
     * @param row the component's row index
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder row(@Range(from = -1, to = Integer.MAX_VALUE) int row) {
        constraints.gridy = row;
        return this;
    }

    /**
     * Sets the component's column index (the {@link GridBagConstraints#gridx} value).
     *
     * @param column the component's column index
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder column(@Range(from = -1, to = Integer.MAX_VALUE) int column) {
        constraints.gridx = column;
        return this;
    }

    /**
     * Sets the component's row and column index (the {@link GridBagConstraints#gridy}
     * and {@link GridBagConstraints#gridx} values respectively).
     *
     * @param row    the component's row index
     * @param column the component's column index
     * @return reference to this builder
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    public GridBagConstraintsBuilder position(@Range(from = -1, to = Integer.MAX_VALUE) int row,
                                              @Range(from = -1, to = Integer.MAX_VALUE) int column) {
        constraints.gridx = column;
        constraints.gridy = row;
        return this;
    }

    /**
     * Sets the component's width in grid cells (the {@link GridBagConstraints#gridwidth} value).
     *
     * @param width the component's width
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder width(@Range(from = -1, to = Integer.MAX_VALUE) int width) {
        constraints.gridwidth = width;
        return this;
    }

    /**
     * Sets the component's height in grid cells (the {@link GridBagConstraints#gridheight} value).
     *
     * @param height the component's height
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder height(@Range(from = -1, to = Integer.MAX_VALUE) int height) {
        constraints.gridheight = height;
        return this;
    }

    /**
     * Sets the component's size in grid cells (the {@link GridBagConstraints#gridwidth}
     * and {@link GridBagConstraints#gridheight} values respectively).
     *
     * @param width  the component's width
     * @param height the component's height
     * @return reference to this builder
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    public GridBagConstraintsBuilder size(@Range(from = -1, to = Integer.MAX_VALUE) int width,
                                          @Range(from = -1, to = Integer.MAX_VALUE) int height) {
        constraints.gridwidth = width;
        constraints.gridheight = height;
        return this;
    }

    /**
     * Sets the component's extra horizontal space distribution rule (the {@link GridBagConstraints#weightx} value).
     *
     * @param weight the component's horizontal layout weight
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder weightX(@FloatRange(from = 0, to = Double.MAX_VALUE) double weight) {
        constraints.weightx = weight;
        return this;
    }

    /**
     * Sets the component's extra vertical space distribution rule (the {@link GridBagConstraints#weighty} value).
     *
     * @param weight the component's vertical layout weight
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder weightY(@FloatRange(from = 0, to = Double.MAX_VALUE) double weight) {
        constraints.weighty = weight;
        return this;
    }

    /**
     * Sets the component's extra space distribution rule (the {@link GridBagConstraints#weightx}
     * and {@link GridBagConstraints#weighty} values respectively).
     *
     * @param x the component's horizontal layout weight
     * @param y the component's vertical layout weight
     * @return reference to this builder
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    public GridBagConstraintsBuilder weight(@FloatRange(from = 0, to = Double.MAX_VALUE) double x,
                                            @FloatRange(from = 0, to = Double.MAX_VALUE) double y) {
        constraints.weightx = x;
        constraints.weighty = y;
        return this;
    }

    /**
     * Sets the component's external padding (the {@link GridBagConstraints#insets} value).
     *
     * @param padding the component's padding
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder extPadding(@NotNull Insets padding) {
        constraints.insets = padding;
        return this;
    }

    /**
     * Sets the component's external padding (the {@link GridBagConstraints#insets} value).
     *
     * @param top    the component's top padding
     * @param left   the component's left padding
     * @param bottom the component's bottom padding
     * @param right  the component's right padding
     * @return reference to this builder
     */
    @Contract(value = "_, _, _, _ -> this", mutates = "this")
    public GridBagConstraintsBuilder extPadding(int top, int left, int bottom, int right) {
        constraints.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * Sets the component's external top padding (the {@link GridBagConstraints#insets}{@code #}{@link Insets#top} value).
     *
     * @param padding the component's top padding
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder extPaddingTop(int padding) {
        return extPadding(padding, constraints.insets.left, constraints.insets.bottom, constraints.insets.right);
    }

    /**
     * Sets the rule of placement the component in a cell (the {@link GridBagConstraints#anchor} value).
     *
     * @param anchor the placement fule
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder anchor(@MagicConstant(
            intValues = {CENTER, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, PAGE_START, PAGE_END,
                         LINE_START, LINE_END, FIRST_LINE_START, FIRST_LINE_END, LAST_LINE_START, LAST_LINE_END, BASELINE,
                         BASELINE_LEADING, BASELINE_TRAILING, ABOVE_BASELINE, ABOVE_BASELINE_LEADING, ABOVE_BASELINE_TRAILING,
                         BELOW_BASELINE, BELOW_BASELINE_LEADING, BELOW_BASELINE_TRAILING}) int anchor) {
        constraints.anchor = anchor;
        return this;
    }

    /**
     * Sets the rule of filling the cell with the component (the {@link GridBagConstraints#fill} value).
     *
     * @param fill the filling fule
     * @return reference to this builder
     */
    @Contract(value = "_ -> this", mutates = "this")
    public GridBagConstraintsBuilder fill(@MagicConstant(intValues = {NONE, HORIZONTAL, VERTICAL, BOTH}) int fill) {
        constraints.fill = fill;
        return this;
    }

    public GridBagConstraints build() {
        return constraints;
    }
}
