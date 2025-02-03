package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.google.common.base.Optional;
import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
/**
 * GUI used to select the deck to play with.
 */
public class DeckSelector extends JPanel {
    private static final String TITLE_FONT = "SNAP_ITC";
    private static final String DESC_DECK_FONT = "COPPER_BLACK";
    private static final float TITLE_SIZE = 100f;
    private static final float DECK_SIZE = 30f;
    private static final float DESCR_SIZE = 25f;
    private final Map<String, DeckInfo> decksTranslator = new HashMap<>();
    private String deckName;
    private JLabel labelName;
    private JTextArea deckDescription;
    private JButton deck;
    private Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

    /**
     * Sets the GUI and waits for the user to choose a deck.
     * @param controller the master controller.
     * @param decks to choose from.
     */
    public DeckSelector(MasterController controller, List<DeckInfo> decks) {
        setLayout(new GridBagLayout());
        decks.forEach(d -> {
            this.decksTranslator.put(d.name(), d);
        });
        this.deckName = decks.get(0).name();
        /**
         * Setting the title.
         */
        final JLabel title = new JLabel("BALATRO");
        title.setFont(getFont(TITLE_FONT, TITLE_SIZE));
        title.setForeground(Color.WHITE.brighter());
        this.add(title, getConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 0, 0,
            0, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 50, 0, 20, 0));
        /**
         * Setting the deck selector.
         */
        final JPanel centralMenu = new JPanel(new GridBagLayout());
        centralMenu.setBackground(Color.DARK_GRAY);
        centralMenu.setPreferredSize(new Dimension(
            (int) (this.getPreferredSize().width * 1.2),
            (int) (this.getPreferredSize().height * 1.5)
        ));
        this.add(centralMenu, getConstraints(0, 0, 0.2, 1.0, 0,
            GridBagConstraints.PAGE_END, GridBagConstraints.NONE, 0, 0, 50, 0));
        /**
         * Adding component to the central menu.
         * Setting left button.
         */
        final JButton lefButton = new JButton("<");
        lefButton.setFont(getFont(DESC_DECK_FONT, DECK_SIZE));
        lefButton.setBackground(Color.RED);
        lefButton.addActionListener(e -> {
            try {
                this.deckName = decks.get(decks.indexOf(this.decksTranslator.get(this.deckName)) - 1).name();
            } catch (IndexOutOfBoundsException a) {
                JOptionPane.showMessageDialog(this, "This is the first card");
            }
            this.updateDeck();
        });
        centralMenu.add(lefButton, getConstraints(0, 0, 0, 0.2, 0,
            GridBagConstraints.LINE_START, GridBagConstraints.VERTICAL, 20, 5, 20, 5));
        /**
         * Setting right button.
         */
        final JButton rightButton = new JButton(">");
        rightButton.setFont(getFont(DESC_DECK_FONT, DECK_SIZE));
        rightButton.setBackground(Color.RED);
        rightButton.addActionListener(e -> {
            try {
                this.deckName = decks.get(decks.indexOf(this.decksTranslator.get(this.deckName)) + 1).name();
            } catch (IndexOutOfBoundsException a) {
                JOptionPane.showMessageDialog(this, "This is the last card");
            }
            this.updateDeck();
        });
        centralMenu.add(rightButton, getConstraints(2, 0, 0, 0.2, 0,
            GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 20, 5, 20, 5));
        /**
         * Setting panel in which the decks will be displayed.
         */
        final JPanel deckPanel = new JPanel(new GridBagLayout());
        deckPanel.setBackground(Color.BLACK);
        centralMenu.add(deckPanel, getConstraints(1, 0, 1.0, 0.2, 0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 30, 0, 30, 0));
        /**
         * Setting the description panel.
         */
        final JPanel descriptionPanel = new JPanel(new GridBagLayout());
        descriptionPanel.setBackground(Color.DARK_GRAY);
        deckPanel.add(descriptionPanel, getConstraints(1, 0, 1.0, 0.2, 0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 10, 10, 10, 10));
        /**
         * setting deck name.
         */
        this.labelName = new JLabel();
        this.labelName.setFont(getFont(DESC_DECK_FONT, DECK_SIZE));
        this.labelName.setForeground(Color.WHITE);
        descriptionPanel.add(this.labelName, getConstraints(0, 0, 0.2, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 15, 0, 0, 0));
        /**
         * setting deck description.
         */
        this.deckDescription = new JTextArea();
        this.deckDescription.setLineWrap(true);
        this.deckDescription.setWrapStyleWord(true);
        this.deckDescription.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.deckDescription.setFont(getFont(DESC_DECK_FONT, DESCR_SIZE));;
        this.deckDescription.setBackground(Color.WHITE);
        this.deckDescription.setOpaque(true);
        this.deckDescription.setForeground(Color.BLACK);
        descriptionPanel.add(deckDescription, getConstraints(0, 1, 1.0, 1.0, 50,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 10, 5, 5, 5));
        /**
         * setting deck image
         */
        this.deck = new JButton();
        this.deck.setContentAreaFilled(false);
        this.deck.setBorderPainted(false);
        this.deck.addActionListener(e -> {
            controller.handleEvent(BalatroEvent.CHOOSE_DECK, Optional.of(this.decksTranslator.get(this.deckName)));
        });
        deckPanel.add(deck, getConstraints(0, 0, 0, 0.2, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.VERTICAL, 10, 10, 10, 10));
        this.updateDeck();
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

    /**
     * Create a GribBagConstraint with the given data.
     */
    private GridBagConstraints getConstraints(int x, int y, double weightx, double weighty, int ipadx, int anchor, int fill, int top, int left, int bottom, int right) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.ipadx = ipadx;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = new Insets(top, left, bottom, right);
        return gbc;
    }

    /**
     * Updates the image of the deck.
     */
    private void updateDeck() {
        try {
            final Image img = ImageIO.read(getClass().getResource("/img/decks/" + this.deckName.toUpperCase() + "_DECK.png"));
            this.deck.setIcon(new ImageIcon(img));
        } catch (IOException | IndexOutOfBoundsException a) {
            JOptionPane.showMessageDialog(this, "This is the last deck!");
        }
        this.labelName.setText(deckName);
        this.deckDescription.setText(decksTranslator.get(deckName).desc());
        this.revalidate();
        this.repaint();
    }

    /**
     * Gives back the requested font with the given size.
     */
    private Font getFont(String nameFont, float fontSize) {
        Font font = new Font("Arial", Font.PLAIN, 15);
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/font/" + nameFont + ".TTF")
            );
            font = font.deriveFont(fontSize);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot load font");
        }
        return font;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
