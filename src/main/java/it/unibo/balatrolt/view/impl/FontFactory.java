package it.unibo.balatrolt.view.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.swing.JOptionPane;

import java.awt.FontFormatException;
import java.awt.Component;
import java.awt.Font;

/**
 * Creates a font with the given name.
 * Used to avoid code duplications.
 * @author Nicholas Benedetti
 */
final class FontFactory implements Serializable {
    static final long serialVersionUID = 1L;
    private static final String BASE_FONT = "Arial";

    /**
     * Gives back the requested font with the given size.
     * @param nameFont name of the font.
     * @param fontSize size of the font.
     * @param component component in which throwing messageDialogs.
     * @return the Font requested with the given size.
     */
    Font getFont(final String nameFont, final float fontSize, final Component component) {
        Font font = new Font(BASE_FONT, Font.PLAIN, (int) fontSize);
        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/font/" + nameFont + ".TTF")
            );
            font = font.deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            JOptionPane.showMessageDialog(component, "Cannot load font");
        }
        return font;
    }
}
