package com.rubynaxela.onyx.gui.window;

import com.formdev.flatlaf.util.Animator;
import com.rubynaxela.jadeite.swing.GridBagConstraintsBuilder;
import com.rubynaxela.jadeite.swing.event.EnterAndExitListener;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.gui.animation.Animation;
import com.rubynaxela.onyx.gui.animation.FloatAnimation;
import com.rubynaxela.onyx.gui.components.IconButton;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.util.ComponentUtils;
import com.rubynaxela.onyx.util.ImageUtils;
import jiconfont.IconCode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the side panel with menu and user profile information. The size of this panel is 256x768.
 */
@SuppressWarnings("FieldCanBeLocal")
public class SidePanel extends JPanel {

    private final JPanel container;
    private final JLabel userLogo, username;
    private final ButtonPanel buttonPanel;

    /**
     * Creates the side panel.
     */
    public SidePanel() {
        setPreferredSize(new Dimension(256, 768));
        setBackground(new Color(0, 0, 0, 64));
        setLayout(new BorderLayout());

        container = new JPanel();
        container.setPreferredSize(new Dimension(256, 512));
        container.setBackground(Colors.TRANSPARENT);

        userLogo = new JLabel(new ImageIcon(ImageUtils.createCircleImage("/res/user_profile.png", 96, 96)));
        ComponentUtils.addMargin(userLogo, 16, 80, 16, 80);
        container.add(userLogo);

        username = new JLabel("Alex");
        ComponentUtils.setFontParams(username, Font.BOLD, 24f);
        ComponentUtils.addMargin(username, 0, 80, 16, 80);
        container.add(username);

        buttonPanel = new ButtonPanel();
        initButtonPanel();
        container.add(buttonPanel);

        add(container, BorderLayout.CENTER);

        final var copyright = new JLabel("<html><p style=\"color:rgba(255,255,255,0.15);text-align:center\">Onyx 3.0<br>" +
                                         "Copyright © 2022 RubyNaxela</p><br><p></p></html>");
        copyright.setHorizontalAlignment(SwingConstants.CENTER);
        add(copyright, BorderLayout.SOUTH);
    }

    private void initButtonPanel() {

        final var homeButton = new TabSelector(I18n.getString("label.tab.home"), MaterialIcons.HOME);
        homeButton.addActionListener(e -> ViewControllers.SWITCH_TAB.accept(MainWindow.Tab.HOME));
        buttonPanel.add(homeButton);

        final var historyButton = new TabSelector(I18n.getString("label.tab.history"), MaterialIcons.HISTORY);
        historyButton.addActionListener(e -> ViewControllers.SWITCH_TAB.accept(MainWindow.Tab.HISTORY));
        buttonPanel.add(historyButton);

        final var pendingOperationsButton = new TabSelector(I18n.getString("label.tab.pending"), MaterialIcons.PENDING_ACTIONS);
        buttonPanel.add(pendingOperationsButton);

        final var invoicesButton = new TabSelector(I18n.getString("label.tab.invoices"), MaterialIcons.RECEIPT_LONG);
        buttonPanel.add(invoicesButton);
    }

    public static class ButtonPanel extends JPanel {

        private final List<JButton> buttons = new ArrayList<>();

        public ButtonPanel() {
            setLayout(new GridBagLayout());
            setBackground(UIManager.getColor("Button.focusedBorderColor"));
        }

        public void add(@NotNull JButton button) {
            super.add(button, GridBagConstraintsBuilder.gbc().row(buttons.size()).build());
            buttons.add(button);
        }
    }

    public static class TabSelector extends IconButton {

        private static final Animator.Interpolator shiftInterpolator = Animation.easeInOut(0.1f, 0f);
        private static int threadCounter = 0;
        private float xOffset = 0;
        private Thread currentAnimation;

        public TabSelector(@NotNull String text, @NotNull IconCode icon) {
            super(" " + text, icon, new Insets(0, 12, 0, 0), new Insets(0, 4, 0, 0));
            setHorizontalAlignment(SwingConstants.LEFT);
            setPreferredSize(new Dimension(256, 48));
            addMouseListener(new EnterAndExitListener() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    final var animation = new FloatAnimation(xOffset, 8, offset -> xOffset = offset);
                    animation.setInterpolator(shiftInterpolator);
                    startAnimation(animation);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    final var animation = new FloatAnimation(xOffset, 0, offset -> xOffset = offset);
                    animation.setInterpolator(shiftInterpolator);
                    startAnimation(animation);
                }
            });
        }

        private void startAnimation(@NotNull FloatAnimation animation) {
            final int framerate = 60;
            final float duration = 0.2f;
            final int frameDurationMs = Math.round(1000f / framerate), totalFrames = Math.round(duration * framerate);
            if (currentAnimation != null) currentAnimation.interrupt();
            (currentAnimation = new Thread(() -> {
                try {
                    for (int frame = 0; frame < totalFrames; frame++) {
                        Thread.sleep(frameDurationMs);
                        animation.update(1f * frame / totalFrames);
                        repaint();
                    }
                } catch (InterruptedException ignored) {
                }
            }, "ButtonAnimationThread-" + threadCounter++)).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            final Graphics2D graphics = (Graphics2D) g.create();
            final var transform = graphics.getTransform();
            transform.translate(xOffset, 0);
            graphics.setTransform(transform);
            super.paintComponent(graphics);
        }
    }
}
