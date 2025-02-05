package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
/**
 * Shows the game over GUI
 */
public class GameEnd extends JPanel {
    static final long serialVersionUID = 1L;
    private static final String FONT = "JOKERMAN";
    private static final float BUTTON_SIZE = 65f;
    private static final float TEXT_SIZE = 70f;
    private static final int HEIGHT_GAP = 200;
    private Image image;

    /**
     * builds the GUI.
     * @param controller master controller.
     */
    GameEnd(final MasterController controller, final String title) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        final JPanel gameOverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        final JLabel gameOver = new JLabel(title);
        gameOver.setFont(getFont("SNAP_ITC", TEXT_SIZE));
        gameOver.setForeground(Color.WHITE.brighter());
        gameOverPanel.setOpaque(false);
        gameOverPanel.add(gameOver);
        /**
         * Setting new game button.
         */
        final JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, HEIGHT_GAP, 0));
        add(Box.createGlue());
        add(gameOverPanel);
        add(Box.createGlue());
        add(buttons);
        add(Box.createGlue());
        final JButton accept = new JButton("New Game");
        accept.setFont(getFont(FONT, BUTTON_SIZE));
        accept.setForeground(Color.WHITE.brighter());
        accept.setContentAreaFilled(false);
        accept.setBorder(null);
        accept.addActionListener(a -> controller.handleEvent(BalatroEvent.MAIN_MENU, null));
        accept.setAlignmentY(CENTER_ALIGNMENT);
        buttons.add(accept);
        buttons.setOpaque(false);
        /**
         * Setting quit button
         */
        final JButton decline = new JButton("Quit");
        decline.setFont(getFont(FONT, BUTTON_SIZE));
        decline.setForeground(Color.WHITE.brighter());
        decline.setContentAreaFilled(false);
        decline.setBorder(null);
        decline.addActionListener(a -> System.exit(0));
        decline.setAlignmentY(CENTER_ALIGNMENT);
        buttons.add(decline);
        decline.setPreferredSize(accept.getPreferredSize());
        /**
         * Setting the background.
         */
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/img/MAIN_BACKGROUND.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot load background");
        }
        setVisible(true);
    }

    /*
     * Gives back the requested font with the given size.
     */
    private Font getFont(final String nameFont, final float fontSize) {
        Font font = new Font("Arial", Font.PLAIN, (int) fontSize);
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/font/" + nameFont + ".TTF")
            );
            font = font.deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot load font");
        }
        return font;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
