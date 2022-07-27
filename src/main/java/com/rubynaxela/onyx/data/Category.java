package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public enum Category {

    BANK_CHARGES_AND_INTERESTS(MaterialIcons.ACCOUNT_BALANCE),
    BEVERAGES(MaterialIcons.LOCAL_DRINK),
    BUILDING_MATERIALS(MaterialIcons.IMAGESEARCH_ROLLER),
    CLOTHING_AND_FOOTWEAR(MaterialIcons.CHECKROOM),
    COFFEE(MaterialIcons.COFFEE),
    COSMETICS(MaterialIcons.SOAP),
    ELECTRONIC_APPLIANCES(MaterialIcons.ELECTRICAL_SERVICES),
    ELECTRONIC_SERVICES(MaterialIcons.CLOUD_DONE),
    FINANCES(MaterialIcons.ATTACH_MONEY),
    FOOD(MaterialIcons.RAMEN_DINING),
    GROCERIES(MaterialIcons.EGG),
    ICECREAM_AND_DESSERTS(MaterialIcons.ICECREAM),
    INTERNAL_TRANSFER(MaterialIcons.TRANSFORM),
    INVOICES(MaterialIcons.RECEIPT_LONG),
    MEDICINES_AND_HEALTH(MaterialIcons.HEALING),
    ONE_TIME_INCOME(MaterialIcons.REDEEM),
    OTHER(MaterialIcons.GRID_VIEW),
    REGULAR_INCOME(MaterialIcons.CALENDAR_MONTH),
    RESALE(MaterialIcons.MOVE_UP),
    TEA(MaterialIcons.EMOJI_FOOD_BEVERAGE),
    TOOLS(MaterialIcons.HANDYMAN),
    TRANSPORTATION(MaterialIcons.EMOJI_TRANSPORTATION);

    public final IconCode icon;
    public final ComboBoxView view;

    Category(@NotNull IconCode icon) {
        this.icon = icon;
        this.view = new ComboBoxView();
    }

    public class ComboBoxView extends JLabel {

        public ComboBoxView() {
            super(I18n.getString(Category.this));
            setIcon(IconFontSwing.buildIcon(icon, getFont().getSize(), getForeground()));
        }
    }
}
