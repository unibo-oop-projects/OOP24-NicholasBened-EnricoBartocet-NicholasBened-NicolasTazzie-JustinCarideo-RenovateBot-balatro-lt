package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.BalatroEvent;
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
    private static final int SPECIAL_CARD_WIDTH = 75;
    private static final int SPECIAL_CARD_HEIGHT = 100;
    private static final int DECK_WIDTH = 100;
    private static final int DECK_HEIGHT = 120;
    private static final int MAX_SPECIAL_CARDS = 5;
    private static final float RIDIM = 1.5f;

    private final MasterController controller;
    private final JFrame frame = new JFrame();
    private JPanel masterPanel;
    private InfoPanel infoPanel;
    private JPanel rightPanel;
    private JPanel activePanel;
    private JPanel specialSlotPanel;

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
        this.setLookAndFeel();
        this.setFrameIconImg();
    }

    @Override
    public void showMainMenu() {
        masterPanel = changePanel(frame, masterPanel, new MainMenu(controller, "Play"), Optional.absent());
    }

    @Override
    public void showDecks(final List<DeckInfo> decks) {
        masterPanel = changePanel(frame, masterPanel, new DeckSelector(controller, decks), Optional.absent());
    }

    @Override
    public void showSettings(
        final BlindInfo info,
        final BlindStats stats,
        final List<SpecialCardInfo> specialCards,
        final DeckInfo deck,
        final int numAnte,
        final List<CombinationInfo> combinations
    ) {
        masterPanel = changePanel(frame, masterPanel, new JPanel(new BorderLayout()), Optional.absent());
        infoPanel = (InfoPanel) changePanel(
            masterPanel,
            null,
            new InfoPanel(info, stats, combinations),
            Optional.of(BorderLayout.WEST)
        );
        rightPanel = changePanel(masterPanel, null, new JPanel(new BorderLayout()), Optional.of(BorderLayout.CENTER));
        final JPanel northPanel = changePanel(rightPanel, null, new JPanel(), Optional.of(BorderLayout.NORTH));
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
        northPanel.setBackground(Color.GREEN.darker().darker().darker());

        final var deckSlot = new SlotPanel<>(
            1,
            DECK_WIDTH,
            DECK_HEIGHT,
            () -> true,
            () -> false,
            e -> JOptionPane.showMessageDialog(
                    frame,
                    deck.name() + " deck:\n" + deck.desc(),
                    "Deck Info",
                    JOptionPane.INFORMATION_MESSAGE
                )
        );
        deckSlot.addObject(new SlotPanel.SlotObject<>(
            deck,
            "Deck",
            "decks/" + deck.name().toUpperCase(Locale.getDefault()) + "_DECK"
        ));
        final JPanel deckSlotContainer;
        // The order of add is important!
        specialSlotPanel = changePanel(northPanel, null, new JPanel(new FlowLayout(FlowLayout.LEFT)), Optional.absent());
        northPanel.add(Box.createHorizontalGlue());
        deckSlotContainer = changePanel(northPanel, null, new JPanel(new FlowLayout(FlowLayout.RIGHT)), Optional.absent());

        deckSlotContainer.setOpaque(false);
        deckSlotContainer.add(deckSlot);
        specialSlotPanel.setOpaque(false);
        updateSpecialCards(specialCards);
    }

    @Override
    public void showAnte(final AnteInfo anteInfo) {
        activePanel = changePanel(rightPanel, activePanel, new AnteView(controller, anteInfo), Optional.absent());
    }


    @Override
    public void showRound(final List<PlayableCardInfo> playableCards) {
        activePanel = changePanel(rightPanel, activePanel, new GameTable(controller, playableCards), Optional.absent());
    }

    @Override
    public void updateGameTable(final List<PlayableCardInfo> hand, final BlindStats stats) {
        Preconditions.checkState(this.activePanel instanceof GameTable, "The current active panel isn't a Game Table");
        ((GameTable) this.activePanel).updateHand(hand);
        ((GameTable) this.activePanel).setDiscardEnabled(stats.discards() > 0);
    }

    @Override
    public void updateCombinationStatus(final CombinationInfo combination) {
        this.infoPanel.updateCombination(combination);
    }

    @Override
    public void updateSpecialCards(final List<SpecialCardInfo> specialCards) {
        specialSlotPanel.removeAll();

        final var specialSlot = new SlotPanel<SpecialCardInfo>(
            MAX_SPECIAL_CARDS,
            SPECIAL_CARD_WIDTH,
            SPECIAL_CARD_HEIGHT,
            () -> true,
            () -> false,
            c -> {
                switch (JOptionPane.showConfirmDialog(
                    frame,
                    "\"" + c.name() + "\":\n" + c.description() + "\n\nSell Value: " + c.price() + "$\nDo you want to sell it?",
                    "Special Card Details",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE)
                ) {
                    case JOptionPane.YES_OPTION -> this.controller.handleEvent(BalatroEvent.SELL_CARD, Optional.of(c));
                    default -> { }
                }
            }
        );
        specialCards.forEach(c -> specialSlot.addObject(new SlotPanel.SlotObject<>(c, c.name(), "JOKER")));
        specialSlotPanel.add(specialSlot);
    }

    @Override
    public void updateCurrency(final int currency) {
        this.infoPanel.updateCurrency(currency);
    }

    @Override
    public void updateAnteInfo(final AnteInfo ante) {
        this.infoPanel.updateAnte(ante);
    }
    @Override
    public void updateScore(final BlindStats stats) {
        this.infoPanel.updateStats(stats);
    }

    @Override
    public void showBlindDefeated(final BlindInfo blindInfo) {
        activePanel = changePanel(
            rightPanel,
            activePanel,
            new BlindOver(controller, blindInfo),
            Optional.of(BorderLayout.CENTER)
        );
    }

    @Override
    public void showGameOver() {
        rightPanel = changePanel(masterPanel, rightPanel, new GameEnd(controller, "Game Over"), Optional.of(BorderLayout.CENTER));
    }

    @Override
    public void showYouWon() {
        rightPanel = changePanel(masterPanel, rightPanel, new GameEnd(controller, "You Won!"), Optional.of(BorderLayout.CENTER));
    }

    @Override
    public void showShop() {
        activePanel = changePanel(rightPanel, activePanel, new ShopViewImpl(controller), Optional.of(BorderLayout.CENTER));
    }

    @Override
    public void updateShopCards(final Set<SpecialCardInfo> toSell) {
        Preconditions.checkState(this.activePanel instanceof ShopView, "The current active panel isn't a Shop View");
        ((ShopView) this.activePanel).updateCards(toSell);
    }

    @Override
    public void notifyErrror(final String title, final String desc) {
        JOptionPane.showMessageDialog(this.masterPanel, desc, title, JOptionPane.ERROR_MESSAGE);
    }

    private JPanel changePanel(
        final Container parent,
        final JPanel oldPanel,
        final JPanel newPanel,
        final Optional<Object> constraints
    ) {
        if (oldPanel != null) {
            parent.remove(oldPanel);
        }
        if (constraints.isPresent()) {
            parent.add(newPanel, constraints.get());
        } else {
            parent.add(newPanel);
        }
        newPanel.setVisible(true);
        this.frame.revalidate();
        this.frame.repaint();
        this.frame.setVisible(true);
        return newPanel;
    }

    private void setFrameIconImg() {
        try {
            final Image img;
            img = ImageIO.read(getClass().getResource("/img/cards/ACEHEARTS.png"));
            frame.setIconImage(img);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Can't load the application icon", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e
            ) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
