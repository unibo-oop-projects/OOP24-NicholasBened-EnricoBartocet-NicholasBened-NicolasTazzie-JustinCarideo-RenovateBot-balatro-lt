package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

public final class ShopViewImpl extends JPanel implements ShopView {
    private final MasterController controller;
    private final JButton buyButton;
    private final ShopInnerLogic logic;
    private final List<JButton> cardButtons = new LinkedList<>();

    public ShopViewImpl(final MasterController controller, final Dimension guiSize) {
        super(new GridBagLayout());
        this.logic = new ShopInnerLogicImpl();
        // this.guiSize = guiSize;
        this.controller = controller;
        this.setBackground(Color.ORANGE);
        this.buyButton = new JButton("Buy");
        this.buyButton.addActionListener(e -> {
            this.controller.handleEvent(BalatroEvent.BUY_CARD, this.logic.getSelectedCard());
        });
        this.updateCards(Set.of(
                new SpecialCardInfo("card1", "Card price 10", 10),
                new SpecialCardInfo("card2", "Card price 11", 11),
                new SpecialCardInfo("card3", "Card price 12", 12),
                new SpecialCardInfo("card4", "Card price 13", 13),
                new SpecialCardInfo("card5", "Card price 14", 14)));
    }

    private JPanel getCardWithLabel(final String name, final String desc, final int price) {
        final JPanel ret = new JPanel(new GridBagLayout());
        final JButton card = new JButton(name);
        card.addActionListener(e -> {
            this.logic.hitCard(new SpecialCardInfo(name, desc, price));
            this.redraw();
            var btn = (JButton) e.getSource();
            if (this.logic.isCardSelected()) {
                btn.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        });
        final JButton info = new JButton("info");
        info.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, desc, "Card description", JOptionPane.INFORMATION_MESSAGE);
        });
        ret.add(new JLabel(Integer.toString(price)), getInnerConstraint(0));
        ret.add(card, getInnerConstraint(1));
        ret.add(info, getInnerConstraint(2));
        cardButtons.add(card);
        return ret;
    }

    private JPanel getBuyOrContinuePanel() {
        final var panel = new JPanel(new GridLayout(2, 0));
        final JButton continueGame = new JButton("Continue");
        continueGame.addActionListener(e -> {
            this.controller.handleEvent(BalatroEvent.CLOSE_SHOP, Optional.absent());
        });
        panel.add(buyButton);
        panel.add(continueGame);
        return panel;
    }

    private GridBagConstraints getInnerConstraint(final int pos) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = pos;
        return gbc;
    }

    private GridBagConstraints getOuterConstraints(final int x, final int y) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weighty = 0.2f;
        gbc.weightx = 0.2f;
        gbc.anchor = GridBagConstraints.PAGE_END;
        return gbc;
    }

    private void redraw() {
        this.buyButton.setVisible(this.logic.isCardSelected());
        this.cardButtons.forEach(e -> e.setBorder(
            BorderFactory.createLineBorder(e.getParent().getBackground())));
    }

    @Override
    public void updateCards(final Set<SpecialCardInfo> toSell) {
        this.logic.setCardsToSell(toSell);
        final var shopTitle = new JLabel("Shop");
        shopTitle.setFont(new Font("Bauhaus 93", Font.PLAIN, 100));
        final JPanel p = new JPanel(new GridLayout(1, toSell.size()));
        for (final var card : toSell) {
            p.add(getCardWithLabel(card.name(), card.description(), card.price()));
        }
        this.add(shopTitle, this.getOuterConstraints(1, 0));
        this.add(p, this.getOuterConstraints(1, 1));
        this.add(new JPanel(), this.getOuterConstraints(2, 2));
        this.add(this.getBuyOrContinuePanel(), this.getOuterConstraints(2, 1));
        this.redraw();
    }
}
