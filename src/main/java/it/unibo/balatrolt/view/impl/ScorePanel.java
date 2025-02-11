package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
/**
 * Display the scores (actual score and the one to beat).
 */
public final class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String SCORE_FONT = "COPPER_BLACK";
    private static final float SCORE_SIZE = 36f;
    private static final FontFactory FONT_FACTORY = new FontFactory();
    private final JLabel minimumScoreLabel;
    private final JLabel currentScoreLabel;

    /**
     * The constructor set the whole thing, calling
     * up methods that set every thing that has to be in a score panel.
     * @param info
     * @param stats
     */
    public ScorePanel(final BlindInfo info, final BlindStats stats) {
        final Font currentScoreFont = FONT_FACTORY.getFont(SCORE_FONT, SCORE_SIZE, this);
        final Font mainFont = FONT_FACTORY.getFont(SCORE_FONT, SCORE_SIZE / 2, this);
        final Font minimumScoreFont = FONT_FACTORY.getFont(SCORE_FONT, SCORE_SIZE, this);
        this.currentScoreLabel = getCurrentScorePanel(stats, currentScoreFont);
        this.minimumScoreLabel = getMinimumScoreLabel(info, minimumScoreFont);
        initializePanel(mainFont);
    }

    /**
     * initialize this panel.
     * @param font
     */
    private void initializePanel(final Font font) {
        super.setLayout(new BorderLayout());
        final JPanel mainScoreContainer = new JPanel(new BorderLayout());
        mainScoreContainer.add(getMainTitleLabel(font), BorderLayout.CENTER);
        super.add(mainScoreContainer, BorderLayout.NORTH);
        final JPanel scoreContainer = new JPanel(new GridLayout(3, 1));
        scoreContainer.add(minimumScoreLabel);
        final JLabel current = getMainTitleLabel(font);
        current.setText("Current score: ");
        scoreContainer.add(current);
        scoreContainer.add(currentScoreLabel);
        scoreContainer.setBorder(BorderFactory.createLineBorder(currentScoreLabel.getBackground()));
        super.add(scoreContainer, BorderLayout.CENTER);
    }

    /**
     * This method create the label for the current score
     * made by the player.
     * @param stats
     * @param font
     * @return the label in question
     */
    private JLabel getCurrentScorePanel(final BlindStats stats, final Font font) {
        final JLabel currentScore = new JLabel();
        currentScore.setText(String.valueOf(stats.chips()));
        currentScore.setFont(font);
        currentScore.setBackground(Color.DARK_GRAY);
        currentScore.setForeground(Color.white);
        currentScore.setOpaque(true);
        currentScore.setHorizontalAlignment(SwingConstants.CENTER);
        currentScore.setBorder(BorderFactory.createLineBorder(currentScore.getBackground()));
        return currentScore;
    }

    /**
     * Set the label with SCORE.
     * @param font
     * @return the label in question
     */
    private JLabel getMainTitleLabel(final Font font) {
        final JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score at least: ");
        scoreLabel.setFont(font);
        scoreLabel.setBackground(Color.DARK_GRAY);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setOpaque(true);
        scoreLabel.setBorder(BorderFactory.createLineBorder(scoreLabel.getBackground()));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return scoreLabel;
    }

    /**
     * This method return the label with the minimum score to be reached.
     * @param info
     * @param font
     * @return the label in question
     */
    private JLabel getMinimumScoreLabel(final BlindInfo info, final Font font) {
        final JLabel minimumScoreLabel = new JLabel();
        minimumScoreLabel.setText(String.valueOf(info.minimumChips()));
        minimumScoreLabel.setFont(font);
        minimumScoreLabel.setBackground(Color.DARK_GRAY);
        minimumScoreLabel.setForeground(Color.RED.darker());
        minimumScoreLabel.setBorder(BorderFactory.createLineBorder(minimumScoreLabel.getBackground()));
        minimumScoreLabel.setOpaque(true);
        minimumScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return minimumScoreLabel;
    }

    /**
     * This method updates the visualized score.
     * @param stats
     */
    public void updateScore(final BlindStats stats) {
        this.currentScoreLabel.setText(String.valueOf(stats.chips()));
    }
}
