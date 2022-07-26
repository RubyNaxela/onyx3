package com.rubynaxela.onyx.gui;

import jiconfont.IconCode;
import jiconfont.IconFont;

import java.io.InputStream;

@SuppressWarnings("unused")
public enum MaterialIcons implements IconCode {

    ACCOUNT_BALANCE('\ue84f'),
    ACCOUNT_BALANCE_WALLET('\ue850'),
    ADD('\ue145'),
    ARROW_DOWNWARD('\ue5db'),
    ARROW_UPWARD('\ue5d8'),
    ATM('\ue573'),
    ATTACH_MONEY('\ue227'),
    CALENDAR_MONTH('\uebcc'),
    CHECKROOM('\uf19e'),
    CLOUD_DONE('\ue2bf'),
    COFFEE('\uefef'),
    CREDIT_CARD('\ue870'),
    ELECTRICAL_SERVICES('\uf102'),
    EMOJI_FOOD_BEVERAGE('\uea1b'),
    EMOJI_TRANSPORTATION('\uea1f'),
    EGG('\ueacc'),
    GRID_VIEW('\ue9b0'),
    HANDYMAN('\uf10b'),
    HEALING('\ue3f3'),
    HISTORY('\ue889'),
    HOME('\ue88a'),
    ICECREAM('\uea69'),
    IMAGESEARCH_ROLLER('\ue9b4'),
    KEYBOARD_DOUBLE_ARROW_LEFT('\ueac3'),
    KEYBOARD_DOUBLE_ARROW_RIGHT('\ueac9'),
    LOCAL_DRINK('\ue544'),
    MOVE_UP('\ueb64'),
    PAYMENTS('\uef63'),
    PENDING_ACTIONS('\uf1bb'),
    RAMEN_DINING('\uea64'),
    RECEIPT_LONG('\uef6e'),
    REDEEM('\ue8b1'),
    SAVINGS('\ue2eb'),
    SMARTPHONE('\ue32c'),
    SOAP('\uf1b2'),
    SYNC_ALT('\uea18'),
    TRANSFORM('\ue428');

    private final char character;

    MaterialIcons(char character) {
        this.character = character;
    }

    public static IconFont getIconFont() {
        return new IconFont() {
            @Override
            public String getFontFamily() {
                return "Material Icons Round";
            }

            @Override
            public InputStream getFontInputStream() {
                return MaterialIcons.class.getResourceAsStream("/res/MaterialIconsRound-Regular.ttf");
            }
        };
    }

    @Override
    public char getUnicode() {
        return character;
    }

    @Override
    public String getFontFamily() {
        return "Material Icons Round";
    }
}
