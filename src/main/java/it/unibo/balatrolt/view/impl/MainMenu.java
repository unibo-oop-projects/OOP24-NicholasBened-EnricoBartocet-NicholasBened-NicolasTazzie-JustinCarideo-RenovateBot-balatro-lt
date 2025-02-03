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
import javax.swing.JLabel;
import javax.swing.JPanel;
import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;

public class MainMenu extends JPanel {
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    public MainMenu(MasterController controller, String testo) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        /**
         * Setting the title.
         */
        final JLabel title = new JLabel("BALATRO");
        title.setFont(new Font("Snap ITC", Font.BOLD, 70));
        title.setForeground(Color.WHITE.brighter());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalGlue());
        this.add(title);
        /**
         * Setting the start button.
         */
        final JButton start = new JButton(testo);
        start.setFont(new Font("Jokerman", Font.BOLD, 40));
        start.setForeground(Color.WHITE.brighter());
        start.setContentAreaFilled(false);
        start.setBorder(null);
        start.addActionListener(a -> controller.handleEvent(BalatroEvent.INIT_GAME, null));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalGlue());
        this.add(start);
        this.add(Box.createVerticalGlue());
        /**
         * Setting the background.
         */
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
        g.drawImage(image, -250, -130, this);
    }
}
