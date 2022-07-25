package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.data.Operation;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class OperationListView extends Card {

    public OperationListView(@NotNull Operation operation) {
        super(8);
        ComponentUtils.addMargin(this, 0, 0, 8, 0);

        add(new Icon(operation.positive() ? MaterialIcons.ARROW_UPWARD : MaterialIcons.ARROW_DOWNWARD));

        final var numbersPanel = new JPanel();
        numbersPanel.setLayout(new BoxLayout(numbersPanel, BoxLayout.Y_AXIS));
        final var idLabel = new JLabel(operation.wGetId());
        final var amountLabel = new JLabel(operation.wGetTotalAmount().toString());
        final var dateLabel = new JLabel(operation.getDate().toString());
    }
}
