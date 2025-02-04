package it.unibo.balatrolt.view.impl;

import java.awt.GridLayout;

import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

/**
 * Creates the left part of the main GUI.
 */
public class InfoPanel extends JPanel {
    static final long serialVersionUID = 1L;
    private final ScorePanel scorePanel;
    private final CombinationPanel combinationPanel;
    private final HandPanel handPanel;

    public InfoPanel(final BlindInfo info, final BlindStats stats) {
        this.setLayout(new GridLayout(4, 1));
        final var titlePanel = new TitlePanel(info);
        this.scorePanel = new ScorePanel(info, stats);
        this.combinationPanel = new CombinationPanel(new CombinationInfo(" ", 0, 0));
        this.handPanel = new HandPanel(stats);
        add(titlePanel);
        add(this.scorePanel);
        add(this.combinationPanel);
        add(this.handPanel);
    }

    public void updateCombination(final CombinationInfo info) {
        this.combinationPanel.updateCombination(info);
    }

    public void updateStats(final BlindStats stats) {
        this.scorePanel.updateScore(stats);
        this.handPanel.updateHands(stats);
    }


}
