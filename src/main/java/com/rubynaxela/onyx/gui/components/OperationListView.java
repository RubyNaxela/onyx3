package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.data.Operation;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.util.ComponentUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class OperationListView extends Card {

    private final Operation operation;

    public OperationListView(@NotNull Operation operation) {
        super(8);
        this.operation = operation;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        ComponentUtils.addMargin(this, 0, 0, 8, 0);

        final var leftIcon = operation.positive() ? new Icon(MaterialIcons.ARROW_UPWARD, 36f, Colors.INCOME_COLOR)
                                                  : new Icon(MaterialIcons.ARROW_DOWNWARD, 36f, Colors.EXPENSE_COLOR);
        ComponentUtils.addMargin(leftIcon, 4);
        add(leftIcon);
        add(createLeftPanel());
        add(createMiddlePanel());
        add(createRightPanel());
    }

    private JPanel createLeftPanel() {

        final var leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Colors.TRANSPARENT);
        leftPanel.setPreferredSize(new Dimension(128, 54));
        ComponentUtils.addMargin(leftPanel, 0, 8, 0, 8);

        final var dateLabel = new JLabel(operation.getDate().toString());
        dateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        ComponentUtils.setFontSize(dateLabel, 14f);
        leftPanel.add(dateLabel);

        final var amountLabel = new JLabel(operation.wGetTotalAmount().toString());
        amountLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        ComponentUtils.setFontParams(amountLabel, Font.BOLD, 18f);
        leftPanel.add(amountLabel);

        final var idLabel = new JLabel(operation.wGetId());
        idLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        idLabel.setForeground(Colors.withAlpha(idLabel.getForeground(), 0.5f));
        ComponentUtils.setFontSize(idLabel, 12f);
        leftPanel.add(idLabel);

        return leftPanel;
    }

    private JPanel createMiddlePanel() {

        final var middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBackground(Colors.TRANSPARENT);

        final var contractorAndFormPanel = new JPanel();
        contractorAndFormPanel.setLayout(new BoxLayout(contractorAndFormPanel, BoxLayout.X_AXIS));
        contractorAndFormPanel.setBackground(Colors.TRANSPARENT);

        operation.getFragments().stream().map(f -> f.getForm().icon).collect(Collectors.toSet())
                 .forEach(icon -> contractorAndFormPanel.add(new Icon(icon, 18f)));

        final var directionIcon = new Icon(operation.positive() ? MaterialIcons.KEYBOARD_DOUBLE_ARROW_LEFT
                                                                : MaterialIcons.KEYBOARD_DOUBLE_ARROW_RIGHT, 18f);
        ComponentUtils.addMargin(directionIcon, 0, 3, 0, 3);
        contractorAndFormPanel.add(directionIcon);

        final var contractorLabel = new JLabel(operation.getContractor());
        ComponentUtils.addMargin(contractorLabel, 0, 6, 0, 0);
        ComponentUtils.setFontSize(contractorLabel, 18f);
        contractorAndFormPanel.add(contractorLabel);

        middlePanel.add(contractorAndFormPanel, BorderLayout.NORTH);

        final var descriptionArea = new JTextArea(operation.wGetDescription());
        descriptionArea.setBackground(Colors.TRANSPARENT);
        descriptionArea.setForeground(Colors.withAlpha(descriptionArea.getForeground(), 0.8f));
        ComponentUtils.setFontParams(descriptionArea, Font.ITALIC, 12f);
        middlePanel.add(descriptionArea);

        return middlePanel;
    }

    private JPanel createRightPanel() {

        final var rightPanel = new JPanel();
        rightPanel.setBackground(Colors.TRANSPARENT);

        final var categories = operation.getFragments().stream().flatMap(f -> f.getBranches().stream())
                                        .map(b -> b.getCategory().icon).collect(Collectors.toSet());
        if (categories.size() == 1) {
            rightPanel.setLayout(new GridLayout(0, 1));
            categories.forEach(icon -> rightPanel.add(new Icon(icon, 36f)));
        } else {
            rightPanel.setLayout(new GridLayout(0, 2));
            categories.forEach(icon -> rightPanel.add(new Icon(icon, 18f)));
        }

        return rightPanel;
    }
}
