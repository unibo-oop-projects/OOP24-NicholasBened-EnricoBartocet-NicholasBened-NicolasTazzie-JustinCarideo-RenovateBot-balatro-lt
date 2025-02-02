package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;

public class DeckSelector extends JPanel {
    private Map<String, DeckInfo> decksTranslator = new HashMap<>();

    public DeckSelector(MasterController controller, Set<DeckInfo> decks) {
        setLayout(new BorderLayout());
        JComboBox<String> list = new JComboBox<>();
        var centerPanel = new JPanel(new FlowLayout());
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(new JLabel("Choose your deck:"));
        centerPanel.add(list);
        decks.forEach(d -> {
            list.addItem(d.name());
            decksTranslator.put(d.name(), d);
        });
        list.setSelectedIndex(-1);
        var button = new JButton("Select");
        button.addActionListener(e -> {
            try {
                controller.handleEvent(BalatroEvent.CHOOSE_DECK, Optional.of(decksTranslator.get((list.getSelectedItem()))));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "You have to chose a deck", "DECK", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(button, BorderLayout.SOUTH);
        setVisible(true);
    }

}
