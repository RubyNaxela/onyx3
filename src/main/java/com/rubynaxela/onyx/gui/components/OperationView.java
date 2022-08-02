package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.jadeite.util.OptionalGetter;
import com.rubynaxela.onyx.data.Operation;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class OperationView extends JPanel {

    private final JLabel date, id, contractor;
    private OptionalGetter<Operation> operation;

    public OperationView() {
        this.date = new JLabel();
        this.id = new JLabel();
        this.contractor = new JLabel();
        this.operation = OptionalGetter.empty();
        setPreferredSize(new Dimension(320, 640));
        add(date);
        add(id);
        add(contractor);
    }

    public void setOperation(@Nullable Operation op) {
        this.operation = OptionalGetter.ofNullable(op);
        date.setText(operation.getString(Operation::getDate).orElse(""));
        id.setText(operation.get(Operation::wGetId).orElse(""));
        contractor.setText(operation.get(Operation::getContractor).orElse(""));
    }
}
