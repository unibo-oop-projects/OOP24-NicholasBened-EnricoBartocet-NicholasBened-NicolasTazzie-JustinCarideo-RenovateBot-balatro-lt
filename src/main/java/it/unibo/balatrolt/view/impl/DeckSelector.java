package it.unibo.balatrolt.view.impl;

import java.awt.Color;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
/**
 * GUI used to select the deck to play with.
 */
public class DeckSelector extends JPanel {
    private static final int RIDIM = 2;
    private String deckName;
    private final Iterator<String> nameIterator;
    private final Map<String, DeckInfo> decksTranslator = new HashMap<>();
    private JLabel labelName;
    private JLabel deckDescription;
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
        this.nameIterator = decksTranslator.keySet().iterator();
        this.deckName = this.nameIterator.next();
        /**
         * Setting the title.
         */
        final JLabel title = new JLabel("BALATRO");
        title.setFont(new Font("Snap ITC", Font.BOLD, 70));
        title.setForeground(Color.WHITE.brighter());
        this.add(title, getConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 0, 0,
            0, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 50, 0, 20, 0));
        /**
         * Setting the deck selector.
         */
        final JPanel centralMenu = new JPanel(new GridBagLayout());
        centralMenu.setBackground(Color.DARK_GRAY);
        SwingUtilities.invokeLater(() -> {
            centralMenu.setPreferredSize(new Dimension(
                (int) (this.getBounds().width / RIDIM),
                (int) (this.getBounds().height / RIDIM)
            ));
            repaint();
            revalidate();
        });
        this.add(centralMenu, getConstraints(0, 0, 1.0, 1.0, 0, 1,
            GridBagConstraints.PAGE_END, GridBagConstraints.NONE, 0, 10, 50, 10));
        /**
         * Adding component to the central menu.
         */
        //left Button.
        final JButton lefButton = new JButton("<");
        lefButton.setBackground(Color.RED);
        lefButton.addActionListener(e -> {
            
        });
        centralMenu.add(lefButton, getConstraints(0, 0, 0.2, 0.2, 0, 1,
            GridBagConstraints.LINE_START, GridBagConstraints.VERTICAL, 20, 5, 20, 5));
        //right button
        final JButton rightButton = new JButton(">");
        rightButton.setBackground(Color.RED);
        rightButton.addActionListener(e -> {
            this.deckName = nameIterator.next();
            this.labelName.setText("<html>" + deckName + "</html>");
            this.deckDescription.setText("<html>" + decksTranslator.get(deckName).desc() + "</html>");
            revalidate();
            repaint();
        });
        centralMenu.add(rightButton, getConstraints(2, 0, 0.2, 0.2, 0, 1,
            GridBagConstraints.LINE_END, GridBagConstraints.VERTICAL, 20, 5, 20, 5));
        /**
         * Panel in which the decks will be displayed.
         */
        final JPanel deckPanel = new JPanel(new GridBagLayout());
        deckPanel.setBackground(Color.BLACK);
        centralMenu.add(deckPanel, getConstraints(1, 0, 1.0, 0.2, 50, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 30, 0, 30, 0));
        //setting deck image
        final JButton deck = new JButton();
        deck.setContentAreaFilled(false);
        deck.setBorderPainted(false);
        deck.addActionListener(e -> {
            controller.handleEvent(BalatroEvent.CHOOSE_DECK, Optional.of(this.decksTranslator.get(deckName)));
        });
        deckPanel.add(deck, getConstraints(0, 0, 0.2, 0.2, 0, 1,
            GridBagConstraints.PAGE_START, GridBagConstraints.VERTICAL, 10, 10, 10, 10));
        try {
            final Image img = ImageIO.read(getClass().getResource("/" + "img/decks/WHITE_DECK" + ".png"));
            deck.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        //Setting the description panel.
        final JPanel descriptionPanel = new JPanel(new GridBagLayout());
        descriptionPanel.setBackground(Color.DARK_GRAY);
        deckPanel.add(descriptionPanel, getConstraints(1, 0, 0.7, 0.2, 0, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 10, 10, 10, 10));
        //setting deck name.
        this.labelName = new JLabel("<html>" + deckName + "</html>");
        this.labelName.setForeground(Color.WHITE);
        this.labelName.setFont(new Font("Cooper Black", Font.PLAIN, 20));
        descriptionPanel.add(this.labelName, getConstraints(0, 0, 0.2, 0, 0, 1,
            GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 15, 0, 0, 0));
        //setting deck description.
        this.deckDescription = new JLabel("<html>" + decksTranslator.get(deckName).desc() + "</html>");
        deckDescription.setFont(new Font("Cooper Black", Font.PLAIN, 17));
        deckDescription.setBackground(Color.WHITE);
        deckDescription.setOpaque(true);
        deckDescription.setForeground(Color.BLACK);
        descriptionPanel.add(deckDescription, getConstraints(0, 1, 1.0, 1.0, 50, 1,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, 10, 5, 5, 5));
        /*
        JComboBox<String> list = new JComboBox<>();
        var centerPanel = new JPanel(new FlowLayout());
        add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(new JLabel("Choose your deck:"));
        centerPanel.add(list);
        decks.forEach(d -> {
            list.addItem(d.name());
            decksTranslator.put(d.name(), d);
        });
        list.setSelectedIndex(-1);
        var button = new JButton("Select");
        button.addActionListener(e -> {
            try {
                controller.handleEvent(BalatroEvent.CHOOSE_DECK, Optional.of(decksTranslator.get((list.getSelectedItem()))));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "You have to chose a deck", "DECK", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(button, BorderLayout.SOUTH);
        */
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

    private GridBagConstraints getConstraints(int x, int y, double weightx, double weighty, int ipadx, int height, int anchor, int fill, int top, int left, int bottom, int right) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.ipadx = ipadx;
        gbc.gridheight = height;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = new Insets(top, left, bottom, right);
        return gbc;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, -250, -130, this);
    }
}
