package it.unibo.balatrolt.view.impl;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;

public class MainMenu extends JPanel {

    public MainMenu(MasterController controller, String testo) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        var label = new JLabel("BALATRO");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalGlue());
        this.add(label);
        var button = new JButton(testo);
        button.addActionListener(a -> controller.handleEvent(BalatroEvent.INIT_GAME, null));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalGlue());
        this.add(button);
        this.add(Box.createVerticalGlue());
        this.setVisible(true);
    }

}
