package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main GUI: here we build and mix togheter all the
 * parts that will form the final GUI.
 * @author Benedetti Nicholas
 */
public class SwingMainRound extends JFrame {

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
    public SwingMainRound(final int size) throws IOException {
        //creating left panel
        this.buildLeftPanel();

        //creating slots panel
        this.buildSlotPanel();

        this.setVisible(size);
    }

    private void setVisible(final int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH_EXPAND * size, HEIGHT_EXPAND * size);
        this.setVisible(true);
    }

    private void buildLeftPanel() {
        //creating left panel
        final JPanel leftPanel = new LeftGUI().build();
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void buildSlotPanel() {
        //creating slots panel
        try {
            System.out.println(this.getSize().height);
            final JPanel slotPanel = new SlotGUI();
            this.add(slotPanel);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
