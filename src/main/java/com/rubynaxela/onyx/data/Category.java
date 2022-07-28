package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public enum Category {

    BANK_CHARGES_AND_INTERESTS(MaterialIcons.ACCOUNT_BALANCE, new Color(255, 98, 0)),
    BEVERAGES(MaterialIcons.LOCAL_DRINK, new Color(39, 81, 184)),
    BUILDING_MATERIALS(MaterialIcons.IMAGESEARCH_ROLLER, new Color(230, 230, 230)),
    CLOTHING_AND_FOOTWEAR(MaterialIcons.CHECKROOM, new Color(221, 92, 212)),
    COFFEE(MaterialIcons.COFFEE, new Color(204, 170, 140)),
    COSMETICS(MaterialIcons.SOAP, new Color(185, 94, 221)),
    ELECTRONIC_APPLIANCES(MaterialIcons.ELECTRICAL_SERVICES, new Color(160, 160, 160)),
    ELECTRONIC_SERVICES(MaterialIcons.CLOUD_DONE, new Color(104, 150, 243)),
    FINANCES(MaterialIcons.ATTACH_MONEY, new Color(4, 215, 206)),
    FOOD(MaterialIcons.RAMEN_DINING, new Color(255, 200, 57)),
    GROCERIES(MaterialIcons.EGG, new Color(204, 146, 107)),
    ICECREAM_AND_DESSERTS(MaterialIcons.ICECREAM, new Color(255, 212, 238)),
    INTERNAL_TRANSFER(MaterialIcons.TRANSFORM, new Color(186, 86, 246)),
    INVOICES(MaterialIcons.RECEIPT_LONG, new Color(157, 138, 238)),
    MEDICINES_AND_HEALTH(MaterialIcons.HEALING, new Color(255, 0, 80)),
    ONE_TIME_INCOME(MaterialIcons.REDEEM, new Color(159, 198, 162)),
    OTHER(MaterialIcons.GRID_VIEW, new Color(190, 190, 190)),
    REGULAR_INCOME(MaterialIcons.CALENDAR_MONTH, new Color(62, 140, 69)),
    RESALE(MaterialIcons.MOVE_UP, new Color(255, 97, 177)),
    TEA(MaterialIcons.EMOJI_FOOD_BEVERAGE, new Color(79, 141, 107)),
    TOOLS(MaterialIcons.HANDYMAN, new Color(255, 0, 32)),
    TRANSPORTATION(MaterialIcons.EMOJI_TRANSPORTATION, new Color(55, 151, 216));

    public final IconCode icon;
    public final Color color;
    public final ComboBoxView view;

    Category(@NotNull IconCode icon, @NotNull Color color) {
        this.icon = icon;
        this.color = color;
        this.view = new ComboBoxView();
    }

    public class ComboBoxView extends JLabel {

        public ComboBoxView() {
            super(I18n.getString(Category.this));
            setIcon(IconFontSwing.buildIcon(icon, getFont().getSize(), color));
        }
    }
}
