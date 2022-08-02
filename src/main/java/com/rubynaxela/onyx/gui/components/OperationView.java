package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.data.Operation;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.ComponentUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

import static com.rubynaxela.jadeite.awt.JGridBagConstraints.gbc;

public class OperationView extends JPanel {

    private final JLabel title, dateLabel, date, contractorLabel, contractor;

    public OperationView() {

        super(new GridBagLayout());
        setPreferredSize(new Dimension(320, 640));

        this.title = new JLabel();
        this.dateLabel = new JLabel();
        this.date = new JLabel();
        this.contractor = new JLabel();
        this.contractorLabel = new JLabel();

        final var brief = new Card(new GridBagLayout());
        add(brief, gbc().weightX(1).fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.NORTH));

        ComponentUtils.setFontParams(title, Font.BOLD, 18f);
        brief.add(title, gbc().width(2));

        ComponentUtils.setFontSize(dateLabel, 14f);
        brief.add(dateLabel, gbc().row(1).weightX(1).fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.WEST));
        ComponentUtils.setFontParams(date, Font.BOLD, 14f);
        brief.add(date, gbc().position(1, 1).extPadding(6).anchor(GridBagConstraints.EAST));

        ComponentUtils.setFontSize(contractorLabel, 14f);
        brief.add(contractorLabel, gbc().row(2).weightX(1).fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.WEST));
        ComponentUtils.setFontParams(contractor, Font.BOLD, 14f);
        brief.add(contractor, gbc().position(1, 2).anchor(GridBagConstraints.EAST));
    }

    public void setOperation(@Nullable Operation op) {
        if (op != null) {
            title.setText(String.format(I18n.getString("title.panel.operation_[id]"), op.wGetId()));
            dateLabel.setText(I18n.getString("label.common.date"));
            date.setText(op.getDate().toString());
            contractorLabel.setText(I18n.getString("label.common.contractor"));
            contractor.setText(op.getContractor());
        }
    }
}
