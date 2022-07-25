package com.rubynaxela.onyx.io;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public final class IOUtils {

    private IOUtils() {
    }

    /**
     * Creates a {@link Font} from the specified TrueType font file resource located in the module of this class.
     *
     * @param resource the TrueType font file resource name
     * @param size     the font size in pixels
     * @param style    the font style
     * @return a {@link Font} created from the font resource with the specified parameters
     * @throws NullPointerException if the resource could not be found
     * @throws IOException          if the resource could not be completely read
     * @throws FontFormatException  if the file does not contain the required font tables for the TrueType format
     */
    @NotNull
    public static Font createFontFromResource(@NotNull String resource, float size,
                                              @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style)
    throws NullPointerException, IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(IOUtils.class.getResourceAsStream(resource)))
                   .deriveFont(style, size);
    }

    /**
     * Tries to create a {@link Font} from the specified TrueType font file resource located in the module of this class
     * using the {@link #createFontFromResource} method. Returns {@code null} if the font creation fails for any reason.
     *
     * @param resource the TrueType font file resource name
     * @param size     the font size in pixels
     * @param style    the font style
     * @return a {@link Font} created from the font resource with the
     * specified parameters, or {@code null} if the font could not be created
     */
    @Nullable
    public static Font tryCreateFontFromResource(@NotNull String resource, float size,
                                                 @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style) {
        try {
            return createFontFromResource(resource, size, style);
        } catch (NullPointerException | FontFormatException | IOException e) {
            return null;
        }
    }

    /**
     * Tries to create a {@link BufferedImage} from the specigied image file resource located in
     * the module of this class. Returns {@code null} if the font creation fails for any reason.
     *
     * @param resource the image file resource name
     * @return an {@link BufferedImage} created from the image resource, or {@code null} if the font could not be created
     */
    public static BufferedImage tryCreateImageFromResource(@NotNull String resource) {
        try {
            return ImageIO.read(Objects.requireNonNull(IOUtils.class.getResourceAsStream(resource)));
        } catch (NullPointerException | IOException e) {
            return null;
        }
    }
}
