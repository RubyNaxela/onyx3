package com.rubynaxela.onyx.gui.animation;

import com.rubynaxela.onyx.gui.window.MainWindow;
import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents the intro animation panel. The animation is played when the program is started, before any controls appear.
 */
public class AnimationPanel extends JPanel {

    private static final int LOGO_OFFSET = -256;
    private final List<Drawable> components = new ArrayList<>();
    private final AnimationGroup<? super Animation<?>>
            ribbonEnterAnimations = new AnimationGroup<>(4),
            ribbonExtendAnimations = new AnimationGroup<>(4),
            ribbonFoldAnimations = new AnimationGroup<>(6),
            circleAnimation = new AnimationGroup<>(2),
            logoMoveAnimation = new AnimationGroup<>(6);
    private FloatAnimation logoAppearN, logoAppearY, logoAppearX;
    private Runnable stopCallback;

    /**
     * Creates the intro animation panel.
     */
    public AnimationPanel() {
        final var windowCenter = new Vector2f(MainWindow.WIDTH / 2f, MainWindow.HEIGHT / 2f);
        setupText(windowCenter);
        setupCircle(windowCenter);
        setupRibbons(windowCenter);
        setupRibbonExtensions(windowCenter);
        logoMoveAnimation.setInterpolator(Animation.easeInOut(0.8f, 0.2f));
    }

    private void setupCircle(@NotNull Vector2f windowCenter) {

        final var circle = new CircleShape(0);
        circle.setPosition(windowCenter);
        circle.setColor(Colors.LOGO_DARK_GRAY);
        components.add(circle);
        circleAnimation.add(new FloatAnimation(0, 128, circle::setRadius));
        circleAnimation.add(new Vector2fAnimation(windowCenter, Vector2f.subtract(windowCenter, new Vector2f(128, 128)),
                                                  circle::setPosition));
        circleAnimation.setInterpolator(Animation.easeInOut(0.1f, 0f));
        logoMoveAnimation.add(new DynamicFloatAnimation(circle::getPositionX, LOGO_OFFSET, circle::setPositionX));
    }

    private void setupRibbons(@NotNull Vector2f windowCenter) {

        final var ribbonNorth = new RectangleShape(32, windowCenter.y);
        ribbonNorth.setPositionX(windowCenter.x - 16 + 7);
        ribbonNorth.setColor(Colors.LOGO_LIGHT_GRAY_SHADE);
        components.add(ribbonNorth);
        ribbonEnterAnimations.add(new FloatAnimation(-ribbonNorth.getSize().y - 7, -64 - 7, ribbonNorth::setPositionY));
        ribbonFoldAnimations.add(new FloatAnimation(ribbonNorth.getSize().y, 0, ribbonNorth::setSizeY));
        ribbonFoldAnimations.add(new FloatAnimation(-64 - 7, -64 - 7 + ribbonNorth.getSize().y, ribbonNorth::setPositionY));

        final var ribbonEast = new RectangleShape(windowCenter.x, 32);
        ribbonEast.setPositionY(windowCenter.y - 16 + 7);
        ribbonEast.setColor(Colors.LOGO_LIGHT_GRAY_SHADE);
        components.add(ribbonEast);
        ribbonEnterAnimations.add(new FloatAnimation(MainWindow.WIDTH + 7, windowCenter.x + 64 + 7, ribbonEast::setPositionX));
        ribbonFoldAnimations.add(new FloatAnimation(ribbonEast.getSize().x, 0, ribbonEast::setSizeX));

        final var ribbonSouth = new RectangleShape(32, windowCenter.y);
        ribbonSouth.setPositionX(windowCenter.x - 16);
        ribbonSouth.setColor(Colors.LOGO_LIGHT_GRAY_SHADE);
        components.add(ribbonSouth);
        ribbonEnterAnimations.add(new FloatAnimation(MainWindow.HEIGHT, windowCenter.y + 64, ribbonSouth::setPositionY));
        ribbonFoldAnimations.add(new ColorAnimation(Colors.LOGO_LIGHT_GRAY_SHADE, Colors.LOGO_LIGHT_GRAY,
                                                    ribbonSouth::setColor));
        logoMoveAnimation.add(new DynamicFloatAnimation(ribbonSouth::getPositionX, LOGO_OFFSET, ribbonSouth::setPositionX));

        final var ribbonWest = new RectangleShape(windowCenter.x, 32);
        ribbonWest.setPositionY(windowCenter.y - 16 - 7);
        ribbonWest.setColor(Colors.LOGO_LIGHT_GRAY_SHADE);
        components.add(ribbonWest);
        ribbonEnterAnimations.add(new FloatAnimation(-ribbonWest.getSize().x - 7, -64 - 7, ribbonWest::setPositionX));
        ribbonFoldAnimations.add(new FloatAnimation(ribbonWest.getSize().x, 0, ribbonWest::setSizeX));
        ribbonFoldAnimations.add(new FloatAnimation(-64 - 7, -64 - 7 + ribbonWest.getSize().x, ribbonWest::setPositionX));

        ribbonEnterAnimations.setInterpolator(Animation.easeInOut(0.8f, 0.2f));
        ribbonEnterAnimations.update(0);
        ribbonFoldAnimations.setInterpolator(Animation.easeInOut(0.8f, 0.2f));
    }

    private void setupRibbonExtensions(@NotNull Vector2f windowCenter) {

        final var ribbonExtensionNorth = new RectangleShape(32, 0);
        ribbonExtensionNorth.setPosition(windowCenter.x, windowCenter.y - 94);
        ribbonExtensionNorth.setRotation(45);
        ribbonExtensionNorth.setColor(Colors.LOGO_LIGHT_GRAY);
        components.add(ribbonExtensionNorth);
        ribbonExtendAnimations.add(new FloatAnimation(0, 132, ribbonExtensionNorth::setSizeY));

        final var ribbonExtensionEast = new RectangleShape(32, 0);
        ribbonExtensionEast.setPosition(windowCenter.x + 94, windowCenter.y);
        ribbonExtensionEast.setRotation(135);
        ribbonExtensionEast.setColor(Colors.LOGO_LIGHT_GRAY);
        components.add(ribbonExtensionEast);
        ribbonExtendAnimations.add(new FloatAnimation(0, 84, ribbonExtensionEast::setSizeY));

        final var ribbonExtensionSouth = new RectangleShape(32, 0);
        ribbonExtensionSouth.setPosition(windowCenter.x + 7, windowCenter.y + 87);
        ribbonExtensionSouth.setRotation(225);
        ribbonExtensionSouth.setColor(Colors.LOGO_LIGHT_GRAY);
        components.add(ribbonExtensionSouth);
        ribbonExtendAnimations.add(new FloatAnimation(0, 114, ribbonExtensionSouth::setSizeY));

        final var ribbonExtensionWest = new RectangleShape(32, 0);
        ribbonExtensionWest.setPosition(windowCenter.x - 94, windowCenter.y);
        ribbonExtensionWest.setRotation(315);
        ribbonExtensionWest.setColor(Colors.LOGO_LIGHT_GRAY);
        components.add(ribbonExtensionWest);
        ribbonExtendAnimations.add(new FloatAnimation(0, 132, ribbonExtensionWest::setSizeY));

        ribbonExtendAnimations.setInterpolator(Animation.easeInOut(0.8f, 0.4f));

        Stream.of(ribbonExtensionNorth, ribbonExtensionEast, ribbonExtensionSouth, ribbonExtensionWest)
              .forEach(r -> logoMoveAnimation.add(new DynamicFloatAnimation(r::getPositionX, LOGO_OFFSET, r::setPositionX)));
    }

    private void setupText(@NotNull Vector2f windowCenter) {

        final var font = IOUtils.tryCreateFontFromResource("/res/qualy.ttf", 128f, Font.BOLD);

        final var logoTextN = new Text("N");
        logoTextN.setPosition(-128, windowCenter.y + 78);
        logoTextN.setFont(font);
        components.add(logoTextN);
        logoAppearN = new FloatAnimation(windowCenter.x + LOGO_OFFSET, windowCenter.x + 1 / 3f * LOGO_OFFSET + 32,
                                         logoTextN::setPositionX);
        logoAppearN.setInterpolator(Animation.easeInOut(0.1f, 0f));

        final var logoTextY = new Text("Y");
        logoTextY.setPosition(-128, windowCenter.y + 78);
        logoTextY.setFont(font);
        components.add(logoTextY);
        logoAppearY = new FloatAnimation(windowCenter.x + LOGO_OFFSET, windowCenter.x - 1 / 3f * LOGO_OFFSET + 16,
                                         logoTextY::setPositionX);
        logoAppearY.setInterpolator(Animation.easeInOut(0.1f, 0f));

        final var logoTextX = new Text("X");
        logoTextX.setPosition(-128, windowCenter.y + 78);
        logoTextX.setFont(font);
        components.add(logoTextX);
        logoAppearX = new FloatAnimation(windowCenter.x + LOGO_OFFSET, windowCenter.x - LOGO_OFFSET,
                                         logoTextX::setPositionX);
        logoAppearX.setInterpolator(Animation.easeInOut(0.1f, 0f));
    }

    /**
     * Starts the intro animation.
     */
    public void startAnimation() {
        final int framerate = 60;
        final float duration = 4;
        final int frameDurationMs = Math.round(1000f / framerate), totalFrames = Math.round(duration * framerate);
        new Thread(() -> {
            try {
                for (int frame = 0; frame < totalFrames; frame++) {
                    Thread.sleep(frameDurationMs);
                    animate(1f * frame / totalFrames);
                }
                stopCallback.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "IntroAnimationThread").start();
    }

    /**
     * Sets the action to be executed when the animation finishes.
     *
     * @param callback the action to be executed when the animation finishes
     */
    public void setStopCallback(@NotNull Runnable callback) {
        this.stopCallback = callback;
    }

    void animate(float progress) {
        if (progress <= 0.25f) ribbonEnterAnimations.update(4 * progress);
        if (progress > 0.2f && progress <= 0.3f) ribbonExtendAnimations.update(10 * (progress - 0.2f));
        if (progress > 0.25f && progress <= 0.5f) ribbonFoldAnimations.update(4 * (progress - 0.25f));
        if (progress > 0.3f && progress <= 0.5f) circleAnimation.update(5 * (progress - 0.3f));
        if (progress > 0.5f && progress <= 0.75f) logoMoveAnimation.update(4 * (progress - 0.5f));
        if (progress > 0.7f && progress <= 0.8f) logoAppearX.update(10 * (progress - 0.7f));
        if (progress > 0.735f && progress <= 0.835f) logoAppearY.update(10 * (progress - 0.735f));
        if (progress > 0.77f && progress <= 0.87f) logoAppearN.update(10 * (progress - 0.77f));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final var graphics = (Graphics2D) g;
        components.forEach(c -> c.draw(graphics));
    }
}
