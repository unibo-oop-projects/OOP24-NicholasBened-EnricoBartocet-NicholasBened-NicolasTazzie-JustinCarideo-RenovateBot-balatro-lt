package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

/**
 * This class represents the view of the combination table.
 */
class CombinationFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String FONT = "COPPER_BLACK";
    private static final int FONT_DIM = 30;
    private static final FontFactory FONT_FACTORY = new FontFactory();

    /**
     * Constructor builds the table with 3 fields:
     * Combination, Points and Multiplier.
     * @param combinations to be represented
     */
    CombinationFrame(final List<CombinationInfo> combinations) {
        super.setSize(
            Toolkit.getDefaultToolkit().getScreenSize().width / 2,
            Toolkit.getDefaultToolkit().getScreenSize().height / 2
        );
        final JPanel mainPanel = new JPanel(new GridLayout(combinations.size() + 1, 3));
        mainPanel.add(createGeneralLabel("Combination", Color.DARK_GRAY.darker()));
        mainPanel.add(createGeneralLabel("Points", Color.decode("#2274A5").darker()));
        mainPanel.add(createGeneralLabel("Multiplier", Color.decode("#c1121f").darker()));
        super.add(mainPanel);
        super.setTitle("Combinations Table");
        for (final CombinationInfo combinationInfo : combinations) {
            mainPanel.add(createGeneralLabel(combinationInfo.name(), Color.DARK_GRAY));
            mainPanel.add(createGeneralLabel(String.valueOf(combinationInfo.points()), Color.decode("#2274A5")));
            mainPanel.add(createGeneralLabel(String.valueOf(combinationInfo.multiplier()), Color.decode("#c1121f")));
        }
        final JScrollPane scrollPane = new JScrollPane(mainPanel);
        super.add(scrollPane);
    }

    private JLabel createGeneralLabel(final String text, final Color color) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        label.setFont(FONT_FACTORY.getFont(FONT, FONT_DIM / 2, this));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }
}
