package com.rubynaxela.onyx;

import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.window.MainWindow;
import com.rubynaxela.onyx.io.I18n;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.util.Locale;

/**
 * Application entry point class.
 */
public class Onyx {

    /**
     * Application entry point
     *
     * @param args unused
     */
    public static void main(String[] args) {
        I18n.setLocale(Locale.forLanguageTag("pl-PL"));
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        IconFontSwing.register(MaterialIcons.getIconFont());
        FlatDarkPurpleIJTheme.setup();
        UIManager.put("Component.arrowType", "chevron");
        new MainWindow().setVisible(true);

//        final var branch = new Operation.Branch(Category.OTHER, Monetary.valueOf(50f));
//        final var fragment = new Operation.Fragment("Test description", List.of(branch));
//        Database.INSTANCE.addOperation(new Operation(Utils.parseDate("2022/07/24"), 0, "Test", List.of(fragment)));
//        Database.INSTANCE.addOperation(new Operation(Utils.parseDate("2022/07/24"), 1, "Test", List.of(fragment)));
    }
}