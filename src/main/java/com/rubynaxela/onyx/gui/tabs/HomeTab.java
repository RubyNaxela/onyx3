package com.rubynaxela.onyx.gui.tabs;

import com.rubynaxela.jadeite.awt.JColor;
import com.rubynaxela.jadeite.awt.graphics.RectangleShape;
import com.rubynaxela.jadeite.pointers.Pointer;
import com.rubynaxela.jadeite.util.Pair;
import com.rubynaxela.onyx.data.Category;
import com.rubynaxela.onyx.data.Database;
import com.rubynaxela.onyx.data.Monetary;
import com.rubynaxela.onyx.gui.MaterialIcons;
import com.rubynaxela.onyx.gui.ViewControllers;
import com.rubynaxela.onyx.gui.chart.Charts;
import com.rubynaxela.onyx.gui.components.Card;
import com.rubynaxela.onyx.gui.components.ChartPanel;
import com.rubynaxela.onyx.gui.components.Icon;
import com.rubynaxela.onyx.io.I18n;
import com.rubynaxela.onyx.util.Colors;
import com.rubynaxela.onyx.util.ComponentUtils;
import jiconfont.IconCode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

import static com.rubynaxela.jadeite.awt.JGridBagConstraints.gbc;

/**
 * Represents the home panel. The size of this panel is 896x768.
 */
public class HomeTab extends WindowTab {

    private final JLabel accountBalanceLabel, savingsLabel, cashLabel, pendingOperationsLabel;
    private final JLabel availableBalanceLabel, totalBalanceLabel;
    private final ChartPanel expensesBreakdown, revenuesBreakdown;

    /**
     * Creates the content panel.
     */
    public HomeTab() {

        setRootLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new GridBagLayout());

        accountBalanceLabel = new JLabel();
        savingsLabel = new JLabel();
        cashLabel = new JLabel();
        pendingOperationsLabel = new JLabel();
        availableBalanceLabel = new JLabel();
        totalBalanceLabel = new JLabel();
        expensesBreakdown = new ChartPanel();
        revenuesBreakdown = new ChartPanel();

        createSummaryCards();
        createCharts();

        ViewControllers.UPDATE_SUMMARY_CARDS = () -> {
            final var database = Database.INSTANCE;
            accountBalanceLabel.setText(database.wGetAccountBalance().toString());
            savingsLabel.setText(database.getSavings().toString());
            cashLabel.setText(database.wGetTotalCash().toString());
            pendingOperationsLabel.setText(database.wGetPendingOperationsBalance().toString());
            availableBalanceLabel.setText(database.wGetAvailableBalance().toString());
            totalBalanceLabel.setText(database.wGetTotalBalance().toString());
            expensesBreakdown.draw(Charts.EXPENSES_BREAKDOWN);
            revenuesBreakdown.draw(Charts.REVENUES_BREAKDOWN);
        };
        ViewControllers.UPDATE_SUMMARY_CARDS.run();
    }

    private Card createTopCard(@NotNull IconCode icon, @NotNull JLabel dataLabel, @NotNull String description) {

        final var card = new Card();
        card.setLayout(new GridBagLayout());

        final var iconColor = UIManager.getColor("Button.focusedBorderColor").brighter().brighter();
        final var iconLabel = new Icon(icon, 48f, iconColor);
        ComponentUtils.addMargin(iconLabel, 0, 16, 0, 0);
        card.add(iconLabel, gbc().height(2).anchor(GridBagConstraints.WEST).weightX(0));

        ComponentUtils.setFontParams(dataLabel, Font.BOLD, 16f);
        card.add(dataLabel, gbc().position(1, 0).anchor(GridBagConstraints.WEST).weightX(1).extPaddingTop(2));

        final var descriptionLabel = new JLabel(description);
        ComponentUtils.setFontSize(descriptionLabel, 11f);
        descriptionLabel.setForeground(descriptionLabel.getForeground().darker());
        card.add(descriptionLabel, gbc().position(1, 1).anchor(GridBagConstraints.WEST).weightX(1).extPaddingTop(6));

        return card;
    }

    private Card createSumCard(@NotNull IconCode icon, @NotNull JLabel valueLabel, @NotNull String description) {

        final var sumCard = new Card();
        sumCard.setLayout(new BorderLayout());
        ComponentUtils.addMargin(sumCard, 16, 0, 0, 0);

        final var iconColor = UIManager.getColor("Button.focusedBorderColor").brighter().brighter();
        final var iconLabel = new Icon(icon, 32f, iconColor);
        ComponentUtils.addMargin(iconLabel, 0, 16, 0, 0);
        sumCard.add(iconLabel, BorderLayout.WEST);

        final var descriptionLabel = new JLabel(description);
        ComponentUtils.setFontSize(descriptionLabel, 14f);
        sumCard.add(descriptionLabel, BorderLayout.CENTER);

        ComponentUtils.setFontParams(valueLabel, Font.BOLD, 20f);
        sumCard.add(valueLabel, BorderLayout.EAST);

        return sumCard;
    }

    private void createSummaryCards() {

        final var topCardsPanel = new JPanel();
        final var layout = new GridLayout(1, 4);
        layout.setHgap(16);
        topCardsPanel.setLayout(layout);

        topCardsPanel.add(createTopCard(MaterialIcons.ACCOUNT_BALANCE, accountBalanceLabel,
                                        I18n.getString("label.card.account_balance")));
        topCardsPanel.add(createTopCard(MaterialIcons.SAVINGS, savingsLabel,
                                        I18n.getString("label.card.savings")));
        topCardsPanel.add(createTopCard(MaterialIcons.PAYMENTS, cashLabel,
                                        I18n.getString("label.card.cash")));
        topCardsPanel.add(createTopCard(MaterialIcons.PENDING_ACTIONS, pendingOperationsLabel,
                                        I18n.getString("label.card.pending")));

        add(topCardsPanel, gbc().width(2));
        add(createSumCard(MaterialIcons.FUNCTIONS, totalBalanceLabel,
                          I18n.getString("label.card.total_balance")),
            gbc().row(1).width(2).fill(GridBagConstraints.HORIZONTAL));
        add(createSumCard(MaterialIcons.ACCOUNT_BALANCE_WALLET, availableBalanceLabel,
                          I18n.getString("label.card.available_balance")),
            gbc().row(2).width(2).fill(GridBagConstraints.HORIZONTAL));
    }

    private JLabel createChartTitle(@NotNull String title) {
        final var label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        ComponentUtils.setFontParams(label, Font.BOLD, 16f);
        ComponentUtils.addMargin(label, 0, 0, 16, 0);
        return label;
    }

    private JPanel createChartLegend(@NotNull Map<Category, Monetary> dataset, boolean mergeRefunds) {
        final var panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(Colors.TRANSPARENT);

        final SortedMap<Monetary, Pair<Color, String>> data = new TreeMap<>(Comparator.comparing(Monetary::negativeAbsolute));
        final var refunds = Pointer.to(Monetary.ZERO);
        final List<JColor> refundsColors = new ArrayList<>();
        dataset.forEach((c, a) -> {
            if (!mergeRefunds || !c.incomesAreRedunds) data.put(a, Pair.of(c.color, I18n.getString(c)));
            else {
                refunds.set(Monetary.add(refunds.get(), a));
                refundsColors.add(c.color);
            }
        });
        if (!refunds.get().equals(Monetary.ZERO)) {
            var refundsColor = JColor.average(refundsColors);
            refundsColor = refundsColor.withSaturation(Math.max(refundsColor.getSaturation(), 0.5f));
            data.put(refunds.get(), Pair.of(refundsColor, I18n.getString("label.chart.refunds")));
        }

        data.forEach((key, value) -> {
            final var colorPreview = new RectangleShape(16, 16);
            colorPreview.setColor(value.v1);
            final var label = new JLabel();
            label.setText("<html>" + value.v2 + " :: <span style=\"color:" +
                          new JColor(label.getForeground()).withAlpha(0.5f).cssValue() + "\">" +
                          key.absolute() + "</span></html>");
            label.setIcon(new ImageIcon(colorPreview.createImage()));
            ComponentUtils.addMargin(label, 2, 0, 2, 0);
            panel.add(label);
        });
        return panel;
    }

    private void createCharts() {

        final var expensesBreakdownCard = new Card(new GridBagLayout());
        expensesBreakdownCard.add(createChartTitle(I18n.getString("title.chart.expenses_breakdown")));
        expensesBreakdownCard.add(expensesBreakdown, gbc().row(1).weightX(1).weightY(1).fill(GridBagConstraints.BOTH));
        expensesBreakdownCard.add(createChartLegend(Database.INSTANCE.wGetExpensesByCategory(), false),
                                  gbc().column(1).height(2).extPaddingLeft(16));
        add(expensesBreakdownCard, gbc().row(3).weightX(1).fill(GridBagConstraints.BOTH)
                                        .extPadding(16, 0, 0, 8));

        final var revenuesBreakdownCard = new Card(new GridBagLayout());
        revenuesBreakdownCard.add(createChartTitle(I18n.getString("title.chart.revenues_breakdown")));
        revenuesBreakdownCard.add(revenuesBreakdown, gbc().row(1).weightX(1).weightY(1).fill(GridBagConstraints.BOTH));
        revenuesBreakdownCard.add(createChartLegend(Database.INSTANCE.wGetRevenuesByCategory(), true),
                                  gbc().row(2).anchor(GridBagConstraints.WEST).extPaddingTop(16));
        add(revenuesBreakdownCard, gbc().position(1, 3).fill(GridBagConstraints.VERTICAL)
                                        .extPadding(16, 8, 0, 0));
    }
}
