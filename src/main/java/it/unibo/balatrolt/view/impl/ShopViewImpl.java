package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopView;

public final class ShopViewImpl extends JPanel implements ShopView {
    final Dimension guiSize;
    private final class DescPanel extends JPanel {
        private final JLabel desc;

        public DescPanel() {
            super(new FlowLayout());
            this.desc = new JLabel();
            this.setMaximumSize(new Dimension(guiSize.width / 100, guiSize.height));
            this.add(desc);
        }

        public void updateDescription(String desc) {
            this.desc.setText(desc);
        }
    }

    private final DescPanel descriptionPanel;

    public ShopViewImpl(final Dimension guiSize) {
        super(new GridBagLayout());
        this.guiSize = guiSize;
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        this.setBackground(Color.ORANGE);
        this.descriptionPanel = new DescPanel();
        this.showShop(Set.of(
                new SpecialCardInfo("card1", "Card price 10", 10),
                new SpecialCardInfo("card2", "Card price 11", 11),
                new SpecialCardInfo("card3", "Card price 12", 12),
                new SpecialCardInfo("card4", "Card price 13", 13),
                new SpecialCardInfo("card5", "Card price 14", 14)));
    }

    private JPanel getCardWithLabel(final String name, final String desc, final int price) {
        final JPanel ret = new JPanel(new GridBagLayout());
        ret.setBorder(BorderFactory.createLineBorder(Color.RED));
        final JButton card = new JButton(name);
        card.addMouseListener(getCardMouseListener(name, desc));
        ret.add(new JLabel(Integer.toString(price)), getInnerConstraint(0));
        ret.add(card, getInnerConstraint(1));
        return ret;
    }

    private MouseListener getCardMouseListener(final String name, final String desc) {
        return new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                System.out.println(name);
            }

            @Override
            public void mousePressed(final MouseEvent e) {
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
                showDescription(desc);
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                hideDescription();
            }
        };
    }

    private void hideDescription() {
        this.descriptionPanel.updateDescription("");
        this.descriptionPanel.setBackground(this.getBackground());
    }

    private void showDescription(final String desc) {
        this.descriptionPanel.updateDescription(desc);
        this.descriptionPanel.setBackground(Color.MAGENTA);
    }

    private GridBagConstraints getInnerConstraint(final int pos) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = pos;
        return gbc;
    }

    @Override
    public void showShop(final Set<SpecialCardInfo> toSell) {
        final JPanel p = new JPanel(new GridLayout(1, toSell.size()));
        for (final var card : toSell) {
            p.add(getCardWithLabel(card.name(), card.description(), card.price()));
        }
        this.add(p, this.getOuterConstraints(1,1));
        this.add(this.descriptionPanel, this.getDescPanelConstraint());
        this.add(new JPanel(), this.getOuterConstraints(2, 2));
    }

    private GridBagConstraints getDescPanelConstraint() {
        final var gbc = getOuterConstraints(0,1);
        gbc.anchor = GridBagConstraints.PAGE_END;
        return gbc;
    }

    private GridBagConstraints getOuterConstraints(int x, int y) {
        final var gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weighty = 0.2f;
        gbc.weightx = 0.2f;
        gbc.anchor = GridBagConstraints.PAGE_END;
        return gbc;
    }

    @Override
    public void updateCards(final Set<SpecialCardInfo> toSell) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCards'");
    }
}
