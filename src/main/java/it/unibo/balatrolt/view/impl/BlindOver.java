package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;

public class BlindOver extends JPanel {

    public BlindOver(MasterController controller, String title, BlindInfo blindInfo, BlindStats blindStats) {
        super(new BorderLayout());
        var button = new JButton("OPEN SHOP");
        button.addActionListener(a -> controller.handleEvent(BalatroEvent.OPEN_SHOP, null));
        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.append(title);
        text.append("\nSTATISTICS");
        text.append("\nCurrent Blind: " + blindInfo.id());
        text.append("\nReward: " + blindInfo.reward());
        text.append("\nChips earned: " + blindStats.chips());
        this.add(text, BorderLayout.CENTER);
        this.add(button, BorderLayout.SOUTH);
        this.setVisible(true);
    }

}
