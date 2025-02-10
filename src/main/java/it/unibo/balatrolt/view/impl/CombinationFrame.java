package it.unibo.balatrolt.view.impl;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.communication.CombinationInfo;

public class CombinationFrame extends JFrame {

    public CombinationFrame(List<CombinationInfo> combinations) {
        final JPanel mainPanel = new JPanel(new GridLayout(combinations.size(), 3));
        super.add(mainPanel);
        for (CombinationInfo combinationInfo : combinations) {
            mainPanel.add(new JLabel(combinationInfo.name()));
            mainPanel.add(new JLabel(String.valueOf(combinationInfo.points())));
            mainPanel.add(new JLabel(String.valueOf(combinationInfo.multiplier())));
        }
    }

}
