package com.rubynaxela.onyx.gui.dialogs;

import com.rubynaxela.onyx.gui.window.TitleBar;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.JOptionPane.*;

public abstract class Dialog extends JDialog {

    private final int optionType;

    protected Dialog(@NotNull String title, @MagicConstant(intValues = {DEFAULT_OPTION, YES_NO_OPTION, YES_NO_CANCEL_OPTION,
                                                                        OK_CANCEL_OPTION}) int optionType) {
        super((Frame) null, title);
        this.optionType = optionType;
        setUndecorated(true);
        setLayout(new BorderLayout());
        add(new TitleBar(this), BorderLayout.NORTH);
    }

    public void display() {
        final var optionPane = new JOptionPane(getContent(), JOptionPane.PLAIN_MESSAGE, optionType);
        optionPane.addPropertyChangeListener(e -> {
            if (Objects.equals(e.getPropertyName(), "value")) {
                valueSelected(e.getNewValue());
                dispose();
            }
        });
        add(optionPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected abstract JComponent getContent();

    protected abstract void valueSelected(@Nullable Object value);
}
