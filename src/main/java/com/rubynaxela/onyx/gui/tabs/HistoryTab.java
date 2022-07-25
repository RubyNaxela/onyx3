package com.rubynaxela.onyx.gui.tabs;

import com.rubynaxela.onyx.data.Database;
import com.rubynaxela.onyx.data.Operation;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.components.HintTextField;
import com.rubynaxela.onyx.gui.components.IconButton;
import com.rubynaxela.onyx.gui.components.OperationListView;
import com.rubynaxela.onyx.gui.components.OperationView;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.ComponentUtils;

import javax.swing.*;
import java.awt.*;

public class HistoryTab extends WindowTab {

    private final HintTextField searchField;
    private final IconButton registerButton;
    private final OperationView operationPreviewPanel;

    public HistoryTab() {

        setLayout(new BorderLayout(8, 16));

        searchField = new HintTextField(I18n.getString("label.tooltip.search"));
        registerButton = new IconButton(I18n.getString("button.register_operation"), MaterialIcons.ADD,
                                        new Insets(8, 4, 4, 8), new Insets(0, 0, 4, 0));
        operationPreviewPanel = new OperationView();

        add(createTopPanel(), BorderLayout.NORTH);
        add(createOperationsList(), BorderLayout.CENTER);
        add(operationPreviewPanel, BorderLayout.EAST);
    }

    private JPanel createTopPanel() {

        final var topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(16, 0));

        ComponentUtils.setFontSize(searchField, 14f);
        topPanel.add(searchField, BorderLayout.CENTER);

        ComponentUtils.setFontSize(registerButton, 14f);
        topPanel.add(registerButton, BorderLayout.EAST);

        return topPanel;
    }

    private JList<Operation> createOperationsList() {
        final var operations = Database.INSTANCE.getOperations();
        final var listView = new JList<Operation>();
        listView.setLayout(new GridBagLayout());
        listView.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new OperationListView(value));
        listView.setListData(operations);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listView.addListSelectionListener(e -> operationPreviewPanel.setOperation(operations.get(e.getFirstIndex())));
        return listView;
    }
}
