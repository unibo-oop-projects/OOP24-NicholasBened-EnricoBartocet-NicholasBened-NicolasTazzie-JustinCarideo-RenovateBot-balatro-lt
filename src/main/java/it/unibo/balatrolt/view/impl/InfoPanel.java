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
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;

/**
 * Creates the left part of the main GUI.
 */
public class InfoPanel {
    private static final int SIZE_BIG_BLIND = 36;
    private final BlindInfo info;
    private final BlindStats stats;

    public InfoPanel(BlindInfo info, BlindStats stats, List<PlayableCardInfo> playableCards) {
        final JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        this.stats = stats;
        this.info = info;
    }

    /**
     * @return the completed left panel
     */
    public final JPanel build() {
        final JPanel leftPanel = new JPanel(new GridLayout(4, 1));

        leftPanel.add(new TitlePanel(this.info));
        leftPanel.add(new ScorePanel(this.info, this.stats));

        final JLabel multiplicators = new JLabel("--somma carte-- X --moltiplicatori--", SwingConstants.CENTER);
        multiplicators.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(new CombinationPanel());

        final JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JPanel leftInfoPanel = new JPanel(new GridLayout(2, 1));

        final ActionListener al = e -> {
            JOptionPane.showMessageDialog(leftPanel, "ciaooooo");
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

        infoPanel.add(rightInfoPanel);
        leftPanel.add(infoPanel);

        return leftPanel;
    }
}
