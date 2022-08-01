package com.rubynaxela.onyx.gui.dialogs;

import com.rubynaxela.jadeite.collections.JArrays;
import com.rubynaxela.jadeite.collections.JVector;
import com.rubynaxela.jadeite.pointers.Pointers;
import com.rubynaxela.jadeite.util.OptionalGetter;
import com.rubynaxela.onyx.data.*;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.gui.components.Card;
import com.rubynaxela.onyx.gui.components.IconButton;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.ComponentUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import static com.rubynaxela.jadeite.swing.GridBagConstraintsBuilder.gbc;

public class OperationDialog extends Dialog {

    private final JTextField dateField, contractorField;
    private final JSpinner ordinalSelector;
    private final JPanel fragmentsPanel;
    private final OptionalGetter<Operation> operation;

    public OperationDialog(@Nullable Operation operation) {
        super(I18n.getString("title.dialog.new_operation"), JOptionPane.OK_CANCEL_OPTION);
        this.operation = OptionalGetter.of(operation);
        this.dateField = new JTextField();
        this.ordinalSelector = new JSpinner();
        this.contractorField = new JTextField();
        this.fragmentsPanel = new JPanel();
    }

    private void addFragmentPanel(@Nullable Operation.Fragment fragment) {
        final var panel = new FragmentPanel(fragment);
        fragmentsPanel.add(panel);
        fragmentsPanel.setBackground(panel.getColor());
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    @Override
    protected JComponent getContent() {
        final var panel = new JPanel(new GridBagLayout());

        panel.add(new JLabel(I18n.getString("label.dialog.operation_details")), gbc().width(4).extPadding(8, 0, 16, 0).build());

        final var dateLabel = new JLabel(I18n.getString("label.common.date"));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(dateLabel, gbc().row(1).fill(GridBagConstraints.HORIZONTAL).build());
        dateField.setText(operation.get(Operation::getDate).orElse(Date.today()).toString());
        panel.add(dateField, gbc().row(1).column(1).build());

        panel.add(new JLabel(I18n.getString("label.dialog.ordinal_in_week")), gbc().row(1).column(2).build());
        ordinalSelector.setModel(new SpinnerNumberModel(operation.get(Operation::getOrdinalInWeek).orElse(1).intValue(),
                                                        1, 100, 1));
        panel.add(ordinalSelector, gbc().row(1).column(3).build());

        final var contractorLabel = new JLabel(I18n.getString("label.common.contractor"));
        contractorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(contractorLabel, gbc().row(2).fill(GridBagConstraints.HORIZONTAL).build());
        contractorField.setText(operation.get(Operation::getContractor).orElse(""));
        panel.add(contractorField, gbc().row(2).column(1).width(3).fill(GridBagConstraints.HORIZONTAL).build());

        panel.add(new JLabel(I18n.getString("label.dialog.fragments")), gbc().row(3).width(4).build());
        final var addFragmentButton = new IconButton(I18n.getString("button.add"), MaterialIcons.ADD,
                                                     new Insets(2, 8, 2, 12), ComponentUtils.insetBottom(2));
        ComponentUtils.setFontSize(addFragmentButton, 12f);
        addFragmentButton.setIconSize(16f);
        addFragmentButton.addActionListener(e -> addFragmentPanel(null));
        panel.add(addFragmentButton, gbc().row(3).column(2).width(2).anchor(GridBagConstraints.EAST).build());

        fragmentsPanel.setLayout(new BoxLayout(fragmentsPanel, BoxLayout.Y_AXIS));
        panel.add(fragmentsPanel, gbc().row(4).width(4).fill(GridBagConstraints.HORIZONTAL).build());
        operation.get(Operation::getFragments).orElse(JVector.of((Operation.Fragment) null))
                 .forEach(this::addFragmentPanel);

        return panel;
    }

    @Override
    protected void valueSelected(@Nullable Object value) {
        if (Objects.equals(value, JOptionPane.OK_OPTION)) {
            final var operation = new Operation(Date.valueOf(dateField.getText()), (int) ordinalSelector.getValue(),
                                                contractorField.getText(),
                                                Arrays.stream(fragmentsPanel.getComponents())
                                                      .map(p -> ((FragmentPanel) p).get()).collect(JVector.collector()));
            Database.INSTANCE.addOperation(operation);
            ViewControllers.REFRESH_OPERATIONS_LIST.run();
        }
    }

    private static class BranchPanel extends Card {

        private final JComboBox<Category> categorySelector;
        private final JTextField amountField;
        private final OptionalGetter<Operation.Branch> branch;

        public BranchPanel(@Nullable Operation.Branch branch) {
            super(new GridBagLayout(), 4);
            ComponentUtils.addMargin(this, 4, 0, 0, 0);
            this.branch = OptionalGetter.of(branch);
            this.categorySelector = new JComboBox<>(JArrays.sortedCopy(Category.values(), Category[]::new,
                                                                       Comparator.comparing(I18n::getString)));
            this.amountField = new JTextField();
            fillData();
        }

        private void fillData() {

            final var categoryLabel = new JLabel(I18n.getString("label.common.category"));
            categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            add(categoryLabel, gbc().fill(GridBagConstraints.HORIZONTAL).build());
            categorySelector.setRenderer((list, value, index, isSelected, cellHasFocus) -> value.view);
            categorySelector.setSelectedItem(branch.get(Operation.Branch::getCategory).orElse(Category.OTHER));
            add(categorySelector, gbc().column(1).width(2).fill(GridBagConstraints.HORIZONTAL).build());

            final var amountLabel = new JLabel(I18n.getString("label.common.amount"));
            add(amountLabel, gbc().row(1).fill(GridBagConstraints.HORIZONTAL).build());
            amountField.setText(branch.get(Operation.Branch::getAmount).orElse(Monetary.ZERO).toString(""));
            amountField.setHorizontalAlignment(SwingConstants.RIGHT);
            add(amountField, gbc().row(1).column(1).fill(GridBagConstraints.HORIZONTAL).build());
            final var currencyLabel = new JLabel(Monetary.getSymbol());
            add(currencyLabel, gbc().row(1).column(2).fill(GridBagConstraints.HORIZONTAL).build());
        }

        public Operation.Branch get() {
            return new Operation.Branch(Pointers.requireNonNull(categorySelector.getSelectedItem()),
                                        Monetary.valueOf(amountField.getText()));
        }
    }

    private class FragmentPanel extends Card {

        private final JComboBox<Form> formSelector;
        private final JTextField descriptionField;
        private final JPanel branchesPanel;
        private final OptionalGetter<Operation.Fragment> fragment;

        public FragmentPanel(@Nullable Operation.Fragment fragment) {
            super(new GridBagLayout(), 4);
            ComponentUtils.addMargin(this, 4, 0, 0, 0);
            setColor(getColor().darker());
            this.fragment = OptionalGetter.of(fragment);
            this.formSelector = new JComboBox<>(JArrays.sortedCopy(Form.values(), Form[]::new,
                                                                   Comparator.comparingInt(f -> f.icon.getUnicode())));
            this.descriptionField = new JTextField();
            this.branchesPanel = new JPanel();
            fillData();
        }

        private void fillData() {

            final var formLabel = new JLabel(I18n.getString("label.common.form"));
            formLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            add(formLabel, gbc().fill(GridBagConstraints.HORIZONTAL).build());
            formSelector.setRenderer((list, value, index, isSelected, cellHasFocus) -> value.view);
            formSelector.setSelectedItem(fragment.get(Operation.Fragment::getForm).orElse(Form.OTHER));
            add(formSelector, gbc().column(1).fill(GridBagConstraints.HORIZONTAL).build());

            final var descriptionLabel = new JLabel(I18n.getString("label.common.description"));
            descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            add(descriptionLabel, gbc().row(1).fill(GridBagConstraints.HORIZONTAL).build());
            descriptionField.setText(fragment.get(Operation.Fragment::getDescription).orElse(""));
            add(descriptionField, gbc().row(1).column(1).fill(GridBagConstraints.HORIZONTAL).build());

            add(new JLabel(I18n.getString("label.dialog.categories")), gbc().row(2).width(2).build());
            final var addBranchButton = new IconButton(I18n.getString("button.add"), MaterialIcons.ADD,
                                                       new Insets(2, 8, 2, 12), ComponentUtils.insetBottom(2));
            ComponentUtils.setFontSize(addBranchButton, 12f);
            addBranchButton.setIconSize(16f);
            addBranchButton.addActionListener(e -> addBranchPanel(null));
            add(addBranchButton, gbc().row(2).column(1).anchor(GridBagConstraints.EAST).build());

            branchesPanel.setLayout(new BoxLayout(branchesPanel, BoxLayout.Y_AXIS));
            add(branchesPanel, gbc().row(3).width(2).fill(GridBagConstraints.HORIZONTAL).build());
            fragment.get(Operation.Fragment::getBranches).orElse(JVector.of((Operation.Branch) null))
                    .forEach(this::addBranchPanel);
        }

        private void addBranchPanel(@Nullable Operation.Branch branch) {
            final var panel = new BranchPanel(branch);
            branchesPanel.add(panel);
            branchesPanel.setBackground(panel.getColor());
            pack();
            setLocationRelativeTo(null);
            revalidate();
            repaint();
        }

        public Operation.Fragment get() {
            return new Operation.Fragment(Pointers.requireNonNull(formSelector.getSelectedItem()),
                                          descriptionField.getText(),
                                          Arrays.stream(branchesPanel.getComponents())
                                                .map(p -> ((BranchPanel) p).get()).collect(JVector.collector()));
        }
    }
}
