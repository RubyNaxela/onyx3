package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.jadeite.awt.JColor;
import com.rubynaxela.jadeite.awt.JInsets;
import com.rubynaxela.jadeite.util.OptionalGetter;
import com.rubynaxela.onyx.util.Colors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

    public static final JColor DEFAULT_COLOR = new JColor(UIManager.getColor("Desktop.background")).brighter();
    private final JInsets padding;
    private JColor color;

    public Card(@NotNull LayoutManager manager, @NotNull JInsets padding) {
        super(manager);
        this.padding = padding;
        this.color = DEFAULT_COLOR;
        setBackground(Colors.TRANSPARENT);
    }

    public Card(@NotNull LayoutManager manager, int padding) {
        this(manager, JInsets.of(padding));
    }

    public Card(@NotNull LayoutManager manager) {
        this(manager, 16);
    }

    public Card(@NotNull JInsets padding) {
        this(new FlowLayout(), padding);
    }

    public Card(int padding) {
        this(new FlowLayout(), padding);
    }

    public Card() {
        this(16);
    }

    public JColor getColor() {
        return color;
    }

    public void setColor(@Nullable Color color) {
        this.color = OptionalGetter.ofNullable(color).get(JColor::new).orElse(null);
    }

    /**
     * @return {@link Colors#TRANSPARENT}
     * @deprecated Use {@link #getColor} instead.
     */
    @Deprecated
    @Override
    public Color getBackground() {
        return super.getBackground();
    }

    /**
     * This method should be called internally.
     *
     * @param color the desired background {@code Color}
     * @deprecated Use {@link #setColor} instead.
     */
    @Deprecated
    @Override
    public void setBackground(@Nullable Color color) {
        super.setBackground(color);
    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        final var insets = super.getInsets();
        final var graphics = (Graphics2D) g.create();
        graphics.setColor(color);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.fillRoundRect(insets.left, insets.top, getWidth() - insets.left - insets.right,
                               getHeight() - insets.top - insets.bottom, 16, 16);
        graphics.dispose();
        super.paintComponent(g);
    }

    @Override
    public Insets getInsets() {
        return JInsets.add(super.getInsets(), padding);
    }
}
