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

    private TitlePanel titlePanel;
    private ScorePanel scorePanel;
    private CombinationPanel combinationPanel;
    private HandPanel handPanel;

    public InfoPanel(BlindInfo info, BlindStats stats) {
        this.setLayout(new GridLayout(4, 1));
        this.titlePanel = new TitlePanel(info);
        this.scorePanel = new ScorePanel(info, stats);
        this.combinationPanel = new CombinationPanel(new CombinationInfo(" ", 0, 0));
        this.handPanel = new HandPanel(stats);
        this.add(this.titlePanel);
        this.add(this.scorePanel);
        this.add(this.combinationPanel);
        this.add(this.handPanel);
    }

    public void updateCombination(final CombinationInfo info) {
        this.combinationPanel.updateCombination(info);
    }

    public void updateStats(final BlindStats stats) {
        this.scorePanel.updateScore(stats);
        this.handPanel.updateHands(stats);
    }


}
