package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

/**
 * Displays the statistics of the current blind.
 * (remaining discards, hand, current currency and ante).
 */
public class HandPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final CombinationFrame combFrame;
    private final JLabel handLabel;
    private final JLabel discardLabel;
    private final JLabel currencyLabel;
    private final JLabel anteLabel;
    private final int numAnte;
    private boolean isCombinationVisible;

    /**
     * Builds the panel with information about player stats
     * and a button for opening a new frame that has
     * every information about combinations could the player do.
     * @param availableCombinations
     * @param numAnte number of ante
     */
    public HandPanel(final List<CombinationInfo> availableCombinations, final int numAnte) {
        super(new GridLayout(3, 1));
        this.numAnte = numAnte;
        this.combFrame = new CombinationFrame(availableCombinations);
        this.handLabel = createGeneralLabel("");
        this.discardLabel = createGeneralLabel("");
        this.currencyLabel = createGeneralLabel("$0");
        this.anteLabel = createGeneralLabel("Ante: /" + numAnte);
        final JButton combinationButton = new JButton("Available Combinations");
        combinationButton.setBackground(Color.DARK_GRAY.darker());
        combinationButton.setForeground(Color.WHITE);
        combinationButton.addActionListener(e -> {
            isCombinationVisible = !isCombinationVisible;
            combFrame.setVisible(isCombinationVisible);
        });
        super.add(createNorthPanel());
        super.add(createSouthPanel());
        super.add(combinationButton);
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
        this.anteLabel.setText("Ante: " + info.id() + " / " + this.numAnte);
    }
}
