package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
/**
 * Show the Blind over GUI with statistics and
 * possibility to open the shop.
 */
public class BlindOver extends JPanel {
    static final long serialVersionUID = 1L;

    /**
     * Builds the GUI.
     * @param controller master controller.
     * @param blindInfo static info about the blind.
     * @param blindStats statistic of the game till now.
     */
    public BlindOver(final MasterController controller, final BlindInfo blindInfo, final BlindStats blindStats) {
        super(new BorderLayout());
        final JButton button = new JButton("OPEN SHOP");
        button.addActionListener(a -> controller.handleEvent(BalatroEvent.OPEN_SHOP, null));
        final JTextArea text = new JTextArea();
        text.setEditable(false);
        text.append("BLIND DEFEATED");
        text.append("\nSTATISTICS");
        text.append("\nCurrent Blind: " + blindInfo.id());
        text.append("\nReward: " + blindInfo.reward());
        text.append("\nChips earned: " + blindStats.chips());
        this.add(text, BorderLayout.CENTER);
        this.add(button, BorderLayout.SOUTH);
        this.setVisible(true);
    }

}
