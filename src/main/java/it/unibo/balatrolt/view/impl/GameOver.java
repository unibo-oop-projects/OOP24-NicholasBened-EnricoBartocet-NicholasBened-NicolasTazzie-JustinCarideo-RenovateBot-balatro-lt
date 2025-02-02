package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameOver extends JPanel {
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    GameOver() {
        setLayout(new BorderLayout());
        final JButton accept = new JButton("YES");
        accept.setBackground(Color.WHITE);
        final JButton decline = new JButton("NO");
        decline.setBackground(Color.WHITE);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/SFONDO_MAIN.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, -250, -130, this);
    }
}
