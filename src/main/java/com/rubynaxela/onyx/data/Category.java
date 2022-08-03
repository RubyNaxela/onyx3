package com.rubynaxela.onyx.data;

import com.rubynaxela.jadeite.awt.JColor;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.IconCode;
import jiconfont.swing.IconFontSwing;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public enum Category {

    BANK_CHARGES_AND_INTERESTS(MaterialIcons.ACCOUNT_BALANCE, new JColor(255, 98, 0), true, false),
    BEVERAGES(MaterialIcons.LOCAL_DRINK, new JColor(39, 81, 184), true, true),
    BUILDING_MATERIALS(MaterialIcons.IMAGESEARCH_ROLLER, new JColor(230, 230, 230), true, true),
    CLOTHING_AND_FOOTWEAR(MaterialIcons.CHECKROOM, new JColor(221, 92, 212), true, true),
    COFFEE(MaterialIcons.COFFEE, new JColor(204, 170, 140), true, true),
    COSMETICS(MaterialIcons.SOAP, new JColor(185, 94, 221), true, true),
    DESSERTS_AND_SNACKS(MaterialIcons.ICECREAM, new JColor(255, 212, 238), true, true),
    ELECTRONIC_APPLIANCES(MaterialIcons.ELECTRICAL_SERVICES, new JColor(160, 160, 160), true, true),
    ELECTRONIC_SERVICES(MaterialIcons.CLOUD_DONE, new JColor(104, 150, 243), true, true),
    FINANCES(MaterialIcons.ATTACH_MONEY, new JColor(4, 215, 206), false, true),
    FOOD(MaterialIcons.RAMEN_DINING, new JColor(255, 156, 24), true, true),
    GIFTS(MaterialIcons.REDEEM, new JColor(255, 200, 57), true, true),
    GROCERIES(MaterialIcons.EGG, new JColor(204, 146, 107), true, true),
    INTERNAL_TRANSFER(MaterialIcons.TRANSFORM, new JColor(186, 86, 246), false, false),
    INVOICES(MaterialIcons.RECEIPT_LONG, new JColor(157, 138, 238), true, true),
    MEDICINES_AND_HEALTH(MaterialIcons.HEALING, new JColor(255, 0, 80), true, true),
    ONE_TIME_INCOME(MaterialIcons.EVENT, new JColor(159, 198, 162), true, false),
    OTHER(MaterialIcons.GRID_VIEW, new JColor(190, 190, 190), false, false),
    REGULAR_INCOME(MaterialIcons.CALENDAR_MONTH, new JColor(62, 140, 69), true, false),
    RESALE(MaterialIcons.MOVE_UP, new JColor(255, 97, 177), false, true),
    TEA(MaterialIcons.EMOJI_FOOD_BEVERAGE, new JColor(79, 141, 107), true, true),
    TOOLS(MaterialIcons.HANDYMAN, new JColor(255, 0, 32), true, true),
    TRANSPORTATION(MaterialIcons.EMOJI_TRANSPORTATION, new JColor(55, 151, 216), true, true);

    public final IconCode icon;
    public final JColor color;
    public final boolean analyzed, incomesAreRedunds;
    public final ComboBoxView view;

    Category(@NotNull IconCode icon, @NotNull JColor color, boolean analyzed, boolean incomesAreRedunds) {
        this.icon = icon;
        this.color = color;
        this.analyzed = analyzed;
        this.incomesAreRedunds = incomesAreRedunds;
        this.view = new ComboBoxView();
    }

    public class ComboBoxView extends JLabel {

        public ComboBoxView() {
            super(I18n.getString(Category.this));
            setIcon(IconFontSwing.buildIcon(icon, getFont().getSize(), color));
        }
    }
}
