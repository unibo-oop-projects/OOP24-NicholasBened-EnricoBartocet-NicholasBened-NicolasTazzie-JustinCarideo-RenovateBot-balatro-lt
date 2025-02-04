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

    private final JLabel handLabel = new JLabel();
    private final JLabel discardLabel = new JLabel();
    private final JLabel currencyLabel = new JLabel("$0");
    private final JLabel anteLabel = new JLabel("Ante: ");

    /**
     * Builds the panel with informations about
     * player stats.
     * @param stats about the current blind
     */
    public HandPanel(final BlindStats stats) {
        setLayout(new GridLayout(2, 1));
        updateHands(stats);
        final JPanel northPanel = new JPanel(new GridLayout(1, 2));
        handLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        discardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        handLabel.setOpaque(true);
        discardLabel.setOpaque(true);
        handLabel.setBackground(Color.DARK_GRAY);
        discardLabel.setBackground(Color.DARK_GRAY);
        handLabel.setHorizontalAlignment(SwingConstants.CENTER);
        discardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(handLabel);
        northPanel.add(discardLabel);
        this.currencyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currencyLabel.setOpaque(true);
        currencyLabel.setBackground(Color.DARK_GRAY);
        final JPanel southPanel = new JPanel(new GridLayout(1, 2));
        anteLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        anteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        anteLabel.setOpaque(true);
        anteLabel.setBackground(Color.DARK_GRAY);
        currencyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        handLabel.setForeground(Color.white);
        anteLabel.setForeground(Color.white);
        discardLabel.setForeground(Color.white);
        currencyLabel.setForeground(Color.white);
        southPanel.add(anteLabel);
        southPanel.add(currencyLabel);
        this.add(northPanel);
        this.add(southPanel);
    }

    /**
     * This method updates hands and discards.
     * @param stats
     */
    public void updateHands(final BlindStats stats) {
        this.handLabel.setText("Hand: " + stats.hands());
        this.discardLabel.setText("Discard: " + stats.discards());
    }

    /**
     * This method updates the currency held by the player.
     * @param currency
     */
    public void updateCurrency(final int currency) {
        this.currencyLabel.setText("$" + String.valueOf(currency));
    }

    /**
     * This method updates with some informations about the current ante.
     * @param info
     */
    public void updateAnte(final AnteInfo info) {
        this.anteLabel.setText("Ante: " + String.valueOf(info.id()));;
    }
}
