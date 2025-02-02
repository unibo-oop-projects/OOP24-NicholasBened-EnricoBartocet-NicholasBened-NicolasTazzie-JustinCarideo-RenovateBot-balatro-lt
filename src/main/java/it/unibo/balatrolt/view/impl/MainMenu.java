package it.unibo.balatrolt.view.impl;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;

public class MainMenu extends JPanel {
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        public MainMenu(MasterController controller, String testo) {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            final var label = new JLabel("BALATRO");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createVerticalGlue());
            this.add(label);
            final var button = new JButton(testo);
            button.addActionListener(a -> controller.handleEvent(BalatroEvent.INIT_GAME, null));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createVerticalGlue());
            this.add(button);
            this.add(Box.createVerticalGlue());
            try {
                this.image = ImageIO.read(getClass().getResourceAsStream("/SFONDO_MAIN.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}
