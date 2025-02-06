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
public final class AnteView extends JPanel {
    static final long serialVersionUID = 1L;
    private final FontFactory fontFactory = new FontFactory();
    private static final String FONT = "COPPER_BLACK";
    private static final int TOP_DISTANCE = 30;
    private static final int BUTTON_DISTANCE = 50;

    private static final float TITLE_SIZE = 50f;
    private static final float BLIND_SIZE = 26f;
    private static final float LABEL_SIZE = 21f;

    /**
     * Builds the GUI.
     * @param controller master controller.
     * @param anteInfo info's of the current ante.
     */
    public AnteView(final MasterController controller, final AnteInfo anteInfo) {
        super(new BorderLayout());
        this.setBackground(Color.GREEN.darker().darker().darker().darker());
        final JLabel title = new JLabel("Ante n. " + anteInfo.id(), JLabel.CENTER);
        title.setFont(this.fontFactory.getFont(FONT, TITLE_SIZE, this));
        title.setForeground(Color.WHITE);

        final JPanel columns = new JPanel(new GridLayout(1, anteInfo.blinds().size()));
        columns.setBackground(getBackground());

        final JButton playButton = new JButton("START BLIND " + anteInfo.currentBlindId());
        playButton.setBackground(Color.decode("#2274A5"));
        playButton.setForeground(Color.WHITE);
        playButton.addActionListener(a -> controller.handleEvent(BalatroEvent.SHOW_BLINDS, null));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        playButton.setFont(this.fontFactory.getFont(FONT, LABEL_SIZE, this));

        for (final var blind: anteInfo.blinds()) {
            final var column = new JPanel();
            column.setLayout(new BoxLayout(column, BoxLayout.PAGE_AXIS));
            column.setOpaque(false);
            column.add(Box.createRigidArea(new Dimension(0, TOP_DISTANCE)));
            column.add(createLabel("Blind " + blind.id(), BLIND_SIZE));
            column.add(Box.createRigidArea(new Dimension(0, TOP_DISTANCE)));
            column.add(createLabel("Min. chips: " + blind.minimumChips(), LABEL_SIZE));
            column.add(createLabel("Reward: " + blind.reward() + "$", LABEL_SIZE));
            if (blind.id() == anteInfo.currentBlindId()) {
                column.add(Box.createRigidArea(new Dimension(0, BUTTON_DISTANCE)));
                column.add(playButton);
            }
            columns.add(column);
        }
        add(title, BorderLayout.NORTH);
        add(columns, BorderLayout.CENTER);
        setVisible(true);
    }

    private JLabel createLabel(final String text, final float fontSize) {
        final JLabel label = new JLabel(text);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFont(this.fontFactory.getFont(FONT, fontSize, this));
        label.setForeground(Color.WHITE);
        return label;
    }

}
