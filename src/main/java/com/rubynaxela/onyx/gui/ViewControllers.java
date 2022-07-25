package com.rubynaxela.onyx.gui;

import com.rubynaxela.onyx.data.Monetary;
import com.rubynaxela.onyx.gui.window.MainWindow;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class ViewControllers {

    public static Consumer<@NotNull Monetary> AVAILABLE_BALANCE, CASH, SAVINGS, PENDING_OPERATIONS, TOTAL_BALANCE;
    public static Consumer<MainWindow.@NotNull Tab> SWITCH_TAB;

    private ViewControllers() {
    }
}
