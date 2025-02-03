package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;

public class ScorePanel extends JPanel {

    private static final int SCORE_DIM = 36;
    private final JLabel minimumScoreLabel;
    private final JLabel currentScoreLabel;

    public ScorePanel(BlindInfo info, BlindStats stats) {
        this.setLayout(new BorderLayout());
        final JPanel mainScoreContainer = new JPanel(new BorderLayout());
        mainScoreContainer.add(getMainTitleLabel(), BorderLayout.CENTER);
        mainScoreContainer.setPreferredSize(new Dimension(0, 100));
        this.add(mainScoreContainer, BorderLayout.NORTH);
        this.currentScoreLabel = getCurrentScorePanel(stats);
        this.minimumScoreLabel = getMinimumScoreLabel(info);
        final JPanel scoreContainer = new JPanel(new GridLayout(1, 2));
        scoreContainer.add(currentScoreLabel);
        scoreContainer.add(minimumScoreLabel);
        this.add(scoreContainer, BorderLayout.CENTER);

    }

    private JLabel getCurrentScorePanel(BlindStats stats) {
        final JLabel currentScore = new JLabel();
        currentScore.setText(String.valueOf(stats.chips()));
        currentScore.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        currentScore.setFont(new Font("Bauhaus 93", Font.PLAIN, SCORE_DIM));
        currentScore.setBackground(Color.GREEN.darker());
        currentScore.setOpaque(true);
        return currentScore;
    }

    private JLabel getMainTitleLabel() {
        final JLabel scoreLabel = new JLabel();
        scoreLabel.setText("SCORE");
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scoreLabel.setFont(new Font("Bauhaus 93", Font.BOLD, SCORE_DIM));
        scoreLabel.setBackground(Color.orange.darker().darker());
        scoreLabel.setOpaque(true);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return scoreLabel;
    }

    private JLabel getMinimumScoreLabel(BlindInfo info) {
        final JLabel minimumScoreLabel = new JLabel();
        minimumScoreLabel.setText(String.valueOf(info.minimumChips()));
        minimumScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        minimumScoreLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, SCORE_DIM));
        minimumScoreLabel.setBackground(Color.MAGENTA.darker());
        minimumScoreLabel.setOpaque(true);
        return minimumScoreLabel;
    }

    public void updateScore(BlindStats stats) {
        // TODO Auto-generated method stub
        this.currentScoreLabel.setText(String.valueOf(stats.chips()));
    }

}
