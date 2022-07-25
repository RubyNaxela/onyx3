package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.data.Operation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class OperationView extends JPanel {

    private final JLabel date, id, contractor;
    private Operation operation;

    public OperationView() {
        setPreferredSize(new Dimension(320, 640));
        date = new JLabel();
        id = new JLabel();
        contractor = new JLabel();
        add(date);
        add(id);
        add(contractor);
    }

    private void setText(@NotNull JLabel label, @NotNull Function<Operation, Object> getter) {
        label.setText(operation != null ? getter.apply(operation).toString() : "");
    }

    public void setOperation(@Nullable Operation operation) {
        this.operation = operation;
        setText(date, Operation::getDate);
        setText(id, Operation::wGetId);
        setText(contractor, Operation::getContractor);
    }
}
