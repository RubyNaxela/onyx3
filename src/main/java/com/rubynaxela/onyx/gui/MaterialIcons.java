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
    HISTORY('\ue889'),
    GRID_VIEW('\ue9b0'),
    HOME('\ue88a'),
    PAYMENTS('\uef63'),
    PENDING_ACTIONS('\uf1bb'),
    RAMEN_DINING('\uea64'),
    RECEIPT_LONG('\uef6e'),
    SAVINGS('\ue2eb');

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
