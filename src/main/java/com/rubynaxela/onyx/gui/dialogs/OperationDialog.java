package com.rubynaxela.onyx.gui.dialogs;

import com.rubynaxela.onyx.data.*;
import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.OptionalGetter;
import com.rubynaxela.onyx.util.VectorConstr;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

import static com.rubynaxela.onyx.gui.GridBagConstraintsBuilder.gbc;

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
        fragmentsPanel.add(new FragmentPanel(fragment));
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

        panel.add(new JLabel(I18n.getString("label.dialog.fragments")), gbc().row(3).width(4)
                                                                             .fill(GridBagConstraints.HORIZONTAL).build());

        panel.add(fragmentsPanel, gbc().row(4).width(4).fill(GridBagConstraints.HORIZONTAL).build());
        operation.get(Operation::getFragments).orElse(VectorConstr.of((Operation.Fragment) null))
                 .forEach(this::addFragmentPanel);

        return panel;
    }

    @Override
    protected void valueSelected(@Nullable Object value) {
        if (Objects.equals(value, JOptionPane.OK_OPTION)) {
            final var operation = new Operation(Date.valueOf(dateField.getText()), (int) ordinalSelector.getValue(),
                                                contractorField.getText(),
                                                Arrays.stream(fragmentsPanel.getComponents())
                                                      .map(p -> ((FragmentPanel) p).get()).collect(VectorConstr.collector()));
            Database.INSTANCE.addOperation(operation);
            ViewControllers.REFRESH_OPERATIONS_LIST.run();
        }
    }

    private static class BranchPanel extends JPanel {

        private final JTextField categoryField;
        private final JTextField amountField;
        private final OptionalGetter<Operation.Branch> branch;

        public BranchPanel(@Nullable Operation.Branch branch) {
            this.branch = OptionalGetter.of(branch);
            this.categoryField = new JTextField();
            this.amountField = new JTextField();
            fillData();
        }

        private void fillData() {
            categoryField.setText(I18n.getString(branch.get(Operation.Branch::getCategory).orElse(Category.OTHER)).toLowerCase());
            add(categoryField);
            amountField.setText(branch.get(Operation.Branch::getAmount).orElse(Monetary.ZERO).toString());
            add(amountField);
        }

        public Operation.Branch get() {
            return new Operation.Branch(Arrays.stream(Category.values())
                                              .filter(c -> I18n.getString(c).equalsIgnoreCase(categoryField.getText()))
                                              .findFirst().orElse(Category.OTHER),
                                        Monetary.valueOf(amountField.getText()));
        }
    }

    private class FragmentPanel extends JPanel {

        private final JTextField formField;
        private final JTextField descriptionField;
        private final JPanel branchesPanel;
        private final OptionalGetter<Operation.Fragment> fragment;

        public FragmentPanel(@Nullable Operation.Fragment fragment) {
            this.fragment = OptionalGetter.of(fragment);
            this.formField = new JTextField();
            this.descriptionField = new JTextField();
            this.branchesPanel = new JPanel();
            fillData();
        }

        private void fillData() {
            formField.setText(I18n.getString(fragment.get(Operation.Fragment::getType)
                                                     .orElse(Form.OTHER)).toLowerCase());
            add(formField);
            descriptionField.setText(fragment.get(Operation.Fragment::getDescription).orElse(""));
            add(descriptionField);
            add(branchesPanel);
            fragment.get(Operation.Fragment::getBranches).orElse(VectorConstr.of((Operation.Branch) null))
                    .forEach(this::addBranchPanel);
        }

        private void addBranchPanel(@Nullable Operation.Branch branch) {
            branchesPanel.add(new BranchPanel(branch));
            pack();
            setLocationRelativeTo(null);
            revalidate();
            repaint();
        }

        public Operation.Fragment get() {
            return new Operation.Fragment(Arrays.stream(Form.values())
                                                .filter(f -> I18n.getString(f).equalsIgnoreCase(formField.getText()))
                                                .findFirst().orElse(Form.OTHER),
                                          descriptionField.getText(),
                                          Arrays.stream(branchesPanel.getComponents())
                                                .map(p -> ((BranchPanel) p).get()).collect(VectorConstr.collector()));
        }
    }
}
