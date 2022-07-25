package com.rubynaxela.onyx.gui.window;

import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.gui.animation.AnimationPanel;
import com.rubynaxela.onyx.gui.tabs.HistoryTab;
import com.rubynaxela.onyx.gui.tabs.HomeTab;
import com.rubynaxela.onyx.gui.tabs.WindowTab;
import com.rubynaxela.onyx.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Represents application window structure.
 */
@SuppressWarnings("FieldCanBeLocal")
public class MainWindow extends JFrame {

    public static final int WIDTH = 1152, HEIGHT = 796;
    private static final boolean DISABLE_ANIMATION = true;
    private final AnimationPanel animationPanel;
    private final JPanel titleBar, sidePanel;
    private final WindowTab homeTab, historyTab;
    private WindowTab currentTab;

    /**
     * Creates the application window.
     */
    public MainWindow() {

        final var icon = IOUtils.tryCreateImageFromResource("/res/icon.png");
        Taskbar.getTaskbar().setIconImage(icon);
        setIconImage(icon);

        setTitle("Onyx");
        setUndecorated(true);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setShape(new RoundRectangle2D.Double(0, 0, WIDTH, HEIGHT, 20, 20));
        setLocationRelativeTo(null);

        titleBar = new TitleBar(this);
        add(titleBar, BorderLayout.NORTH);

        animationPanel = new AnimationPanel();
        sidePanel = new SidePanel();
        homeTab = new HomeTab();
        historyTab = new HistoryTab();

        ViewControllers.SWITCH_TAB = tab -> {
            if (currentTab != null) currentTab.removeFromContainer(this);
            final var newTab = switch (tab) {
                case HOME -> homeTab;
                case HISTORY -> historyTab;
            };
            if (currentTab != newTab) {
                currentTab = newTab;
                newTab.addToContainer(this, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        };

        if (!DISABLE_ANIMATION) {

            add(animationPanel, BorderLayout.CENTER);

            animationPanel.setStopCallback(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                SwingUtilities.invokeLater(() -> {
                    remove(animationPanel);
                    add(sidePanel, BorderLayout.WEST);
                    ViewControllers.SWITCH_TAB.accept(Tab.HOME);
                    revalidate();
                    repaint();
                });
            });
        } else {
            add(sidePanel, BorderLayout.WEST);
            ViewControllers.SWITCH_TAB.accept(Tab.HOME);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible && !DISABLE_ANIMATION) animationPanel.startAnimation();
    }

    public enum Tab {
        HOME, HISTORY
    }
}
