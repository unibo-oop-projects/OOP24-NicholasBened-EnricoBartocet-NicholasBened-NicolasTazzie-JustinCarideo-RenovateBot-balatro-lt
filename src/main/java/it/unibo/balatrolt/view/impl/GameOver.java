package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class GameOver extends JPanel {
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    GameOver(MasterController controller) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        final JPanel gameOverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        final JLabel gameOver = new JLabel("Game Over");
        gameOver.setFont(new Font("Snap ITC", Font.BOLD, 70));
        gameOver.setForeground(Color.WHITE.brighter());
        gameOverPanel.setOpaque(false);
        gameOverPanel.add(gameOver);
        /**
         * Setting accept button.
         */
        final JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 0));
        this.add(Box.createGlue());
        this.add(gameOverPanel);
        this.add(Box.createGlue());
        this.add(buttons);
        this.add(Box.createGlue());
        final JButton accept = new JButton("New Game");
        accept.setFont(new Font("Jokerman", Font.BOLD, 45));
        accept.setForeground(Color.WHITE.brighter());
        accept.setContentAreaFilled(false);
        accept.setBorder(null);
        accept.addActionListener(a -> controller.handleEvent(BalatroEvent.MAIN_MENU, null));
        accept.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttons.add(accept);
        buttons.setOpaque(false);;
        /**
         * Setting decline button.
         */
        final JButton decline = new JButton("Quit");
        decline.setFont(new Font("Jokerman", Font.BOLD, 45));
        decline.setForeground(Color.WHITE.brighter());
        decline.setContentAreaFilled(false);
        decline.setBorder(null);
        decline.addActionListener(a -> System.exit(0));
        decline.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttons.add(decline);
        decline.setPreferredSize(accept.getPreferredSize());
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
