package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

/**
 * Represent the game table, formed by some generic SlotPanel<X>
 * of the given param X.
 */
public class GameTable extends JPanel {
    private static final int MAX_PLAYED_CARDS = 5;
    private static final int MAX_SPECIAL_CARDS = 5;
    private static final float BASE_WEIGHT = 0.2f;
    private static final int RIDIM = 28;
    private static final int JB_FONT_SIZE = 18;
    private final MasterController controller;
    private SlotPanel<PlayableCardInfo> handSlot;
    private SlotPanel<PlayableCardInfo> playedSlot;
    private SlotPanel<SpecialCardInfo> specialSlot;
    private List<PlayableCardInfo> selectedCards;
    private JButton discardButton;

    /**
     * Represent the game table, with the cards in the player's hand,
     * the played cards and the special ones (jokers).
     *
     * @param controller the master controller.
     * @param cards in the player hands.
     * @param specialCards owned by the player.
     * @throws IOException
     */
    public GameTable(MasterController controller, List<PlayableCardInfo> cards, List<SpecialCardInfo> specialCards) {
        super(new GridBagLayout());
        this.setBackground(Color.green.darker().darker().darker());
        this.controller = Preconditions.checkNotNull(controller);
        this.selectedCards = new ArrayList<>();
        /**
         * Creating slot for the cards in hand.
         */
        this.handSlot = new SlotPanel<>(
            cards.size(),
            () -> this.selectedCards.size() < MAX_PLAYED_CARDS,
            card -> {
                this.selectedCards.add(card);
                this.playedSlot.addObject(slotTranslator(card));
                this.revalidate();
                this.repaint();
            }
        );
        cards.forEach(c -> this.handSlot.addObject(this.slotTranslator(c)));
        this.build(this.handSlot, 0, 2, 3, 2, GridBagConstraints.PAGE_END, RIDIM);

        /**
         * Creating slot for the played cards.
         */
        this.playedSlot = new SlotPanel<>(
            MAX_PLAYED_CARDS,
            () -> true,
            card -> {
                this.selectedCards.remove(card);
                this.handSlot.addObject(slotTranslator(card));
                this.revalidate();
                this.repaint();
            }
        );
        this.build(this.playedSlot, 0, 1, 3, 1, GridBagConstraints.CENTER, RIDIM);

        /**
         * Creating slot for the special cards.
         */
        this.specialSlot = new SlotPanel<>(
            MAX_SPECIAL_CARDS,
            () -> true,
            card -> {}
        );
        specialCards.forEach(c -> this.specialSlot.addObject(this.slotTranslator(c)));
        this.build(this.specialSlot, 0, 0, 1, 1,GridBagConstraints.FIRST_LINE_START, RIDIM);

        /**
         * Creating the play button.
         */
        final JButton playButton = new JButton("Play Hand");
        playButton.setBackground(Color.BLUE);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(new Font("Arial", Font.PLAIN, JB_FONT_SIZE));
        playButton.addActionListener(e-> {
            if (!this.selectedCards.isEmpty()) {
                this.playedSlot.removeAll();
                this.controller.handleEvent(BalatroEvent.PLAY_CARDS, Optional.of(this.selectedCards));
                this.selectedCards.clear();
            }
        });
        this.build(playButton, 0, 3, 1, 0, GridBagConstraints.LINE_END, 0);

        /**
         * Creating the discard button
         */
        this.discardButton = new JButton("Discard");
        discardButton.setBackground(Color.RED);
        discardButton.setForeground(Color.WHITE);
        discardButton.setFont(new Font("Arial", Font.PLAIN, JB_FONT_SIZE));
        discardButton.addActionListener(e -> {
            if (!this.selectedCards.isEmpty()) {
                this.playedSlot.removeAll();
                this.controller.handleEvent(BalatroEvent.DISCARD_CARDS, Optional.of(this.selectedCards));
                this.selectedCards.clear();
            }
        });
        this.build(discardButton, 2, 3, 1, 0, GridBagConstraints.LINE_START, 0);
    }

    public void updateHand(List<PlayableCardInfo> newCards) {
        this.handSlot.removeAll();
        newCards.forEach(c -> this.handSlot.addObject(this.slotTranslator(c)));
    }

    /**
     * Adds the slot given to the GameTable panel
     * applying the given constraints.
     *
     * @param slot to which the constraints will be applied.
     * @param x position of gridx.
     * @param y position of gridy.
     * @param width how many cells has to occupy starting from @param x
     * @param multiplier multiplier for weighty.
     * @param anchor enum indicating where to anchor.
     */
    private void build(Component component, int x, int y, int width, int multiplier, int anchor, int inset) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = BASE_WEIGHT;
        gbc.weighty = BASE_WEIGHT * multiplier;
        gbc.gridwidth = width;
        gbc.insets = new Insets(inset , RIDIM, RIDIM, RIDIM);
        gbc.anchor = anchor;
        this.add(component, gbc);
    }

    /**
     *
     * @param card to translate.
     * @return SlotObject representing the card's name and itself.
     */
    private SlotPanel.SlotObject<PlayableCardInfo> slotTranslator(PlayableCardInfo card) {
        return new SlotPanel.SlotObject<>(card, card.rank().toUpperCase() + card.suit().toUpperCase());
    }

    /**
     *
     * @param card to translate.
     * @return SlotObject representing the card's name and itself.
     */
    private SlotPanel.SlotObject<SpecialCardInfo> slotTranslator(SpecialCardInfo card) {
        return new SlotPanel.SlotObject<>(card, card.name().toUpperCase());
    }

    /**
     * returns true if the discard button is enabled.
     * @param isEnable true if the discard button is enable.
     */
    public void setDiscardEnabled(boolean isEnable) {
        this.discardButton.setEnabled(isEnable);
    }
}
