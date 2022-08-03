package com.rubynaxela.onyx.data;

import com.rubynaxela.jadeite.awt.JColor;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public enum Form {

    ATM_DEPOSIT(MaterialIcons.ATM, new JColor(145, 198, 238)),
    ATM_WITHDRAWAL(MaterialIcons.ATM, new JColor(145, 198, 238)),
    BLIK_PAYMENT(MaterialIcons.SMARTPHONE, new JColor(215, 85, 111)),
    CARD_PAYMENT(MaterialIcons.CREDIT_CARD, new JColor(255, 198, 0)),
    CARD_REVENUE(MaterialIcons.CREDIT_CARD, new JColor(255, 198, 0)),
    CASH_PAYMENT(MaterialIcons.PAYMENTS, new JColor(62, 140, 69)),
    CASH_REVENUE(MaterialIcons.PAYMENTS, new JColor(62, 140, 69)),
    INCOMING_TRANSFER(MaterialIcons.ACCOUNT_BALANCE, new JColor(255, 98, 0)),
    OTHER(MaterialIcons.GRID_VIEW, new JColor(190, 190, 190)),
    OUTGOING_TRANSFER(MaterialIcons.ACCOUNT_BALANCE, new JColor(255, 98, 0));

    public final IconCode icon;
    public final JColor color;
    public final ComboBoxView view;

    Form(@NotNull IconCode icon, @NotNull JColor color) {
        this.icon = icon;
        this.color = color;
        this.view = new ComboBoxView();
    }

    public class ComboBoxView extends JLabel {

        public ComboBoxView() {
            super(I18n.getString(Form.this));
            setIcon(IconFontSwing.buildIcon(icon, getFont().getSize(), color));
        }
    }
}
