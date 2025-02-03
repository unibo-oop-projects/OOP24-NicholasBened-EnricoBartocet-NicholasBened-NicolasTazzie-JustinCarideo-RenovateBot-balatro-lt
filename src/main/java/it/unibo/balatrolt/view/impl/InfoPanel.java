package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;

/**
 * Creates the left part of the main GUI.
 */
public class InfoPanel extends JPanel {

    private TitlePanel titlePanel;
    private ScorePanel scorePanel;
    private CombinationPanel combinationPanel;

    public InfoPanel(BlindInfo info, BlindStats stats, List<PlayableCardInfo> playableCards) {
        this.setLayout(new GridLayout(4, 1));
        this.titlePanel = new TitlePanel(info);
        this.scorePanel = new ScorePanel(info, stats);
        this.combinationPanel = new CombinationPanel();
        this.add(this.titlePanel);
        this.add(this.scorePanel);
        this.add(this.combinationPanel);

        final JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JPanel leftInfoPanel = new JPanel(new GridLayout(2, 1));

        final ActionListener al = e -> {
            JOptionPane.showMessageDialog(this, "ciaooooo");
        };

        final JButton infoButton = new JButton("Run info");
        infoButton.setBackground(Color.ORANGE);
        final JButton optionButton = new JButton("Options");
        optionButton.setBackground(Color.ORANGE);
        infoButton.addActionListener(al);
        optionButton.addActionListener(al);

        leftInfoPanel.add(infoButton, SwingConstants.CENTER);
        leftInfoPanel.add(optionButton, SwingConstants.CENTER);

        infoPanel.add(leftInfoPanel, BorderLayout.WEST);

        final JPanel rightInfoPanel = new JPanel(new GridLayout(3, 2));
        rightInfoPanel.setBackground(Color.red.darker());
        final JLabel hands = new JLabel("Hands : xxx");
        hands.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JLabel money = new JLabel("money : xxx");
        money.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JLabel discards = new JLabel("Discards : xxx");
        discards.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JLabel ante = new JLabel("Ante : xxx");
        ante.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final JLabel round = new JLabel("Round : xxx");
        round.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightInfoPanel.add(hands);
        rightInfoPanel.add(discards);
        rightInfoPanel.add(money);
        rightInfoPanel.add(ante);
        rightInfoPanel.add(round);
        this.add(infoPanel);
        infoPanel.add(rightInfoPanel);
    }

    public void updateCombination(final CombinationInfo info) {
        
    }

    public void updateBlindStatistics(final BlindStats stats) {
        
    }
}
