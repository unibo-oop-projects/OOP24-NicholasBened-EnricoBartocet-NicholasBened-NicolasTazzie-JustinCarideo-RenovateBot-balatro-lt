package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;

public class GameOver extends JPanel {
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    GameOver(MasterController controller) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        /**
         * Setting accept button.
         */
        final JButton accept = new JButton("YES");
        accept.setFont(new Font("Jokerman", Font.BOLD, 45));
        accept.setForeground(Color.WHITE.brighter());
        accept.setContentAreaFilled(false);
        accept.setBorder(null);
        accept.addActionListener(a -> controller.handleEvent(BalatroEvent.MAIN_MENU, null));
        accept.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(Box.createHorizontalGlue());
        this.add(accept);
        this.add(Box.createHorizontalGlue());
        /**
         * Setting decline button.
         */
        final JButton decline = new JButton("NO");
        decline.setFont(new Font("Jokerman", Font.BOLD, 45));
        decline.setForeground(Color.WHITE.brighter());
        decline.setContentAreaFilled(false);
        decline.setBorder(null);
        decline.addActionListener(a -> System.exit(0));
        decline.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(Box.createHorizontalGlue());
        this.add(decline);
        this.add(Box.createHorizontalGlue());
        /**
         * Setting the background.
         */
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/img/MAIN_BACKGROUND.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, -250, -130, this);
    }
}
