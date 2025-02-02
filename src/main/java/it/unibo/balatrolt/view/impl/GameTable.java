package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
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
    private static final Color BG_COLOR = Color.green.darker().darker().darker();
    private static final int MAX_PLAYED_CARDS = 5;
    private static final int MAX_SPECIAL_CARDS = 5;
    private static final float BASE_WEIGHT = 0.2f;
    private static final int RIDIM = 28;
    private static final int JB_FONT_SIZE = 18;

    private final MasterController controller;
    private final SlotPanel<PlayableCardInfo> handSlot;
    private SlotPanel<PlayableCardInfo> playedSlot;
    private final SlotPanel<SpecialCardInfo> specialSlot;
    private final List<PlayableCardInfo> selectedCards = new ArrayList<>();
    private final JButton discardButton;
    private final JPanel centerPanel;

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
        super(new BorderLayout());
        this.setBackground(BG_COLOR);
        this.controller = Preconditions.checkNotNull(controller);

        final JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        northPanel.setBackground(BG_COLOR);
        this.add(northPanel, BorderLayout.NORTH);
        this.centerPanel = new JPanel(new GridBagLayout());
        this.centerPanel.setBackground(BG_COLOR);
        this.add(centerPanel, BorderLayout.CENTER);
        final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        southPanel.setBackground(BG_COLOR);
        this.add(southPanel, BorderLayout.SOUTH);

        /**
         * Creating slot for the cards in hand.
         */
        this.handSlot = new SlotPanel<>(
            cards.size(),
            () -> this.selectedCards.size() < MAX_PLAYED_CARDS,
            () -> true,
            card -> {
                this.selectedCards.add(card);
                this.playedSlot.addObject(slotTranslator(card));
                this.revalidate();
                this.repaint();
            }
        );
        cards.forEach(c -> this.handSlot.addObject(this.slotTranslator(c)));
        this.buildSlot(this.handSlot, 1);

        /**
         * Creating slot for the played cards.
         */
        this.playedSlot = new SlotPanel<>(
            MAX_PLAYED_CARDS,
            () -> true,
            () -> true,
            card -> {
                this.selectedCards.remove(card);
                this.handSlot.addObject(slotTranslator(card));
                this.revalidate();
                this.repaint();
            }
        );
        this.buildSlot(this.playedSlot, 0);

        /**
         * Creating slot for the special cards.
         */
        this.specialSlot = new SlotPanel<>(
            MAX_SPECIAL_CARDS,
            () -> true,
            () -> false,
            card -> JOptionPane.showMessageDialog(this, card.name() + ":\n" + card.description(), "Special Card Info", JOptionPane.INFORMATION_MESSAGE)
        );
        specialCards.forEach(c -> this.specialSlot.addObject(this.slotTranslator(c)));
        northPanel.add(specialSlot);

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
        southPanel.add(playButton, BorderLayout.SOUTH);

        /**
         * Creating the discard button
         */
        this.discardButton = new JButton("Discard");
        discardButton.setBackground(Color.RED);
        discardButton.setForeground(Color.WHITE);
        discardButton.setFont(new Font("Arial", Font.PLAIN, JB_FONT_SIZE));
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

    public void updateHand(List<PlayableCardInfo> newCards) {
        this.handSlot.removeAll();
        newCards.forEach(c -> this.handSlot.addObject(this.slotTranslator(c)));
    }

    /**
     * @param isEnable true if the discard button is enable.
     */
    public void setDiscardEnabled(boolean isEnable) {
        this.discardButton.setEnabled(isEnable);
    }

    private void buildSlot(Component component, int y) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = BASE_WEIGHT;
        gbc.weighty = BASE_WEIGHT * 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, RIDIM, 5, RIDIM);
        gbc.anchor = GridBagConstraints.PAGE_END;
        this.centerPanel.add(component, gbc);
    }

    private SlotPanel.SlotObject<PlayableCardInfo> slotTranslator(PlayableCardInfo card) {
        return new SlotPanel.SlotObject<>(card, card.rank() + " " + card.suit(), card.rank() + card.suit());
    }

    private SlotPanel.SlotObject<SpecialCardInfo> slotTranslator(SpecialCardInfo card) {
        return new SlotPanel.SlotObject<>(card, card.name(), "JOKER");
    }
}
