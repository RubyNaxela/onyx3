package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public enum Form {

    ATM_DEPOSIT(MaterialIcons.ATM),
    ATM_WITHDRAWAL(MaterialIcons.ATM),
    BLIK_PAYMENT(MaterialIcons.SMARTPHONE),
    CARD_PAYMENT(MaterialIcons.CREDIT_CARD),
    CARD_REVENUE(MaterialIcons.CREDIT_CARD),
    CASH_PAYMENT(MaterialIcons.PAYMENTS),
    CASH_REVENUE(MaterialIcons.PAYMENTS),
    INCOMING_TRANSFER(MaterialIcons.ACCOUNT_BALANCE),
    OTHER(MaterialIcons.GRID_VIEW),
    OUTGOING_TRANSFER(MaterialIcons.ACCOUNT_BALANCE);

    public final IconCode icon;
    public final ComboBoxView view;

    Form(@NotNull IconCode icon) {
        this.icon = icon;
        this.view = new ComboBoxView();
    }

    public class ComboBoxView extends JLabel {

        public ComboBoxView() {
            super(I18n.getString(Form.this));
            setIcon(IconFontSwing.buildIcon(icon, getFont().getSize(), getForeground()));
        }
    }
}
