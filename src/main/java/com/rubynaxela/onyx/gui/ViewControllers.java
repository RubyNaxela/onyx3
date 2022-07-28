package com.rubynaxela.onyx.gui;

import com.rubynaxela.onyx.gui.window.MainWindow;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class ViewControllers {

    public static Runnable UPDATE_SUMMARY_CARDS, REFRESH_OPERATIONS_LIST;
    public static Consumer<MainWindow.@NotNull Tab> SWITCH_TAB;

    private ViewControllers() {
    }
}
