package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;

/**
 * Builds the title in the left GUI.
 */
public final class TitlePanel extends JPanel {
    static final long serialVersionUID = 1L;
    private static final String FONT = "COPPER_BLACK";
    private static final Color SMALL_BLIND_COLOR = Color.CYAN.darker().darker().darker();
    private static final Color BIG_BLIND_COLOR = Color.ORANGE.darker().darker().darker();
    private static final Color BOSS_BLIND_COLOR = Color.MAGENTA.darker().darker().darker();
    private static final int SIZE_TITLE_BLIND = 30;
    private final BlindInfo info;

    /**
     * builds the title.
     * @param info Blind static information.
     */
    public TitlePanel(final BlindInfo info) {
        this.info = info;
        final Color backgroundColor = getBlindColor();
        setLayout(new BorderLayout());

        final JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.add(getTitle(backgroundColor), BorderLayout.CENTER);
        titleContainer.setPreferredSize(new Dimension(0, 100));

        add(titleContainer, BorderLayout.NORTH);
        add(getRewardLabel(backgroundColor));
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
        rewardLabel.setText("$" + info.reward());
        rewardLabel.setOpaque(true);
        rewardLabel.setFont(getFont(FONT, SIZE_TITLE_BLIND));
        rewardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return rewardLabel;
    }

    private String getBlindTitle() {
        return switch (this.info.id()) {
            case 1 -> "SMALL BLIND";
            case 2 -> "BIG BLIND";
            case 3 -> "BOSS BLIND";
            default -> "Error";
        };
    }

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
