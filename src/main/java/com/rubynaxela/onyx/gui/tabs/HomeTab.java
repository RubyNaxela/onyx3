package com.rubynaxela.onyx.gui.tabs;

import com.rubynaxela.onyx.data.Database;
import com.rubynaxela.onyx.data.Monetary;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.gui.components.Card;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.ComponentUtils;
import jiconfont.IconCode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.rubynaxela.onyx.gui.GridBagConstraintsBuilder.gbc;

/**
 * Represents the home panel. The size of this panel is 896x768.
 */
public class HomeTab extends WindowTab {

    private final JLabel availableBalanceLabel, savingsLabel, cashLabel, pendingOperationsLabel, totalBalanceLabel;

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

        final var data = Database.INSTANCE;
        final var availableBalance = Monetary.add(data.getInitialAmount(), data.wGetOperationsBalance());
        final var pendingBalance = data.wGetPendingOperationsBalance();
        availableBalanceLabel = new JLabel(availableBalance.toString());
        savingsLabel = new JLabel(data.getSavings().toString());
        cashLabel = new JLabel(data.getCash().toString());
        pendingOperationsLabel = new JLabel(pendingBalance.toString());
        totalBalanceLabel = new JLabel(Monetary.sum(List.of(availableBalance, pendingBalance,
                                                            data.getCash(), data.getSavings())).toString());

        ViewControllers.AVAILABLE_BALANCE = v -> availableBalanceLabel.setText(v.toString());
        ViewControllers.SAVINGS = v -> savingsLabel.setText(v.toString());
        ViewControllers.CASH = v -> cashLabel.setText(v.toString());
        ViewControllers.PENDING_OPERATIONS = v -> pendingOperationsLabel.setText(v.toString());
        ViewControllers.TOTAL_BALANCE = v -> totalBalanceLabel.setText(v.toString());

        topCardsPanel.add(createTopCard(MaterialIcons.ACCOUNT_BALANCE, availableBalanceLabel,
                                        I18n.getString("label.card.available_balance")));
        topCardsPanel.add(createTopCard(MaterialIcons.SAVINGS, savingsLabel,
                                        I18n.getString("label.card.savings")));
        topCardsPanel.add(createTopCard(MaterialIcons.PAYMENTS, cashLabel,
                                        I18n.getString("label.card.cash")));
        topCardsPanel.add(createTopCard(MaterialIcons.PENDING_ACTIONS, pendingOperationsLabel,
                                        I18n.getString("label.card.pending")));

        add(topCardsPanel, gbc().build());
        add(createSumCard(), gbc().row(1).fill(GridBagConstraints.HORIZONTAL).build());
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

    private Card createSumCard() {

        final var sumCard = new Card();
        sumCard.setLayout(new BorderLayout());
        ComponentUtils.addMargin(sumCard, 16, 0, 16, 0);

        final var iconColor = UIManager.getColor("Button.focusedBorderColor").brighter().brighter();
        final var iconLabel = ComponentUtils.createIconLabel(MaterialIcons.ACCOUNT_BALANCE_WALLET, 32f, iconColor);
        ComponentUtils.addMargin(iconLabel, 0, 16, 0, 0);
        sumCard.add(iconLabel, BorderLayout.WEST);

        final var totalBalanceDescription = new JLabel(I18n.getString("label.card.total_balance"));
        ComponentUtils.setFontSize(totalBalanceDescription, 14f);
        sumCard.add(totalBalanceDescription, BorderLayout.CENTER);

        ComponentUtils.setFontParams(totalBalanceLabel, Font.BOLD, 20f);
        sumCard.add(totalBalanceLabel, BorderLayout.EAST);

        return sumCard;
    }
}
