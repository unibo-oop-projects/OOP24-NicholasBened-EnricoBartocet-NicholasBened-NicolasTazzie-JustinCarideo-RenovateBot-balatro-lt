package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;

/**
 * Displays the statistics of the current blind.
 * (remaining discards, hand, current currency and ante).
 */
public class HandPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JLabel handLabel;
    private final JLabel discardLabel;
    private final JLabel currencyLabel;
    private final JLabel anteLabel;

    /**
     * Builds the panel with information about player stats.
     * @param stats about the current blind
     */
    public HandPanel(final BlindStats stats) {
        super(new GridLayout(2, 1));
        this.handLabel = createGeneralLabel("");
        this.discardLabel = createGeneralLabel("");
        this.currencyLabel = createGeneralLabel("$0");
        this.anteLabel = createGeneralLabel("Ante: ");
        initialize(stats);
    }

    /**
     * Initializes the UI components and updates the stats.
     * @param stats initial stats
     */
    private void initialize(final BlindStats stats) {
        add(createNorthPanel());
        add(createSouthPanel());
        updateHands(stats);
    }

    /**
     * Creates a JLabel with default style.
     * @param text
     * @return a general label with default style
     */
    private JLabel createGeneralLabel(final String text) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }

    /**
     * Configures the north panel with hand and discard labels.
     * @return panel placed in the south part
     */
    private JPanel createNorthPanel() {
        final JPanel northPanel = new JPanel(new GridLayout(1, 2));
        northPanel.add(handLabel);
        northPanel.add(discardLabel);
        return northPanel;
    }

    /**
     * Configures the south panel with ante and currency labels.
     * @return panel placed in the south part
     */
    private JPanel createSouthPanel() {
        final JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.add(anteLabel);
        southPanel.add(currencyLabel);
        return southPanel;
    }

    /**
     * Updates the hand and discard labels.
     * @param stats The current blind stats
     */
    public void updateHands(final BlindStats stats) {
        this.handLabel.setText("Hand: " + stats.hands());
        this.discardLabel.setText("Discard: " + stats.discards());
    }

    /**
     * Updates the currency label.
     * @param currency new amount
     */
    public void updateCurrency(final int currency) {
        this.currencyLabel.setText("$" + currency);
    }

    /**
     * Updates the ante label.
     * @param info
     */
    public void updateAnte(final AnteInfo info) {
        this.anteLabel.setText("Ante: " + info.id());
    }
}
