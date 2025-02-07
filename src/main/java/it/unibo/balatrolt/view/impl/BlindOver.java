package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
/**
 * Show the Blind over GUI with statistics and
 * possibility to open the shop.
 */
public class BlindOver extends JPanel {
    private static final int PADDING_MULT = 2;
    private static final int WIDTH_PADDING = 20;
    private static final int HEIGHT_PADDING = 10;
    private static final float FONT_TITLE_SIZE = 52f;
    static final long serialVersionUID = 1L;
    private static final String FONT = "COPPER_BLACK";
    private static final float FONT_SIZE = 30f;
    private final FontFactory fontFactory = new FontFactory();

    /**
     * Builds the GUI.
     * @param controller master controller.
     * @param blindInfo static info about the blind.
     * @param blindStats statistic of the game till now.
     */
    public BlindOver(final MasterController controller, final BlindInfo blindInfo, final BlindStats blindStats) {
        super(new BorderLayout());
        this.setFont(this.fontFactory.getFont(FONT, FONT_SIZE, this));
        this.setBackground(Color.GREEN.darker().darker().darker());
        final var outerPanel = new JPanel();
        outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
        final var panel = getBlindOverPanel(controller, blindInfo);
        outerPanel.add(Box.createVerticalGlue());
        outerPanel.add(panel);
        outerPanel.add(Box.createVerticalGlue());
        outerPanel.setBackground(this.getBackground());
        this.add(outerPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel getBlindOverPanel(final MasterController controller, final BlindInfo blindInfo) {
        final var panel = new JPanel(new FlowLayout());
        final JButton button = getOpenShopButton(controller);
        final var innerPanel = new JPanel(new BorderLayout());
        final var blindDefeatedLbl = new JLabel("BLIND DEFEATED", JLabel.CENTER);
        blindDefeatedLbl.setForeground(Color.WHITE);
        blindDefeatedLbl.setFont(this.fontFactory.getFont(FONT, FONT_TITLE_SIZE, this));
        innerPanel.add(blindDefeatedLbl, BorderLayout.NORTH);
        final var chipsEarnedLbl = new JLabel(this.getRewardString(blindInfo), JLabel.CENTER);
        chipsEarnedLbl.setForeground(Color.ORANGE);
        chipsEarnedLbl.setFont(this.getFont());
        chipsEarnedLbl.setBorder(this.getPaddingBorderCenterLabel());
        innerPanel.add(chipsEarnedLbl, BorderLayout.CENTER);
        innerPanel.add(button, BorderLayout.SOUTH);
        innerPanel.setBackground(Color.DARK_GRAY);
        innerPanel.setBorder(this.getPaddingBorder());
        panel.add(innerPanel);
        panel.setBackground(this.getBackground());
        return panel;
    }

    private JButton getOpenShopButton(final MasterController controller) {
        final JButton button = new JButton("OPEN SHOP");
        button.addActionListener(a -> controller.handleEvent(BalatroEvent.OPEN_SHOP, Optional.absent()));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.ORANGE);
        button.setFont(this.getFont());
        button.setBorder(this.getPaddingBorder());
        return button;
    }

    private Border getPaddingBorder() {
        return BorderFactory.createEmptyBorder(
            HEIGHT_PADDING,
            WIDTH_PADDING,
            HEIGHT_PADDING,
            WIDTH_PADDING
        );
    }

    private Border getPaddingBorderCenterLabel() {
        return BorderFactory.createEmptyBorder(
            HEIGHT_PADDING * PADDING_MULT,
            WIDTH_PADDING * PADDING_MULT,
            HEIGHT_PADDING * PADDING_MULT,
            WIDTH_PADDING * PADDING_MULT
        );
    }

    private String getRewardString(final BlindInfo blindInfo) {
        return Stream.of(Integer.toString(blindInfo.reward()))
            .collect(Collectors.joining("", "Money earned: ", "$"));
    }

}
