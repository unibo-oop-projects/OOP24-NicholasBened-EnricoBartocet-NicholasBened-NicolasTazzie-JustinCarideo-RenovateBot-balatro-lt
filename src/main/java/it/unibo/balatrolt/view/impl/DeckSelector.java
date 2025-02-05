package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public final class DeckSelector extends JPanel {
    static final long serialVersionUID = 1L;
    private static final String TITLE_FONT = "SNAP_ITC";
    private static final String DESC_DECK_FONT = "COPPER_BLACK";
    private static final float TITLE_SIZE = 100f;
    private static final float DECK_SIZE = 35f;
    private static final float DESCR_SIZE = 25f;
    private static final Double BASE_WEIGHT = 0.2;
    private static final int BASE_PAD = 10;
    private static final int TOP_PAD = 50;
    private static final int DECK_PAD = 30;
    private static final double HEIGHT_MULT = 1.5;
    private static final double WIDTH_MULT = 1.2;

    private final Map<String, DeckInfo> decksTranslator;
    private final FontFactory fontFactory = new FontFactory();
    private final JLabel labelName;
    private final JTextArea deckDescription;
    private final JButton deck;
    private String deckName;
    private Image image;

    /**
     * Sets the GUI and waits for the user to choose a deck.
     * @param controller the master controller.
     * @param decks to choose from.
     */
    public DeckSelector(final MasterController controller, final List<DeckInfo> decks) {
        setLayout(new GridBagLayout());
        this.decksTranslator = new HashMap<>();
        decks.forEach(d -> {
            this.decksTranslator.put(d.name(), d);
        });
        this.deckName = decks.get(0).name();
        /**
         * Setting the title.
         */
        final JLabel title = new JLabel("BALATRO");
        title.setFont(this.fontFactory.getFont(TITLE_FONT, TITLE_SIZE, this));
        title.setForeground(Color.WHITE.brighter());
        add(title, getConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 0, 0,
            0, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, TOP_PAD, 0, TOP_PAD / 2, 0));
        /**
         * Setting the deck selector.
         */
        final JPanel centralMenu = new JPanel(new GridBagLayout());
        centralMenu.setBackground(Color.DARK_GRAY);
        centralMenu.setPreferredSize(new Dimension(
            (int) (getPreferredSize().width * WIDTH_MULT),
            (int) (getPreferredSize().height * HEIGHT_MULT)
        ));
        add(centralMenu, getConstraints(0, 0, BASE_WEIGHT, 1.0, 0,
            GridBagConstraints.PAGE_END, GridBagConstraints.NONE, 0, 0, TOP_PAD, 0));
        /**
         * Adding component to the central menu.
         * Setting left button.
         */
        final JButton lefButton = new JButton("<");
        lefButton.setFont(this.fontFactory.getFont(DESC_DECK_FONT, DECK_SIZE, this));
        lefButton.setBackground(Color.RED);
        lefButton.addActionListener(e -> {
            try {
                this.deckName = decks.get(decks.indexOf(this.decksTranslator.get(this.deckName)) - 1).name();
            } catch (IndexOutOfBoundsException a) {
                JOptionPane.showMessageDialog(this, "This is the first card");
            }
            this.updateDeck();
        });
        centralMenu.add(lefButton, getConstraints(0, 0, 0, BASE_WEIGHT, 0,
            GridBagConstraints.LINE_START, GridBagConstraints.VERTICAL, BASE_PAD * 2, BASE_PAD / 2, BASE_PAD * 2, BASE_PAD / 2));
        /**
         * Setting right button.
         */
        final JButton rightButton = new JButton(">");
        rightButton.setFont(this.fontFactory.getFont(DESC_DECK_FONT, DECK_SIZE, this));
        rightButton.setBackground(Color.RED);
        rightButton.addActionListener(e -> {
            try {
                this.deckName = decks.get(decks.indexOf(this.decksTranslator.get(this.deckName)) + 1).name();
            } catch (IndexOutOfBoundsException a) {
                JOptionPane.showMessageDialog(this, "This is the last card");
            }
            this.updateDeck();
        });
        centralMenu.add(rightButton, getConstraints(2, 0, 0, BASE_WEIGHT, 0,
            GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, BASE_PAD * 2, BASE_PAD / 2, BASE_PAD * 2, BASE_PAD / 2));
        /**
         * Setting panel in which the decks will be displayed.
         */
        final JPanel deckPanel = new JPanel(new GridBagLayout());
        deckPanel.setBackground(Color.BLACK);
        centralMenu.add(deckPanel, getConstraints(1, 0, 1.0, BASE_WEIGHT, 0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, DECK_PAD, 0, DECK_PAD, 0));
        /**
         * Setting the description panel.
         */
        final JPanel descriptionPanel = new JPanel(new GridBagLayout());
        descriptionPanel.setBackground(Color.DARK_GRAY);
        deckPanel.add(descriptionPanel, getConstraints(1, 0, 1.0, BASE_WEIGHT, 0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, BASE_PAD, BASE_PAD, BASE_PAD, BASE_PAD));
        /**
         * setting deck name.
         */
        this.labelName = new JLabel();
        this.labelName.setFont(this.fontFactory.getFont(DESC_DECK_FONT, DECK_SIZE, this));
        this.labelName.setForeground(Color.WHITE);
        descriptionPanel.add(this.labelName, getConstraints(0, 0, BASE_WEIGHT, 0, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.NONE, BASE_PAD, 0, 0, 0));
        /**
         * setting deck description.
         */
        this.deckDescription = new JTextArea();
        this.deckDescription.setEditable(false);
        this.deckDescription.setLineWrap(true);
        this.deckDescription.setWrapStyleWord(true);
        this.deckDescription.setAlignmentY(CENTER_ALIGNMENT);
        this.deckDescription.setFont(this.fontFactory.getFont(DESC_DECK_FONT, DESCR_SIZE, this));
        this.deckDescription.setBackground(Color.WHITE);
        this.deckDescription.setOpaque(true);
        this.deckDescription.setForeground(Color.BLACK);
        descriptionPanel.add(deckDescription, getConstraints(0, 1, 1.0, 1.0, 50,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, BASE_PAD, BASE_PAD / 2, BASE_PAD / 2, BASE_PAD / 2));
        /**
         * setting deck image
         */
        this.deck = new JButton();
        this.deck.setContentAreaFilled(false);
        this.deck.setBorderPainted(false);
        this.deck.addActionListener(e -> {
            controller.handleEvent(BalatroEvent.CHOOSE_DECK, Optional.of(this.decksTranslator.get(this.deckName)));
        });
        deckPanel.add(deck, getConstraints(0, 0, 0, BASE_WEIGHT, 0,
            GridBagConstraints.PAGE_START, GridBagConstraints.VERTICAL, BASE_PAD, BASE_PAD, BASE_PAD, BASE_PAD));
        this.updateDeck();
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
     * Create a GribBagConstraint with the given data.
     */
    private GridBagConstraints getConstraints(final int x, final int y, final double weightx, final double weighty,
        final int ipadx, final int anchor, final int fill, final int top, final int left, final int bottom, final int right) {

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
            final Image img = ImageIO.read(getClass()
                .getResource("/img/decks/" + this.deckName.toUpperCase(Locale.getDefault()) + "_DECK.png"));
            this.deck.setIcon(new ImageIcon(img));
        } catch (IOException | IndexOutOfBoundsException a) {
            JOptionPane.showMessageDialog(this, "This is the last deck!");
        }
        this.labelName.setText(deckName);
        this.deckDescription.setText(decksTranslator.get(deckName).desc());
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
