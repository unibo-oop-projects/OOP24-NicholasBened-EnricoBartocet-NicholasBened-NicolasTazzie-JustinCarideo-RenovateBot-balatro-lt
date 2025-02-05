package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
/**
 * Display the scores (actual score and the one to beat).
 */
public class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String SCORE_FONT = "COPPER_BLACK";
    private static final float SCORE_SIZE = 36f;
    private final JLabel minimumScoreLabel;
    private final JLabel currentScoreLabel;

    /**
     * The constructor set the whole thing, calling
     * up methods that set every thing that has to be in a score panel.
     * @param info
     * @param stats
     */
    public ScorePanel(final BlindInfo info, final BlindStats stats) {
        this.currentScoreLabel = getCurrentScorePanel(stats);
        this.minimumScoreLabel = getMinimumScoreLabel(info);
        initializePanel();
    }

    private void initializePanel() {
        super.setLayout(new BorderLayout());
        final JPanel mainScoreContainer = new JPanel(new BorderLayout());
        mainScoreContainer.add(getMainTitleLabel(), BorderLayout.CENTER);
        super.add(mainScoreContainer, BorderLayout.NORTH);
        final JPanel scoreContainer = new JPanel(new GridLayout(3, 1));
        scoreContainer.add(minimumScoreLabel);
        final JLabel current = getMainTitleLabel();
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
     * @return the label in question
     */
    private JLabel getCurrentScorePanel(final BlindStats stats) {
        final JLabel currentScore = new JLabel();
        currentScore.setText(String.valueOf(stats.chips()));
        currentScore.setFont(getFont(SCORE_FONT, SCORE_SIZE));
        currentScore.setBackground(Color.DARK_GRAY);
        currentScore.setForeground(Color.white);
        currentScore.setOpaque(true);
        currentScore.setHorizontalAlignment(SwingConstants.CENTER);
        currentScore.setBorder(BorderFactory.createLineBorder(currentScore.getBackground()));
        return currentScore;
    }

    /**
     * Set the label with SCORE.
     * @return the label in question
     */
    private JLabel getMainTitleLabel() {
        final JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score at least: ");
        scoreLabel.setFont(getFont(SCORE_FONT, SCORE_SIZE / 2));
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
     * @return the label in question
     */
    private JLabel getMinimumScoreLabel(final BlindInfo info) {
        final JLabel minimumScoreLabel = new JLabel();
        minimumScoreLabel.setText(String.valueOf(info.minimumChips()));
        minimumScoreLabel.setFont(getFont(SCORE_FONT, SCORE_SIZE));
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

    /*
     * Gives back the requested font with the given size.
     */
    private Font getFont(final String nameFont, final float fontSize) {
        Font font = new Font("Arial", Font.PLAIN, (int) fontSize);
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/font/" + nameFont + ".TTF")
            );
            font = font.deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot load font");
        }
        return font;
    }
}
