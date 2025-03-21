package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;

/**
 * Represent the game table, formed by some generic SlotPanel<X>
 * of the given param X.
 */
@SuppressFBWarnings(
    justification = """
        Since we extend JPanel (which is Serializable), it's required to make the class Serializable,
        otherwhise an exception will be thrown when serializing this class.
        Anyway we are sure that we will never serialize this class, because if we want to save the game
        we will only save the informations stored in the model, creating a new View when needed.
        """,
    value = "SE_BAD_FIELD"
)
final class GameTable extends JPanel {
    private static final int SORT_FONT_SIZE = 14;
    private static final int SORT_BORDER_THICKNESS = 5;
    static final long serialVersionUID = 1L;
    private static final Color BG_COLOR = Color.green.darker().darker().darker();
    private static final String FONT = "COPPER_BLACK";
    private static final float BASE_WEIGHT = 0.2f;
    private static final float JB_FONT_SIZE = 18f;
    private static final int JB_WIDTH = 75;
    private static final int JB_HEIGHT = 95;
    private static final int MAX_PLAYED_CARDS = 5;
    private static final int RIDIM = 28;
    private static final int BASE_PADDING = 7;

    private final List<PlayableCardInfo> selectedCards = new ArrayList<>();
    private final List<PlayableCardInfo> handCards = new ArrayList<>();
    private final SlotPanel<PlayableCardInfo> playedSlot;
    private final SlotPanel<PlayableCardInfo> handSlot;
    private final FontFactory fontFactory = new FontFactory();
    private final MasterController controller;
    private final JButton discardButton;
    private final JPanel centerPanel;

    /**
     * Represent the game table, with the cards in the player's hand,
     * the played cards and the special ones (jokers).
     *
     * @param controller the master controller.
     * @param cards in the player hands.
     * @throws IOException
     */
    GameTable(final MasterController controller, final List<PlayableCardInfo> cards) {
        super(new BorderLayout());
        this.setBackground(BG_COLOR);
        this.controller = Preconditions.checkNotNull(controller);

        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
        northPanel.setBackground(BG_COLOR);
        add(northPanel, BorderLayout.NORTH);
        this.centerPanel = new JPanel(new GridBagLayout());
        this.centerPanel.setBackground(BG_COLOR);
        add(centerPanel, BorderLayout.CENTER);
        final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        southPanel.setBackground(BG_COLOR);
        add(southPanel, BorderLayout.SOUTH);

        /**
         * Creating slot for the cards in hand.
         */
        this.handSlot = new SlotPanel<>(
            cards.size(), JB_WIDTH, JB_HEIGHT,
            () -> this.selectedCards.size() < MAX_PLAYED_CARDS,
            () -> true,
            this::stageCard
        );
        cards.forEach(c -> {
            this.handSlot.addObject(this.slotTranslator(c));
            this.handCards.add(c);
        });
        this.buildSlot(this.handSlot, 1);

        /**
         * Creating slot for the staged cards.
         */
        this.playedSlot = new SlotPanel<>(
            MAX_PLAYED_CARDS, JB_WIDTH, JB_HEIGHT,
            () -> true,
            () -> true,
            this::unstageCard
        );
        this.buildSlot(this.playedSlot, 0);


        /**
         * Creating the play button.
         */
        final JButton playButton = new JButton("Play Hand");
        playButton.setBackground(Color.decode("#2274A5"));
        playButton.setForeground(Color.WHITE);
        playButton.setFont(this.fontFactory.getFont(FONT, JB_FONT_SIZE, this));
        playButton.addActionListener(e -> {
            if (!this.selectedCards.isEmpty()) {
                this.playedSlot.removeAll();
                this.controller.handleEvent(BalatroEvent.PLAY_CARDS, Optional.of(this.selectedCards));
                this.selectedCards.clear();
            }
        });
        southPanel.add(playButton, BorderLayout.SOUTH);
        southPanel.add(this.buildSortPanel());

        /**
         * Creating the discard button
         */
        this.discardButton = new JButton("Discard");
        discardButton.setBackground(Color.decode("#C1121F"));
        discardButton.setForeground(Color.WHITE);
        discardButton.setFont(this.fontFactory.getFont(FONT, JB_FONT_SIZE, this));
        discardButton.setPreferredSize(playButton.getPreferredSize());
        discardButton.addActionListener(e -> {
            if (!this.selectedCards.isEmpty()) {
                this.playedSlot.removeAll();
                this.controller.handleEvent(BalatroEvent.DISCARD_CARDS, Optional.of(this.selectedCards));
                this.selectedCards.clear();
            }
        });
        southPanel.add(discardButton, BorderLayout.SOUTH);
    }

    /**
     * updates the GUI with the new cards given.
     * @param newCards to display.
     */
    void updateHand(final List<PlayableCardInfo> newCards) {
        this.handSlot.removeAll();
        this.handCards.clear();
        newCards.forEach(c -> {
            this.handSlot.addObject(this.slotTranslator(c));
            this.handCards.add(c);
        });
    }

    /**
     * @param isEnable true if the discard button is enable.
     */
    void setDiscardEnabled(final boolean isEnable) {
        this.discardButton.setEnabled(isEnable);
    }

    private JPanel buildSortPanel() {
        final List<JButton> sortButtons = new ArrayList<>();
        final var outerSortPanel = new JPanel(new BorderLayout());
        final var sortLabel = new JLabel("Sort by", JLabel.CENTER);
        sortLabel.setForeground(Color.WHITE);
        sortLabel.setFont(this.fontFactory.getFont(FONT, SORT_FONT_SIZE, this));
        outerSortPanel.add(sortLabel, BorderLayout.NORTH);
        outerSortPanel.setBorder(BorderFactory.createLineBorder(
            Color.DARK_GRAY,
            SORT_BORDER_THICKNESS));
        outerSortPanel.setBackground(this.getBackground());
        final var innerSortPanel = new JPanel(new FlowLayout());
        innerSortPanel.setBackground(this.getBackground());
        sortButtons.add(buildOrangeButton("Rank", e -> {
            this.controller.handleEvent(BalatroEvent.SORT_BY_RANK, Optional.of(handCards));
        }));
        sortButtons.add(buildOrangeButton("Suit", e -> {
            this.controller.handleEvent(BalatroEvent.SORT_BY_SUIT, Optional.of(handCards));
        }));
        sortButtons.forEach(innerSortPanel::add);
        outerSortPanel.add(innerSortPanel, BorderLayout.CENTER);
        return outerSortPanel;
    }

    private JButton buildOrangeButton(final String text, final ActionListener e) {
        final var button = new JButton(text);
        button.addActionListener(e);
        button.setBackground(Color.ORANGE);
        button.setForeground(Color.WHITE);
        button.setFont(this.fontFactory.getFont(FONT, JB_FONT_SIZE, this));
        return button;
    }

    private void buildSlot(final Component component, final int y) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = BASE_WEIGHT;
        gbc.weighty = BASE_WEIGHT * 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(BASE_PADDING, RIDIM, BASE_PADDING, RIDIM);
        gbc.anchor = GridBagConstraints.PAGE_END;
        this.centerPanel.add(component, gbc);
    }

    private SlotPanel.SlotObject<PlayableCardInfo> slotTranslator(final PlayableCardInfo card) {
        return new SlotPanel.SlotObject<>(
            card,
            card.rank() + " " + card.suit(),
            "cards/" + card.rank().toUpperCase(Locale.getDefault()) + card.suit().toUpperCase(Locale.getDefault()));
    }

    private void stageCard(final PlayableCardInfo card) {
        this.selectedCards.add(card);
        this.handCards.remove(card);
        this.playedSlot.addObject(slotTranslator(card));
        this.controller.handleEvent(BalatroEvent.STAGE_CARDS, Optional.of(this.selectedCards));
        this.revalidate();
        this.repaint();
    }

    private void unstageCard(final PlayableCardInfo card) {
        this.selectedCards.remove(card);
        this.handCards.add(card);
        this.handSlot.addObject(slotTranslator(card));
        this.controller.handleEvent(BalatroEvent.STAGE_CARDS, Optional.of(this.selectedCards));
        this.revalidate();
        this.repaint();
    }
}
