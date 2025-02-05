package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import it.unibo.balatrolt.controller.api.communication.BlindInfo;

/**
 * This class builds the title in the left part of the GUI.
 */
public final class TitlePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String MAIN_FONT = "COPPER_BLACK";
    private static final Color SMALL_BLIND_COLOR = Color.CYAN.darker().darker();
    private static final Color BIG_BLIND_COLOR = Color.ORANGE.darker().darker();
    private static final Color BOSS_BLIND_COLOR = Color.MAGENTA.darker().darker();
    private static final int SIZE_TITLE_BLIND = 30;
    private transient final BlindInfo info;
    private final FontFactory fontFactory = new FontFactory();

    /**
     * Builds the title panel about the blind that is going to be challenged.
     * @param info about the current blind
     */
    public TitlePanel(final BlindInfo info) {
        this.info = info;
        final Color backgroundColor = getBlindColor();
        super.setLayout(new BorderLayout());
        super.add(getTitlePanel(backgroundColor), BorderLayout.CENTER);
        super.add(getRewardLabel(backgroundColor), BorderLayout.SOUTH);
    }

    /**
     * Creates and configures the title panel.
     * @param color The background color
     * @return A JPanel with the title label
     */
    private JPanel getTitlePanel(final Color color) {
        final JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.add(getFormattedLabel(getBlindTitle(), color, SIZE_TITLE_BLIND), BorderLayout.CENTER);
        return titleContainer;
    }

    /**
     * Creates and configures the reward label.
     * @param color The background color
     * @return A JLabel representing the reward
     */
    private JLabel getRewardLabel(final Color color) {
        return getFormattedLabel("Reward: $" + info.reward(), color.darker(), SIZE_TITLE_BLIND);
    }

    /**
     * Creates a formatted JLabel.
     * @param text The label text
     * @param color The background color
     * @param fontSize The font size
     * @return A configured JLabel
     */
    private JLabel getFormattedLabel(final String text, final Color color, final int fontSize) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(this.fontFactory.getFont(MAIN_FONT, fontSize, this));
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        final int borderWidth = 20;
        final int borderHeight = 10;
        label.setBorder(new EmptyBorder(borderHeight, borderWidth, borderHeight, borderWidth));
        return label;
    }

    /**
     * Returns the blind title based on its ID.
     * @return The corresponding blind title
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
     * Returns the corresponding color for the blind type.
     * @return The corresponding color
     */
    private Color getBlindColor() {
        return switch (this.info.id()) {
            case 1 -> SMALL_BLIND_COLOR;
            case 2 -> BIG_BLIND_COLOR;
            case 3 -> BOSS_BLIND_COLOR;
            default -> Color.DARK_GRAY;
        };
    }
}
