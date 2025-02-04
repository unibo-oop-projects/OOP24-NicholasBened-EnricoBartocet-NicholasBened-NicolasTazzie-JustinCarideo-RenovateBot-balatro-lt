package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;

public class HandPanel extends JPanel {
    static final long serialVersionUID = 1L;
    private final JLabel handLabel = new JLabel();
    private final JLabel discardLabel = new JLabel();
    private final JLabel currencyLabel = new JLabel("$0");
    private final JLabel anteLabel = new JLabel("Ante: ");

    public HandPanel(final BlindStats stats) {
        setLayout(new GridLayout(1, 2));
        updateHands(stats);
        add(getButtonsPanel(), BorderLayout.WEST);
        add(getStatsPanel(), BorderLayout.EAST);
    }

    private JPanel getStatsPanel() {
        final JPanel statsPanel = new JPanel(new GridLayout(3, 1));
        final JPanel northPanel = new JPanel(new GridLayout(1, 2));
        handLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        discardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        northPanel.add(handLabel);
        northPanel.add(discardLabel);
        this.currencyLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JPanel southPanel = new JPanel(new GridLayout(1, 2));
        anteLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JLabel roundPanel = new JLabel("Round: ");
        roundPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        southPanel.add(anteLabel);
        southPanel.add(roundPanel);
        statsPanel.add(northPanel);
        statsPanel.add(currencyLabel);
        statsPanel.add(southPanel);
        return statsPanel;
    }

    private JPanel getButtonsPanel() {
        final JPanel main = new JPanel(new GridLayout(2, 1));
        final JButton buttonInfo = new JButton("Run Info");
        final JButton buttonOptions = new JButton("Options");
        main.add(buttonInfo, SwingConstants.CENTER);
        main.add(buttonOptions, SwingConstants.CENTER);
        return main;
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
     * @param currency
     */
    public void updateCurrency(final int currency) {
        this.currencyLabel.setText("$" + String.valueOf(currency));
    }

    /**
     * @param currency
     */
    public void updateAnte(AnteInfo info) {
        this.anteLabel.setText("Ante :" + String.valueOf(info.id()));;
    }
}
