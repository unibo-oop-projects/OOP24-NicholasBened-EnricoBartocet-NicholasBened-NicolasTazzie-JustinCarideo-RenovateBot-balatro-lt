package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopView;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.view.api.View;

/**
 * Implementation of the View interface.
 */
public final class SwingView implements View {
    private static final int MIN_WIDTH = 1000;
    private static final int MIN_HEIGHT = 600;
    private static final int MAX_SPECIAL_CARDS = 5;
    private static final float RIDIM = 1.5f;
    private final MasterController controller;
    private final JFrame frame = new JFrame();
    private JPanel panel;
    private InfoPanel infoPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;

    /**
     * Sets the frame and it's size.
     *
     * @param controller the MasterController to use.
     */
    public SwingView(final MasterController controller) {
        this.controller = Preconditions.checkNotNull(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() / RIDIM), (int) (screenSize.getHeight() / RIDIM));
        frame.setLocationByPlatform(true);
        frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    }

    @Override
    public void showMainMenu() {
        if (panel != null) {
            frame.remove(panel);
        }
        panel = new MainMenu(controller, "Play");
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showDecks(final List<DeckInfo> setMap) {
        frame.remove(panel);
        panel = new DeckSelector(this.controller, setMap);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showSettings(final BlindInfo info, final BlindStats stats, final List<SpecialCardInfo> specialCards, final DeckInfo deck) {
        frame.remove(panel);
        panel = new JPanel(new BorderLayout());
        frame.add(panel);
        infoPanel = new InfoPanel(info, stats);
        rightPanel = new JPanel(new BorderLayout());
        panel.add(rightPanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.WEST);
        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
        northPanel.setBackground(Color.GREEN.darker().darker().darker());
        rightPanel.add(northPanel, BorderLayout.NORTH);

        /**
         * Creating slot for the special cards.
         */
        final var specialSlot = new SlotPanel<SpecialCardInfo>(
            MAX_SPECIAL_CARDS, 75, 100,
            () -> true,
            () -> false,
            card -> JOptionPane.showMessageDialog(
                frame,
                card.name() + ":\n" + card.description(),
                "Special Card Info",
                JOptionPane.INFORMATION_MESSAGE)
        );
        specialCards.forEach(c -> specialSlot.addObject(new SlotPanel.SlotObject<>(c, c.name(), "JOKER")));
        final var specialSlotContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        specialSlotContainer.setOpaque(false);
        specialSlotContainer.add(specialSlot);

        /**
         * Creating slot for the special cards.
         */
        final var deckSlot = new SlotPanel<>(
            1, 100, 120,
            () -> true,
            () -> false,
            card -> JOptionPane.showMessageDialog(
                frame,
                deck.name() + " deck:\n" + deck.desc(),
                "Deck Info",
                JOptionPane.INFORMATION_MESSAGE)
        );
        deckSlot.addObject(new SlotPanel.SlotObject<>(deck, "Deck", "decks/" + deck.name() + "_DECK"));
        final JPanel deckSlotContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deckSlotContainer.setOpaque(false);
        deckSlotContainer.add(deckSlot);

        northPanel.add(specialSlotContainer);
        northPanel.add(Box.createHorizontalGlue());
        northPanel.add(deckSlotContainer);
        frame.setVisible(true);
    }

    @Override
    public void showAnte(final AnteInfo anteInfo) {
        if (centerPanel != null) {
            rightPanel.remove(centerPanel);
        }
        centerPanel = new AnteView(this.controller, anteInfo);
        rightPanel.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    @Override
    public void showRound(final List<PlayableCardInfo> playableCards) {
        rightPanel.remove(centerPanel);
        centerPanel = new GameTable(this.controller, playableCards);
        rightPanel.add(centerPanel, BorderLayout.CENTER);
        rightPanel.add(centerPanel, BorderLayout.CENTER);
        this.frame.setVisible(true);
    }

    @Override
    public void updateHand(final List<PlayableCardInfo> hand) {
        ((GameTable) this.centerPanel).updateHand(hand);
    }

    @Override
    public void updateCombinationStatus(final CombinationInfo combination) {
        this.infoPanel.updateCombination(combination);
    }

    @Override
    public void updateScore(final BlindStats stats) {
        this.infoPanel.updateStats(stats);
    }

    @Override
    public void updateSpecialCards(final List<SpecialCardInfo> specialCards) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSpecialCards'");
    }

    @Override
    public void updateBlindStatistics(final BlindStats stats) {
        this.infoPanel.updateStats(stats);
        ((GameTable) this.centerPanel).setDiscardEnabled(stats.discards() > 0);
    }

    @Override
    public void showBlindDefeated(final BlindInfo blindInfo, final BlindStats blindStats) {
        rightPanel.remove(this.centerPanel);
        centerPanel = new BlindOver(this.controller, blindInfo, blindStats);
        rightPanel.add(this.centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void showGameOver() {
        panel.remove(rightPanel);
        rightPanel = new GameEnd(controller, "Game Over");
        panel.add(rightPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void showYouWon() {
        panel.remove(rightPanel);
        rightPanel = new GameEnd(controller, "You Won!");
        panel.add(rightPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    @Override
    public void showShop() {
        this.rightPanel.remove(centerPanel);
        this.centerPanel = new ShopViewImpl(controller);
        this.rightPanel.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void notifyErrror(final String title, final String desc) {
        JOptionPane.showMessageDialog(this.panel, desc, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateShopCards(final Set<SpecialCardInfo> toSell) {
        ((ShopView) this.centerPanel).updateCards(toSell);
    }

}
