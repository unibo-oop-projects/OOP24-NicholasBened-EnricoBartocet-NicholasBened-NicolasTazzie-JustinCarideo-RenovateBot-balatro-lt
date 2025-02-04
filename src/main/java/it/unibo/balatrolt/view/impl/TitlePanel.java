package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;

public class TitlePanel extends JPanel{

    private static final Color SMALL_BLIND_COLOR = Color.CYAN.darker().darker().darker();
    private static final Color BIG_BLIND_COLOR = Color.ORANGE.darker().darker().darker();
    private static final Color BOSS_BLIND_COLOR = Color.MAGENTA.darker().darker().darker();
    private static final int SIZE_TITLE_BLIND = 36;

    private final BlindInfo info;

    public TitlePanel(BlindInfo info) {
        this.info = info;
        final Color backgroundColor = getBlindColor();
        this.setLayout(new BorderLayout());

        final JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.add(getTitle(backgroundColor), BorderLayout.CENTER);
        titleContainer.setPreferredSize(new Dimension(0, 100));

        this.add(titleContainer, BorderLayout.NORTH);
        this.add(getRewardLabel(backgroundColor));
    }

    private JLabel getTitle(final Color color) {
        final JLabel titleBlind = new JLabel();
        titleBlind.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        titleBlind.setText(getBlindTitle());
        titleBlind.setFont(new Font(Font.SANS_SERIF, Font.BOLD, SIZE_TITLE_BLIND));
        titleBlind.setBackground(color);
        titleBlind.setOpaque(true);
        titleBlind.setHorizontalAlignment(SwingConstants.CENTER);
        return titleBlind;
    }

    private JLabel getRewardLabel(final Color color) {
        final JLabel rewardLabel = new JLabel();
        rewardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rewardLabel.setBackground(color.brighter());
        rewardLabel.setText("Reward: $" + info.reward());
        rewardLabel.setOpaque(true);
        rewardLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, SIZE_TITLE_BLIND));
        rewardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return rewardLabel;
    }

    private String getBlindTitle() {
        return switch (this.info.id()) {
            case 1 -> "SMALL BLIND";
            case 2-> "BIG BLIND";
            case 3 -> "BOSS BLIND";
            default -> "Error";
        };
    }

    private Color getBlindColor() {
        return switch (this.info.id()) {
            case 1 -> SMALL_BLIND_COLOR;
            case 2-> BIG_BLIND_COLOR;
            case 3 -> BOSS_BLIND_COLOR;
            default -> Color.DARK_GRAY;
        };
    }
}
