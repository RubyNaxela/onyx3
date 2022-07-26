package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.gui.MaterialIcons;
import jiconfont.IconCode;
import org.jetbrains.annotations.NotNull;

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

    Form(@NotNull IconCode icon) {
        this.icon = icon;
    }
}
