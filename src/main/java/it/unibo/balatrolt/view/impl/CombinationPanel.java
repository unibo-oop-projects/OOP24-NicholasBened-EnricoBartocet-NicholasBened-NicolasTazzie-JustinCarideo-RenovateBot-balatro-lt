package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.checkerframework.checker.units.qual.m;

import it.unibo.balatrolt.controller.api.communication.CombinationInfo;
/**
 * Shows the base point, multiplier and combination you would get with
 * the cards that are selected at that moment (in real-time).
 */
public final class CombinationPanel extends JPanel {
    private static final String FONT = "COPPER_BLACK";
    private static final int SCORE_DIM = 30;
    private final JLabel combinationLabel;
    private final JLabel basePointsLabel;
    private final JLabel multiplierLabel;

    public CombinationPanel(final CombinationInfo info) {
        this.setLayout(new BorderLayout());
        combinationLabel = getCombinationLabel(info);
        basePointsLabel = getBasePointsLabel(info);
        multiplierLabel = getMultiplierLabel(info);
        final JPanel mainPanel = new JPanel();
        final int row = 1;
        final int col = 2;
        mainPanel.setLayout(new GridLayout(row, col));
        mainPanel.add(basePointsLabel);
        mainPanel.add(multiplierLabel);
        updateCombination(info);
        this.add(this.combinationLabel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private JLabel getCombinationLabel(final CombinationInfo info) {
        final JLabel combinationLabel = new JLabel();
        combinationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        combinationLabel.setFont(getFont(FONT, SCORE_DIM));
        combinationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        combinationLabel.setBackground(Color.GRAY);
        combinationLabel.setOpaque(true);
        return combinationLabel;
    }

    private JLabel getBasePointsLabel(final CombinationInfo info) {
        final JLabel pointsLabel = new JLabel();
        pointsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsLabel.setFont(getFont(FONT, SCORE_DIM));
        pointsLabel.setBackground(Color.BLUE);
        pointsLabel.setOpaque(true);
        return pointsLabel;
    }

    private JLabel getMultiplierLabel(final CombinationInfo info) {
        final JLabel multiplierLabel = new JLabel();
        multiplierLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        multiplierLabel.setFont(getFont(FONT, SCORE_DIM));
        multiplierLabel.setHorizontalAlignment(SwingConstants.CENTER);
        multiplierLabel.setBackground(Color.RED.darker());
        multiplierLabel.setOpaque(true);
        return multiplierLabel;
    }

    /**
     * Updates the informations (base point, multiplier and the combination).
     * @param info Combination info relaativeto the selected cards.
     */
    public void updateCombination(final CombinationInfo info) {
        this.combinationLabel.setText(info.name());
        this.basePointsLabel.setText(String.valueOf(info.points()));
        this.multiplierLabel.setText(String.valueOf(info.multiplier()));
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot load font");
        }
        return font;
    }
}
