package com.rubynaxela.onyx.gui.animation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class Text extends Drawable {

    private final String text;
    private Font font;

    /**
     * Constructs a new text object with the specified string.
     *
     * @param text the string to be displayed by this object
     */
    public Text(@NotNull String text) {
        this.text = text;
    }

    public void setFont(@Nullable Font font) {
        this.font = font;
    }

    @Override
    public void draw(@NotNull Graphics2D graphics) {
        final var previousColor = graphics.getColor();
        final var previousAntiAliasing = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        final var previousFont = graphics.getFont();
        graphics.setColor(color);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (font != null) graphics.setFont(font);
        graphics.drawString(text, (int) position.x, (int) position.y);
        graphics.setColor(previousColor);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, previousAntiAliasing);
        graphics.setFont(previousFont);
    }
}
