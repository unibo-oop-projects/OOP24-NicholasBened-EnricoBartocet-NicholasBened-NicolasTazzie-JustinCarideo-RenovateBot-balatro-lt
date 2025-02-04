package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
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

public class MainMenu extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String TITLE_FONT = "SNAP_ITC";
    private static final String BUTTON_FONT = "JOKERMAN";
    private static final float TITLE_SIZE = 100f;
    private static final float BUTTON_SIZE = 65f;
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    public MainMenu(final MasterController controller, final String testo) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        /**
         * Setting the title.
         */
        final JLabel title = new JLabel("BALATRO");
        title.setFont(getFont(TITLE_FONT, TITLE_SIZE));
        title.setForeground(Color.WHITE.brighter());
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(title);
        /**
         * Setting the start button.
         */
        final JButton start = new JButton(testo);
        start.setFont(getFont(BUTTON_FONT, BUTTON_SIZE));
        start.setForeground(Color.WHITE.brighter());
        start.setContentAreaFilled(false);
        start.setBorder(null);
        start.setAlignmentX(CENTER_ALIGNMENT);
        start.addActionListener(a -> controller.handleEvent(BalatroEvent.INIT_GAME, null));
        add(Box.createVerticalGlue());
        add(start);
        add(Box.createVerticalGlue());
        /**
         * Setting the background.
         */
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/img/MAIN_BACKGROUND.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot load Background");
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
