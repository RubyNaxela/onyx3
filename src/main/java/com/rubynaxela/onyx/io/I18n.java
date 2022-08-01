package com.rubynaxela.onyx.io;

import com.rubynaxela.jadeite.util.S31N;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Internationalization utility.
 */
public final class I18n {

    private static Locale locale;
    private static Map<String, String> lang;

    private I18n() {
    }

    public static Locale getLocale() {
        return locale;
    }

    @SuppressWarnings("unchecked")
    public static void setLocale(@NotNull Locale locale) {
        I18n.locale = locale;
        var langFile = I18n.class.getResourceAsStream("/res/" + locale.toLanguageTag() + ".json");
        if (langFile == null) {
            I18n.locale = Locale.US;
            langFile = I18n.class.getResourceAsStream("/res/" + Locale.US.toLanguageTag() + ".json");
        }
        lang = S31N.deserialize(TreeMap.class, Objects.requireNonNull(langFile));
    }

    public static String getString(@NotNull String key) {
        if (locale == null) throw new IllegalStateException("Application locale has not been specified");
        return lang.getOrDefault(key, key);
    }

    public static <T extends Enum<T>> String getString(@NotNull T enumConstant) {
        if (locale == null) throw new IllegalStateException("Application locale has not been specified");
        final var key = enumConstant.getClass().getSimpleName().toLowerCase() + '.' + enumConstant.toString().toLowerCase();
        return lang.getOrDefault(key, key);
    }
}
