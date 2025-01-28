package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main GUI: here we build and mix togheter all the
 * parts that will form the final GUI.
 */
public class GUI extends JFrame {

    static final long serialVersionUID = 1L;
    private static final int WIDTH_EXPAND = 70;
    private static final int HEIGHT_EXPAND = 50;

    /**
     * assembles and runs the GUI.
     *
     * @param size of the window.
     *
     * @throws IOException
     */
    public GUI(final int size) throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH_EXPAND * size, HEIGHT_EXPAND * size);

        //creating left panel
        final JPanel leftPanel = new LeftGUI().build();
        this.add(leftPanel, BorderLayout.WEST);

        //creating slots panel
        try {
            final JPanel slotPanel = new SlotGUI().build();
            this.add(slotPanel);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        this.setVisible(true);
    }
}
