package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

/**
 * Shows the base point, multiplier and combination you would get with
 * the cards that are selected (in real-time).
 */
public final class CombinationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String FONT = "COPPER_BLACK";
    private static final int SCORE_DIM = 30;
    private final transient FontFactory fontFactory = new FontFactory();
    private final JLabel combinationLabel;
    private final JLabel basePointsLabel;
    private final JLabel multiplierLabel;

    /**
     * Constructor for building the main panel, with some
     * labels that shows points, multiplier and what type of
     * combination is done.
     * @param info start info
     */
    public CombinationPanel(final CombinationInfo info) {
        this.setLayout(new BorderLayout());
        combinationLabel = getCombinationLabel();
        basePointsLabel = getBasePointsLabel();
        multiplierLabel = getMultiplierLabel();
        final JPanel mainPanel = new JPanel();
        final int row = 2;
        final int col = 2;
        mainPanel.setLayout(new GridLayout(row, col));
        final JLabel pointsTitle = getBasePointsLabel();
        pointsTitle.setBackground(pointsTitle.getBackground().darker());
        pointsTitle.setText("Base Points");
        pointsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pointsTitle.setFont(this.fontFactory.getFont(FONT, SCORE_DIM / 3 * 2, this));
        mainPanel.add(pointsTitle);
        final JLabel multiplierTitle = getMultiplierLabel();
        multiplierTitle.setBackground(multiplierTitle.getBackground().darker());
        multiplierTitle.setText("Multiplier");
        multiplierTitle.setFont(this.fontFactory.getFont(FONT, SCORE_DIM / 3 * 2, this));
        multiplierTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(multiplierTitle);
        mainPanel.add(basePointsLabel);
        mainPanel.add(multiplierLabel);
        updateCombination(info);
        this.add(this.combinationLabel, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.SOUTH);
    }

    /**
     * This method builds the combination label.
     * @return the label in question
     */
    private JLabel getCombinationLabel() {
        final JLabel combinationLabel = new JLabel();
        combinationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        combinationLabel.setFont(this.fontFactory.getFont(FONT, SCORE_DIM, this));
        combinationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        combinationLabel.setBackground(Color.DARK_GRAY.darker());
        combinationLabel.setForeground(Color.white);
        combinationLabel.setOpaque(true);
        return combinationLabel;
    }

    /**
     * This method builds the base points label.
     * @return the label in question
     */
    private JLabel getBasePointsLabel() {
        final JLabel pointsLabel = new JLabel();
        pointsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsLabel.setFont(this.fontFactory.getFont(FONT, SCORE_DIM, this));
        pointsLabel.setBackground(Color.decode("#2274A5"));
        pointsLabel.setForeground(Color.white);
        pointsLabel.setOpaque(true);
        return pointsLabel;
    }

    /**
     * This method builds the multiplier label.
     * @return the label in question
     */
    private JLabel getMultiplierLabel() {
        final JLabel multiplierLabel = new JLabel();
        multiplierLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        multiplierLabel.setFont(this.fontFactory.getFont(FONT, SCORE_DIM, this));
        multiplierLabel.setHorizontalAlignment(SwingConstants.CENTER);
        multiplierLabel.setBackground(Color.decode("#c1121f"));
        multiplierLabel.setForeground(Color.white);
        multiplierLabel.setOpaque(true);
        return multiplierLabel;
    }

    /**
     * Updates the informations (base point, multiplier and the combination).
     * @param info about the new combination done.
     */
    public void updateCombination(final CombinationInfo info) {
        this.combinationLabel.setText(info.name());
        this.basePointsLabel.setText(String.valueOf(info.points()));
        this.multiplierLabel.setText(String.valueOf(info.multiplier()));
    }
}
