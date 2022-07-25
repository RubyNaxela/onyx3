package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Colors;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {

    private final String hintText;
    private boolean hintDisplayed = true;
    private Color baseForeground;

    public HintTextField(@Nullable String hint) {
        super(hint);
        this.hintText = hint;
        this.baseForeground = getForeground();
        setForeground(Colors.withAlpha(baseForeground, 0.5f));
        addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (hintDisplayed) setText("");
        setForeground(baseForeground);
        hintDisplayed = false;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            setText(hintText);
            setForeground(Colors.withAlpha(baseForeground, 0.5f));
            hintDisplayed = true;
        }
    }
}
