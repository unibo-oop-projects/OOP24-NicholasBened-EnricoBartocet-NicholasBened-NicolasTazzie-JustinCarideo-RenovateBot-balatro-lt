package it.unibo.balatrolt.view.impl;

import java.awt.Color;
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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
/**
 * Main menu GUI.
 * @author Benedetti Nicholas
 */
@SuppressFBWarnings(
    justification = """
        Since we extend JPanel (which is Serializable), it's required to make the class Serializable,
        otherwhise an exception will be thrown when serializing this class.
        Anyway we are sure that we will never serialize this class, because if we want to save the game
        we will only save the informations stored in the model, creating a new View when needed.
        """,
    value = "SE_BAD_FIELD"
)
public final class MainMenu extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String TITLE_FONT = "SNAP_ITC";
    private static final String BUTTON_FONT = "JOKERMAN";
    private static final float TITLE_SIZE = 100f;
    private static final float BUTTON_SIZE = 65f;

    private final FontFactory fontFactory = new FontFactory();
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    /**
     * Builds the main menu.
     * @param controller master controller.
     * @param text title text.
     */
    public MainMenu(final MasterController controller, final String text) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        /**
         * Setting the title.
         */
        final JLabel title = new JLabel("BALATRO");
        title.setFont(this.fontFactory.getFont(TITLE_FONT, TITLE_SIZE, this));
        title.setForeground(Color.WHITE.brighter());
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(title);
        /**
         * Setting the start button.
         */
        final JButton start = new JButton(text);
        start.setFont(this.fontFactory.getFont(BUTTON_FONT, BUTTON_SIZE, this));
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

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
