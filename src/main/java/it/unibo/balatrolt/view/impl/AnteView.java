package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.AnteInfo;
/**
 * Displays the ante and relative blind info.
 */
public class AnteView extends JPanel {
    static final long serialVersionUID = 1L;

    /**
     * Builds the GUI.
     * @param controller master controller.
     * @param anteInfo info's of the current ante.
     */
    public AnteView(final MasterController controller, final AnteInfo anteInfo) {
        super(new BorderLayout());
        this.setBackground(Color.GREEN.darker().darker().darker().darker());
        final JLabel title = new JLabel("Ante n. " + anteInfo.id(), JLabel.CENTER);
        title.setForeground(Color.WHITE);

        final JPanel columns = new JPanel(new GridLayout(1, anteInfo.blinds().size()));
        columns.setBackground(getBackground());

        final JButton playButton = new JButton("START BLIND " + anteInfo.currentBlindId());
        playButton.setBackground(Color.decode("#2274A5"));
        playButton.setForeground(Color.WHITE);
        playButton.addActionListener(a -> controller.handleEvent(BalatroEvent.SHOW_BLINDS, null));

        for (var blind: anteInfo.blinds()) {
            var column = new JPanel();
            column.setLayout(new BoxLayout(column, BoxLayout.PAGE_AXIS));
            column.setOpaque(false);
            var blindTitle = new JLabel("Blind " + blind.id());
            var chips = new JLabel("Min. chips: " + blind.minimumChips());
            var reward = new JLabel("Reward: " + blind.reward() + "$");
            blindTitle.setAlignmentX(CENTER_ALIGNMENT);
            chips.setAlignmentX(CENTER_ALIGNMENT);
            reward.setAlignmentX(CENTER_ALIGNMENT);
            chips.setForeground(Color.WHITE);
            reward.setForeground(Color.WHITE);
            blindTitle.setForeground(Color.WHITE);
            column.add(Box.createRigidArea(new Dimension(0,30)));
            column.add(blindTitle);
            column.add(Box.createRigidArea(new Dimension(0,100)));
            column.add(reward);
            column.add(chips);
            column.add(Box.createRigidArea(new Dimension(0,100)));
            if (blind.id() == anteInfo.currentBlindId()) {
                playButton.setAlignmentX(CENTER_ALIGNMENT);
                column.add(playButton);
            }
            columns.add(column);
        }

        /*
        for (int i = 0; i < anteInfo.blinds().size(); i++) {
            text.append("\n\nBlind " + anteInfo.blinds().get(i).id());
            if (i == anteInfo.currentBlindId()) {
                text.append(" (CURRENT BLIND)");
            }
            text.append("\n    Chips Required: " + anteInfo.blinds().get(i).minimumChips());
            text.append("\n    Reward: " + anteInfo.blinds().get(i).reward());
        }*/
        add(title, BorderLayout.NORTH);
        add(columns, BorderLayout.CENTER);
        setVisible(true);
    }

}
