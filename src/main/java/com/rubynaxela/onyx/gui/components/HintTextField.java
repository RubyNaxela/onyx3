package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.jadeite.awt.JColor;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {

    private final String hintText;
    private final JColor baseForeground;
    private boolean hintDisplayed = true;

    public HintTextField(@Nullable String hint) {
        super(hint);
        this.hintText = hint;
        this.baseForeground = new JColor(getForeground());
        setForeground(baseForeground.withAlpha(0.5f));
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
            setForeground(baseForeground.withAlpha(0.5f));
            hintDisplayed = true;
        }
    }
}
