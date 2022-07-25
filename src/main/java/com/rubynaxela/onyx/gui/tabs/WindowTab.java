package com.rubynaxela.onyx.gui.tabs;

import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the center content panel. The size of this panel is 896x768.
 */
public abstract class WindowTab {

    private final JPanel root, container;

    public WindowTab() {
        root = new JPanel();
        ComponentUtils.addMargin(root, 32);
        root.setPreferredSize(new Dimension(896, 768));
        root.setBackground(Colors.TRANSPARENT);
        root.setLayout(new BorderLayout());
        container = new JPanel();
        root.add(container, BorderLayout.CENTER);
    }

    public void add(@NotNull Component component) {
        container.add(component);
    }

    public void add(@NotNull Component component, @Nullable Object constraints) {
        container.add(component, constraints);
    }

    public void setRootLayout(@NotNull LayoutManager manager) {
        root.setLayout(manager);
    }

    public void setLayout(@NotNull LayoutManager manager) {
        container.setLayout(manager);
    }

    public void addToContainer(@NotNull Container container, @Nullable Object constraints) {
        container.add(root, constraints);
    }

    public void removeFromContainer(@NotNull Container container) {
        container.remove(root);
    }
}
