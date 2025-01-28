package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Creates the part of the GUI containing the
 * slots for the cards (any type).
 */
public final class SlotGUI {

    /**
     * @return the completed slot GUI.
     *
     * @throws IOException
     */
    JPanel build() throws IOException {
        final JPanel panel = new JPanel(new GridLayout());
        panel.setBackground(Color.green.darker().darker().darker());

        //TODO: create slots for the cards

        final JButton card = new JButton();
        try {
            final Image img = ImageIO.read(getClass().getResource("/TWOHEARTS.png"));
            card.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        panel.add(card);
        return panel;
    }
}
