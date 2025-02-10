package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
/**
 * Shows the game over GUI.
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
public final class GameEnd extends JPanel {
    static final long serialVersionUID = 1L;
    private static final String FONT = "JOKERMAN";
    private static final String TITLE_FONT = "SNAP_ITC";
    private static final float BUTTON_SIZE = 65f;
    private static final float TEXT_SIZE = 70f;
    private static final int HEIGHT_GAP = 200;

    private final FontFactory fontFactory = new FontFactory();
    private Image image;

    /**
     * builds the GUI.
     * @param controller master controller.
     * @param title the title.
     */
    GameEnd(final MasterController controller, final String title) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        final JPanel gameOverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        final JLabel gameOver = new JLabel(title);
        gameOver.setFont(this.fontFactory.getFont(TITLE_FONT, TEXT_SIZE, this));
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
        accept.setFont(this.fontFactory.getFont(FONT, BUTTON_SIZE, this));
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
        decline.setFont(this.fontFactory.getFont(FONT, BUTTON_SIZE, this));
        decline.setForeground(Color.WHITE.brighter());
        decline.setContentAreaFilled(false);
        decline.setBorder(null);
        decline.addActionListener(a -> {
            for (final var f: JFrame.getFrames()) {
                f.dispose();
            }
        });
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

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
