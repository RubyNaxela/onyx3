package com.rubynaxela.onyx.gui.tabs;

import com.rubynaxela.onyx.data.Database;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.gui.components.Card;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.ComponentUtils;
import jiconfont.IconCode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import static com.rubynaxela.onyx.gui.GridBagConstraintsBuilder.gbc;

/**
 * Represents the home panel. The size of this panel is 896x768.
 */
public class HomeTab extends WindowTab {

    private final JLabel accountBalanceLabel, savingsLabel, cashLabel,
            pendingOperationsLabel, availableBalanceLabel, totalBalanceLabel;

    /**
     * Creates the content panel.
     */
    public HomeTab() {

        setRootLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new GridBagLayout());

        final var topCardsPanel = new JPanel();
        final var layout = new GridLayout(1, 4);
        layout.setHgap(16);
        topCardsPanel.setLayout(layout);

        accountBalanceLabel = new JLabel();
        savingsLabel = new JLabel();
        cashLabel = new JLabel();
        pendingOperationsLabel = new JLabel();
        availableBalanceLabel = new JLabel();
        totalBalanceLabel = new JLabel();

        ViewControllers.UPDATE_SUMMARY_CARDS = () -> {
            final var database = Database.INSTANCE;
            accountBalanceLabel.setText(database.wGetAccountBalance().toString());
            savingsLabel.setText(database.getSavings().toString());
            cashLabel.setText(database.wGetTotalCash().toString());
            pendingOperationsLabel.setText(database.wGetPendingOperationsBalance().toString());
            availableBalanceLabel.setText(database.wGetAvailableBalance().toString());
            totalBalanceLabel.setText(database.wGetTotalBalance().toString());
        };
        ViewControllers.UPDATE_SUMMARY_CARDS.run();

        topCardsPanel.add(createTopCard(MaterialIcons.ACCOUNT_BALANCE, accountBalanceLabel,
                                        I18n.getString("label.card.account_balance")));
        topCardsPanel.add(createTopCard(MaterialIcons.SAVINGS, savingsLabel,
                                        I18n.getString("label.card.savings")));
        topCardsPanel.add(createTopCard(MaterialIcons.PAYMENTS, cashLabel,
                                        I18n.getString("label.card.cash")));
        topCardsPanel.add(createTopCard(MaterialIcons.PENDING_ACTIONS, pendingOperationsLabel,
                                        I18n.getString("label.card.pending")));

        add(topCardsPanel, gbc().build());
        add(createSumCard(MaterialIcons.FUNCTIONS, totalBalanceLabel,
                          I18n.getString("label.card.total_balance")),
            gbc().row(1).fill(GridBagConstraints.HORIZONTAL).build());
        add(createSumCard(MaterialIcons.ACCOUNT_BALANCE_WALLET, availableBalanceLabel,
                          I18n.getString("label.card.available_balance")),
            gbc().row(2).fill(GridBagConstraints.HORIZONTAL).build());
    }

    private Card createTopCard(@NotNull IconCode icon, @NotNull JLabel dataLabel, @NotNull String description) {

        final var card = new Card();
        card.setLayout(new GridBagLayout());

        final var iconColor = UIManager.getColor("Button.focusedBorderColor").brighter().brighter();
        final var iconLabel = ComponentUtils.createIconLabel(icon, 48f, iconColor);
        ComponentUtils.addMargin(iconLabel, 0, 16, 0, 0);
        card.add(iconLabel, gbc().height(2).anchor(GridBagConstraints.WEST).weightX(0).build());

        ComponentUtils.setFontParams(dataLabel, Font.BOLD, 16f);
        card.add(dataLabel, gbc().position(0, 1).anchor(GridBagConstraints.WEST).weightX(1).extPaddingTop(2).build());

        final var descriptionLabel = new JLabel(description);
        ComponentUtils.setFontSize(descriptionLabel, 11f);
        descriptionLabel.setForeground(descriptionLabel.getForeground().darker());
        card.add(descriptionLabel, gbc().position(1, 1).anchor(GridBagConstraints.WEST).weightX(1).extPaddingTop(6).build());

        return card;
    }

    private Card createSumCard(@NotNull IconCode icon, @NotNull JLabel valueLabel, @NotNull String description) {

        final var sumCard = new Card();
        sumCard.setLayout(new BorderLayout());
        ComponentUtils.addMargin(sumCard, 16, 0, 0, 0);

        final var iconColor = UIManager.getColor("Button.focusedBorderColor").brighter().brighter();
        final var iconLabel = ComponentUtils.createIconLabel(icon, 32f, iconColor);
        ComponentUtils.addMargin(iconLabel, 0, 16, 0, 0);
        sumCard.add(iconLabel, BorderLayout.WEST);

        final var descriptionLabel = new JLabel(description);
        ComponentUtils.setFontSize(descriptionLabel, 14f);
        sumCard.add(descriptionLabel, BorderLayout.CENTER);

        ComponentUtils.setFontParams(valueLabel, Font.BOLD, 20f);
        sumCard.add(valueLabel, BorderLayout.EAST);

        return sumCard;
    }
}
