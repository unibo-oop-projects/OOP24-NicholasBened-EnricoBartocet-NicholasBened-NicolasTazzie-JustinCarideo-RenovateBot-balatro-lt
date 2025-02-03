package it.unibo.balatrolt.view.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
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
    private static final int INFO_Y = 3;
    private static final int NAME_Y = 2;
    private static final int CARD_Y = 1;
    private static final int N_SLOT_ROWS = 1;
    private static final float GBC_WEIGHT = 0.2f;
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
        this.cardButtons.clear();
        this.logic.reset();
        buildInnerPanel(toSell);
        this.redraw();
    }

    private void buildInnerPanel(final Set<SpecialCardInfo> toSell) {
        this.innerPanel.removeAll();
        this.innerPanel.setBackground(this.getBackground());
        final JPanel cardContainer = new JPanel(new GridLayout(N_SLOT_ROWS, toSell.size()));
        for (final var card : toSell) {
            cardContainer.add(this.getCardWithPriceLblPanel(card.name(), card.description(), card.price()));
        }
        this.innerPanel.add(cardContainer);
    }

    private JPanel getCardWithPriceLblPanel(final String name, final String desc, final int price) {
        final JPanel panel = new JPanel(new GridBagLayout());
        final JButton card = getIconButton("/img/JOKER.png", e -> {
            this.logic.hitCard(new SpecialCardInfo(name, desc, price));
            this.redraw();
            final var btn = (JButton) e.getSource();
            if (this.logic.isCardSelected()) {
                btn.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        });
        this.cardButtons.add(card);
        final JButton info = getIconButton("/INFO.png", e -> {
            JOptionPane.showMessageDialog(this, desc, "Card description", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(getPriceLable(price), getGBConstraints(0, 0));
        panel.add(card, getGBConstraints(0, CARD_Y));
        panel.add(new JLabel(name, JLabel.CENTER), getGBConstraints(0, NAME_Y));
        panel.add(info, getGBConstraints(0, INFO_Y));
        panel.setBackground(this.getBackground());
        return panel;
    }

    private JButton getIconButton(final String path, final ActionListener action) {
        final var btn = new JButton();
        btn.addActionListener(action);
        try {
            final Image img = ImageIO.read(getClass().getResource(path));
            btn.setIcon(new ImageIcon(img));
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(this, "Image could not be loaded: " + path, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        btn.setContentAreaFilled(false);
        return btn;
    };

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

    private GridBagConstraints getGBConstraints(final int x, final int y) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weighty = GBC_WEIGHT;
        gbc.weightx = GBC_WEIGHT;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private void redraw() {
        this.buyButton.setEnabled(this.logic.isCardSelected());
        this.cardButtons.forEach(e -> e.setBorder(
            BorderFactory.createLineBorder(e.getParent().getBackground())));
        this.repaint();
    }
}
