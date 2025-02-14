package it.unibo.balatrolt.view.impl;

import java.util.List;

import java.awt.GridLayout;

import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

/**
 * Creates the left part of the main GUI.
 */
final class InfoPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final ScorePanel scorePanel;
    private final CombinationPanel combinationPanel;
    private final HandPanel handPanel;

    /**
     * Constructor that builds the main info panel.
     * @param info about the blind
     * @param stats about the actual game
     * @param combinations available
     * @param numAnte number of ante
     */
    InfoPanel(final BlindInfo info, final BlindStats stats, final List<CombinationInfo> combinations, final int numAnte) {
        this.setLayout(new GridLayout(4, 1));
        final var titlePanel = new TitlePanel(info);
        this.scorePanel = new ScorePanel(info, stats);
        this.combinationPanel = new CombinationPanel(new CombinationInfo(" ", 0, 0));
        this.handPanel = new HandPanel(combinations, numAnte);
        this.handPanel.updateHands(stats);
        add(titlePanel);
        add(this.scorePanel);
        add(this.combinationPanel);
        add(this.handPanel);
    }

    /**
     * Updates the combination info.
     * @param info
     */
    void updateCombination(final CombinationInfo info) {
        this.combinationPanel.updateCombination(info);
    }

    /**
     * Updates the statistics in the GUI.
     * @param stats
     */
    void updateStats(final BlindStats stats) {
        this.scorePanel.updateScore(stats);
        this.handPanel.updateHands(stats);
    }

    /**
     * Updates the currency held by the player.
     * @param currency
     */
    void updateCurrency(final int currency) {
        this.handPanel.updateCurrency(currency);
    }

    /**
     * Updates the information about the current ante.
     * @param info
     */
    void updateAnte(final AnteInfo info) {
        this.handPanel.updateAnte(info);
    }

}
