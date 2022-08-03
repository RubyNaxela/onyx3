package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.jadeite.awt.JGridBagConstraints;
import com.rubynaxela.jadeite.pointers.IntPointer;
import com.rubynaxela.onyx.data.Operation;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.util.ComponentUtils;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import static com.rubynaxela.jadeite.awt.JGridBagConstraints.gbc;

public class OperationView extends JPanel {

    private static final JGridBagConstraints
            labelGBC = gbc().weightX(1).fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.WEST),
            fragmentPanelGBC = gbc().column(0).weight(1, 1).extPaddingTop(8)
                                    .fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.NORTH);
    private final JLabel title, dateLabel, date, contractorLabel, contractor, totalAmountLabel, totalAmount;
    private final JPanel fragmentsPanel;

    public OperationView() {

        super(new GridBagLayout());
        setPreferredSize(new Dimension(320, 640));

        this.title = new JLabel();
        this.dateLabel = new JLabel();
        this.date = new JLabel();
        this.contractorLabel = new JLabel();
        this.contractor = new JLabel();
        this.totalAmountLabel = new JLabel();
        this.totalAmount = new JLabel();
        this.fragmentsPanel = new JPanel();

        add(createBriefCard(), gbc().weightX(1).fill(GridBagConstraints.HORIZONTAL));
        add(fragmentsPanel, gbc().row(1).weightX(1).fill(GridBagConstraints.HORIZONTAL));
        fragmentsPanel.setLayout(new GridBagLayout());
    }

    private static Card createFragmentPanel(Operation.Fragment fragment) {

        final var fragmentPanel = new Card(new GridBagLayout());

        final int shift = fragment.getDescription().isEmpty() ? 0 : 1;
        if (shift == 1) {

            final var descriptionLabel = new JLabel(I18n.getString("label.common.description"));
            ComponentUtils.setFontSize(descriptionLabel, 14f);
            fragmentPanel.add(descriptionLabel, labelGBC.row(0));

            final var description = new JLabel(fragment.getDescription());
            ComponentUtils.setFontParams(description, Font.BOLD + Font.ITALIC, 12f);
            fragmentPanel.add(description, gbc().position(1, 0).extPadding(4).anchor(GridBagConstraints.EAST));
        }

        final var formLabel = new JLabel(I18n.getString("label.common.form"));
        ComponentUtils.setFontSize(formLabel, 14f);
        fragmentPanel.add(formLabel, labelGBC.row(shift));

        final var fragmentForm = fragment.getForm();
        final var form = new JLabel(I18n.getString(fragmentForm));
        ComponentUtils.setFontParams(form, Font.BOLD, 14f);
        form.setIcon(IconFontSwing.buildIcon(fragmentForm.icon, 24f, fragmentForm.color));
        fragmentPanel.add(form, gbc().position(1, shift).extPadding(4).anchor(GridBagConstraints.EAST));

        final var branches = fragment.getBranches();
        final var categoryLabel = new JLabel(branches.size() == 1 ? I18n.getString("label.common.category")
                                                                  : I18n.getString("label.common.categories"));
        ComponentUtils.setFontSize(categoryLabel, 14f);
        fragmentPanel.add(categoryLabel, labelGBC.row(1 + shift));

        final var categories = new JPanel(new GridBagLayout());
        categories.setBackground(Colors.TRANSPARENT);
        final IntPointer i = IntPointer.to(0);
        branches.forEach(b -> {
            final var c = b.getCategory();
            final var category = new JLabel(I18n.getString(c));
            ComponentUtils.setFontParams(category, Font.BOLD, 14f);
            category.setIcon(IconFontSwing.buildIcon(c.icon, 24f, c.color));
            categories.add(category, labelGBC.row(i.get()));

            final var amount = new JLabel(b.getAmount().toString());
            ComponentUtils.setFontSize(amount, 14f);
            categories.add(amount, gbc().position(1, i.get()).anchor(GridBagConstraints.EAST));

            i.incrementPostfix();
        });
        fragmentPanel.add(categories, gbc().row(2 + shift).width(2).extPadding(4).weightX(1)
                                           .fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.WEST));

        return fragmentPanel;
    }

    private Card createBriefCard() {

        final var brief = new Card(new GridBagLayout());

        ComponentUtils.setFontParams(title, Font.BOLD, 18f);
        brief.add(title, gbc().width(2).extPaddingBottom(8));

        ComponentUtils.setFontSize(dateLabel, 14f);
        brief.add(dateLabel, labelGBC.row(1));
        ComponentUtils.setFontParams(date, Font.BOLD, 14f);
        brief.add(date, gbc().position(1, 1).extPadding(4).anchor(GridBagConstraints.EAST));

        ComponentUtils.setFontSize(contractorLabel, 14f);
        brief.add(contractorLabel, labelGBC.row(2));
        ComponentUtils.setFontParams(contractor, Font.BOLD, 14f);
        brief.add(contractor, gbc().position(1, 2).extPadding(4).anchor(GridBagConstraints.EAST));

        ComponentUtils.setFontSize(totalAmountLabel, 14f);
        brief.add(totalAmountLabel, labelGBC.row(3));
        ComponentUtils.setFontParams(totalAmount, Font.BOLD, 14f);
        brief.add(totalAmount, gbc().position(1, 3).extPadding(4, 4, 0, 4).anchor(GridBagConstraints.EAST));

        return brief;
    }

    public void setOperation(@NotNull Operation operation) {
        title.setText(String.format(I18n.getString("title.panel.operation_[id]"), operation.wGetId()));
        dateLabel.setText(I18n.getString("label.common.date"));
        date.setText(operation.getDate().toString());
        contractorLabel.setText(I18n.getString("label.common.contractor"));
        contractor.setText(operation.getContractor());
        totalAmountLabel.setText(I18n.getString("label.card.total_amount"));
        totalAmount.setText(operation.wGetTotalAmount().toString());
        fragmentsPanel.removeAll();
        final IntPointer i = IntPointer.to(0);
        operation.getSFragments().forEach(f -> fragmentsPanel.add(createFragmentPanel(f),
                                                                  fragmentPanelGBC.row(i.incrementPostfix())));
        revalidate();
        repaint();
    }
}
