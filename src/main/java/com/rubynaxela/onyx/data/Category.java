package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public enum Category {

    BANK_CHARGES_AND_INTERESTS(MaterialIcons.ACCOUNT_BALANCE, new Color(255, 98, 0), true),
    BEVERAGES(MaterialIcons.LOCAL_DRINK, new Color(39, 81, 184), true),
    BUILDING_MATERIALS(MaterialIcons.IMAGESEARCH_ROLLER, new Color(230, 230, 230), true),
    CLOTHING_AND_FOOTWEAR(MaterialIcons.CHECKROOM, new Color(221, 92, 212), true),
    COFFEE(MaterialIcons.COFFEE, new Color(204, 170, 140), true),
    COSMETICS(MaterialIcons.SOAP, new Color(185, 94, 221), true),
    ELECTRONIC_APPLIANCES(MaterialIcons.ELECTRICAL_SERVICES, new Color(160, 160, 160), true),
    ELECTRONIC_SERVICES(MaterialIcons.CLOUD_DONE, new Color(104, 150, 243), true),
    FINANCES(MaterialIcons.ATTACH_MONEY, new Color(4, 215, 206), false),
    FOOD(MaterialIcons.RAMEN_DINING, new Color(255, 156, 24), true),
    GIFTS(MaterialIcons.REDEEM, new Color(255, 200, 57), true),
    GROCERIES(MaterialIcons.EGG, new Color(204, 146, 107), true),
    ICECREAM_AND_DESSERTS(MaterialIcons.ICECREAM, new Color(255, 212, 238), true),
    INTERNAL_TRANSFER(MaterialIcons.TRANSFORM, new Color(186, 86, 246), false),
    INVOICES(MaterialIcons.RECEIPT_LONG, new Color(157, 138, 238), true),
    MEDICINES_AND_HEALTH(MaterialIcons.HEALING, new Color(255, 0, 80), true),
    ONE_TIME_INCOME(MaterialIcons.EVENT, new Color(159, 198, 162), true),
    OTHER(MaterialIcons.GRID_VIEW, new Color(190, 190, 190), false),
    REGULAR_INCOME(MaterialIcons.CALENDAR_MONTH, new Color(62, 140, 69), true),
    RESALE(MaterialIcons.MOVE_UP, new Color(255, 97, 177), false),
    TEA(MaterialIcons.EMOJI_FOOD_BEVERAGE, new Color(79, 141, 107), true),
    TOOLS(MaterialIcons.HANDYMAN, new Color(255, 0, 32), true),
    TRANSPORTATION(MaterialIcons.EMOJI_TRANSPORTATION, new Color(55, 151, 216), true);

    public final IconCode icon;
    public final Color color;
    public final boolean analyzed;
    public final ComboBoxView view;

    Category(@NotNull IconCode icon, @NotNull Color color, boolean analyzed) {
        this.icon = icon;
        this.color = color;
        this.analyzed = analyzed;
        this.view = new ComboBoxView();
    }

    public class ComboBoxView extends JLabel {

        public ComboBoxView() {
            super(I18n.getString(Category.this));
            setIcon(IconFontSwing.buildIcon(icon, getFont().getSize(), color));
        }
    }
}
