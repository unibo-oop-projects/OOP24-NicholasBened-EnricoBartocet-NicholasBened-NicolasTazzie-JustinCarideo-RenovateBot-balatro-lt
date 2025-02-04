package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;

/**
 * Represent the game table, formed by some generic SlotPanel<X>
 * of the given param X.
 */
public class GameTable extends JPanel {
    static final long serialVersionUID = 1L;
    private static final Color BG_COLOR = Color.green.darker().darker().darker();
    private static final String FONT = "COPPER_BLACK";
    private static final float BASE_WEIGHT = 0.2f;
    private static final float JB_FONT_SIZE = 18f;
    private static final int MAX_PLAYED_CARDS = 5;
    private static final int RIDIM = 28;

    private final MasterController controller;
    private final SlotPanel<PlayableCardInfo> handSlot;
    private SlotPanel<PlayableCardInfo> playedSlot;
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
    public GameTable(final MasterController controller, final List<PlayableCardInfo> cards) {
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
            cards.size(), 75, 95,
            () -> this.selectedCards.size() < MAX_PLAYED_CARDS,
            () -> true,
            card -> {
                this.selectedCards.add(card);
                this.playedSlot.addObject(slotTranslator(card));
                this.controller.handleEvent(BalatroEvent.STAGE_CARDS, Optional.of(this.selectedCards));
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
            MAX_PLAYED_CARDS, 75, 95,
            () -> true,
            () -> true,
            card -> {
                this.selectedCards.remove(card);
                this.handSlot.addObject(slotTranslator(card));
                this.controller.handleEvent(BalatroEvent.STAGE_CARDS, Optional.of(this.selectedCards));
                this.revalidate();
                this.repaint();
            }
        );
        this.buildSlot(this.playedSlot, 0);


        /**
         * Creating the play button.
         */
        final JButton playButton = new JButton("Play Hand");
        playButton.setBackground(Color.BLUE);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(getFont(FONT, JB_FONT_SIZE));
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
        discardButton.setFont(getFont(FONT, JB_FONT_SIZE));
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

    public void updateHand(final List<PlayableCardInfo> newCards) {
        this.handSlot.removeAll();
        newCards.forEach(c -> this.handSlot.addObject(this.slotTranslator(c)));
    }

    /**
     * @param isEnable true if the discard button is enable.
     */
    public void setDiscardEnabled(final boolean isEnable) {
        this.discardButton.setEnabled(isEnable);
    }

    private void buildSlot(final Component component, final int y) {
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

    private SlotPanel.SlotObject<PlayableCardInfo> slotTranslator(final PlayableCardInfo card) {
        return new SlotPanel.SlotObject<>(
            card,
            card.rank() + " " + card.suit(),
            "cards/" + card.rank().toUpperCase(Locale.getDefault()) + card.suit().toUpperCase(Locale.getDefault()));
    }

    /*
     * Gives back the requested font with the given size.
     */
    private Font getFont(final String nameFont, final float fontSize) {
        Font font = new Font("Arial", Font.PLAIN, (int) fontSize);
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/font/" + nameFont + ".TTF")
            );
            font = font.deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot load font");
        }
        return font;
    }
}
