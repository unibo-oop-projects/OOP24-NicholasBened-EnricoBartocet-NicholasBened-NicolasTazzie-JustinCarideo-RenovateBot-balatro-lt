package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;

/**
 * This class builds the title in the left part of the GUI.
 */
public final class TitlePanel extends JPanel {

    private static final String MAIN_FONT = "COPPER_BLACK";
    private static final Color SMALL_BLIND_COLOR = Color.CYAN.darker().darker();
    private static final Color BIG_BLIND_COLOR = Color.ORANGE.darker().darker();
    private static final Color BOSS_BLIND_COLOR = Color.MAGENTA.darker().darker();
    private static final int SIZE_TITLE_BLIND = 30;
    private final BlindInfo info;

    /**
     * Builds the title panel about the blind that
     * is going to be challenged.
     * @param info
     */
    public TitlePanel(final BlindInfo info) {
        this.info = info;
        final Color backgroundColor = getBlindColor();
        setLayout(new BorderLayout());

        final JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.add(getTitle(backgroundColor), BorderLayout.CENTER);

        this.add(titleContainer, BorderLayout.CENTER);
        this.add(getRewardLabel(backgroundColor), BorderLayout.SOUTH);
    }

    /**
     * Builds the title
     * @param color
     * @return
     */
    private JLabel getTitle(final Color color) {
        final JLabel titleBlind = new JLabel();
        titleBlind.setText(getBlindTitle());
        titleBlind.setFont(getFont(MAIN_FONT, SIZE_TITLE_BLIND));
        titleBlind.setBackground(color);
        titleBlind.setForeground(Color.white);
        titleBlind.setOpaque(true);
        titleBlind.setHorizontalAlignment(SwingConstants.CENTER);
        titleBlind.setBorder(new EmptyBorder(10, 20, 10, 20));
        return titleBlind;
    }

    private JLabel getRewardLabel(final Color color) {
        final JLabel rewardLabel = new JLabel();
        rewardLabel.setBackground(color.darker());
        rewardLabel.setText("Reward: $" + info.reward());
        rewardLabel.setOpaque(true);
        rewardLabel.setFont(getFont(MAIN_FONT, SIZE_TITLE_BLIND));
        rewardLabel.setForeground(Color.white);
        rewardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rewardLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        return rewardLabel;
    }

    /**
     * Gives the title based on the blind id.
     * @return a string based on the id
     */
    private String getBlindTitle() {
        return switch (this.info.id()) {
            case 1 -> "SMALL BLIND";
            case 2 -> "BIG BLIND";
            case 3 -> "BOSS BLIND";
            default -> "Error";
        };
    }

    /**
     * Gives the color based on the blind id.
     * @return a color based on the id
     */
    private Color getBlindColor() {
        return switch (this.info.id()) {
            case 1 -> SMALL_BLIND_COLOR;
            case 2 -> BIG_BLIND_COLOR;
            case 3 -> BOSS_BLIND_COLOR;
            default -> Color.DARK_GRAY;
        };
    }

    /*
     * Gives back the requested font with the given size.
     */
    private Font getFont(final String nameFont, final float fontSize) {
        Font font = new Font("Arial", Font.PLAIN, (int) fontSize);
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/font/" + nameFont + ".TTF")
            );
            font = font.deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot load font");
        }
        return font;
    }
}
