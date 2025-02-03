package it.unibo.balatrolt.view.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopInnerLogic;
import it.unibo.balatrolt.view.api.ShopView;

/**
 * Implementation of {@link ShopView}.
 * It also extends a {@link JPanel}, so it can be used to replace an existing one.
 */
public final class ShopViewImpl extends JPanel implements ShopView {
    private final MasterController controller;
    private final JButton buyButton;
    private final ShopInnerLogic logic;
    private final List<JButton> cardButtons = new LinkedList<>();
    private final JPanel innerPanel = new JPanel(new FlowLayout());

    /**
     * Constructor.
     * @param controller controller to attach.
     * @param guiSize current size of the GUI.
     */
    public ShopViewImpl(final MasterController controller, final Dimension guiSize) {
        super(new BorderLayout());
        this.setBackground(Color.LIGHT_GRAY);
        final var shopTitle = new JLabel("Shop");
        shopTitle.setFont(new Font("Bauhaus 93", Font.PLAIN, 100));
        final var titlePanel = new JPanel(new FlowLayout());
        titlePanel.setBackground(this.getBackground());
        titlePanel.add(shopTitle);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(innerPanel, BorderLayout.CENTER);
        this.logic = new ShopInnerLogicImpl();
        // this.guiSize = guiSize;
        this.controller = controller;
        this.buyButton = new JButton("Buy");
        this.buyButton.addActionListener(e -> {
            this.controller.handleEvent(BalatroEvent.BUY_CARD, this.logic.getSelectedCard());
        });
        this.add(this.getBuyOrContinuePanel(), BorderLayout.SOUTH);
    }

    @Override
    public void updateCards(final Set<SpecialCardInfo> toSell) {
        checkNotNull(toSell);
        this.logic.reset();
        getInnerPanel(toSell);
        this.redraw();
    }

    private void getInnerPanel(final Set<SpecialCardInfo> toSell) {
        this.innerPanel.removeAll();
        this.innerPanel.setBackground(this.getBackground());
        final JPanel cardContainer = new JPanel(new GridLayout(1, toSell.size()));
        for (final var card : toSell) {
            cardContainer.add(this.getCardWithPriceLblPanel(card.name(), card.description(), card.price()));
        }
        this.innerPanel.add(cardContainer);
    }

    private JPanel getCardWithPriceLblPanel(final String name, final String desc, final int price) {
        final JPanel ret = new JPanel(new GridBagLayout());
        final JButton card = new JButton();
        card.setContentAreaFilled(false);
        card.addActionListener(e -> {
            this.logic.hitCard(new SpecialCardInfo(name, desc, price));
            this.redraw();
            final var btn = (JButton) e.getSource();
            if (this.logic.isCardSelected()) {
                btn.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        });
        try {
            final Image img = ImageIO.read(getClass().getResource("/img/JOKER.png"));
            card.setIcon(new ImageIcon(img));
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(this, "Image could not be loaded", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        final JButton info = new JButton();
        try {
            final Image img = ImageIO.read(getClass().getResource("/INFO.png"));
            info.setIcon(new ImageIcon(img));
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(this, "Info image could not be loaded", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        info.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, desc, "Card description", JOptionPane.INFORMATION_MESSAGE);
        });
        info.setContentAreaFilled(false);
        info.setBorder(BorderFactory.createLineBorder(this.getBackground()));
        ret.add(getPriceLable(price), getGBConstraints(0, 0, GridBagConstraints.CENTER));
        ret.add(card, getGBConstraints(0, 1, GridBagConstraints.CENTER));
        ret.add(new JLabel(name, JLabel.CENTER), getGBConstraints(0, 2, GridBagConstraints.CENTER));
        ret.add(info, getGBConstraints(0, 3, GridBagConstraints.CENTER));
        ret.setBackground(this.getBackground());
        cardButtons.add(card);
        return ret;
    }

    private JLabel getPriceLable(final int price) {
        final var lbl = new JLabel(Integer.toString(price) + "$");
        lbl.setFont(new Font("Bauhaus 93", Font.PLAIN, 23));
        return lbl;
    }

    private JPanel getBuyOrContinuePanel() {
        final var panel = new JPanel(new FlowLayout());
        final JButton continueGame = new JButton("Continue");
        continueGame.setFont(new Font("Bauhaus 93", Font.PLAIN, 18));
        this.buyButton.setFont(continueGame.getFont());
        this.buyButton.setBackground(Color.RED);
        this.buyButton.setForeground(Color.WHITE);
        this.buyButton.setPreferredSize(continueGame.getPreferredSize());
        continueGame.setBackground(Color.BLUE);
        continueGame.setForeground(Color.WHITE);
        continueGame.addActionListener(e -> {
            this.controller.handleEvent(BalatroEvent.CLOSE_SHOP, Optional.absent());
        });
        panel.add(buyButton);
        panel.add(continueGame);
        panel.setBackground(this.getBackground());
        return panel;
    }

    private GridBagConstraints getGBConstraints(final int x, final int y, final int anchorType) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weighty = 0.2f;
        gbc.weightx = 0.2f;
        gbc.anchor = anchorType;
        return gbc;
    }

    private void redraw() {
        this.buyButton.setEnabled(this.logic.isCardSelected());
        this.cardButtons.forEach(e -> e.setBorder(
            BorderFactory.createLineBorder(e.getParent().getBackground())));
        this.repaint();
    }

    /* private JPanel invisiblePanel() {
        final var p = new JPanel();
        p.setBackground(this.getBackground());
        return p;
    } */
}
