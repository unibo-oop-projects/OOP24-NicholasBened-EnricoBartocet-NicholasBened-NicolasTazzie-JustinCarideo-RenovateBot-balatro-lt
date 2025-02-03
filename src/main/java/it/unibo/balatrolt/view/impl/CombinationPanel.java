package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CombinationPanel extends JPanel {

    private static final int SCORE_DIM = 30;

    public CombinationPanel() {
        this.setLayout(new BorderLayout());
        this.add(getBasePointsLabel(), BorderLayout.EAST);
        this.add(getMultiplierLabel(), BorderLayout.WEST);
    }

    private JLabel getBasePointsLabel() {
        final JLabel pointsLabel = new JLabel();
        pointsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pointsLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, SCORE_DIM));
        pointsLabel.setBackground(Color.BLUE.darker());
        pointsLabel.setOpaque(true);
        return pointsLabel;
    }

    private JLabel getMultiplierLabel() {
        final JLabel multiplierLabel = new JLabel();
        multiplierLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        multiplierLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, SCORE_DIM));
        multiplierLabel.setBackground(Color.RED.darker());
        multiplierLabel.setOpaque(true);
        return multiplierLabel;
    }

}
