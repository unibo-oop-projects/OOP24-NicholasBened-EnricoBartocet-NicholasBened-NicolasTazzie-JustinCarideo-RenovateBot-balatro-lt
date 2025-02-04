package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

public class CombinationPanel extends JPanel {
    private static final String FONT = "COPPER_BLACK";
    private static final int SCORE_DIM = 30;
    private final JLabel combinationLabel;
    private final JLabel basePointsLabel;
    private final JLabel multiplierLabel;

    public CombinationPanel(CombinationInfo info) {
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

    private JLabel getCombinationLabel(CombinationInfo info) {
        final JLabel combinationLabel = new JLabel();
        combinationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        combinationLabel.setFont(getFont(FONT, SCORE_DIM));
        combinationLabel.setBackground(Color.white);
        combinationLabel.setOpaque(true);
        return combinationLabel;
    }

    private JLabel getBasePointsLabel(CombinationInfo info) {
        final JLabel pointsLabel = new JLabel();
        pointsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pointsLabel.setFont(getFont(FONT, SCORE_DIM));
        pointsLabel.setBackground(Color.BLUE);
        pointsLabel.setOpaque(true);
        return pointsLabel;
    }

    private JLabel getMultiplierLabel(CombinationInfo info) {
        final JLabel multiplierLabel = new JLabel();
        multiplierLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        multiplierLabel.setFont(getFont(FONT, SCORE_DIM));
        multiplierLabel.setBackground(Color.RED.darker());
        multiplierLabel.setOpaque(true);
        return multiplierLabel;
    }

    public void updateCombination(CombinationInfo info) {
        this.combinationLabel.setText(info.name());
        this.basePointsLabel.setText(String.valueOf(info.points()));
        this.multiplierLabel.setText(String.valueOf(info.multiplier()));
    }

    /**
     * Gives back the requested font with the given size.
     */
    private Font getFont(String nameFont, float fontSize) {
        Font font = new Font("Arial", Font.PLAIN, 15);
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
