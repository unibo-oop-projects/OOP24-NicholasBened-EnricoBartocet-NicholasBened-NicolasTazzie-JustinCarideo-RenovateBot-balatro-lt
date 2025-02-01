package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Main GUI: here we build and mix togheter all the
 * parts that will form the final GUI.
 * @author Benedetti Nicholas
 */
public class SwingMainRound extends JFrame {

    static final long serialVersionUID = 1L;
    private static final int WIDTH_EXPAND = 500;
    private static final int HEIGHT_EXPAND = 300;
    private SlotGUI slotPanel;
    private JPanel leftPanel;

    /**
     * assembles and runs the GUI.
     *
     * @param size of the window.
     *
     * @throws IOException
     */
    public SwingMainRound() throws IOException {
        //creating left panel
        leftPanel = this.buildLeftPanel();

        //creating slots panel
        slotPanel = this.buildSlotPanel();

        this.setVisible();
    }

    public void updateHand(List<String> hand) {
        slotPanel.updateHand(hand);
    }

    private void setVisible() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WIDTH_EXPAND, HEIGHT_EXPAND);
        this.setVisible(true);
    }

    private JPanel buildLeftPanel() {
        //creating left panel
        final JPanel leftPanel = new LeftGUI().build();
        this.add(leftPanel, BorderLayout.WEST);
        return leftPanel;
    }

    private SlotGUI buildSlotPanel() {
        //creating slots panel
        try {
            final SlotGUI slotPanel = new SlotGUI(List.of());
            this.add(slotPanel);
            return slotPanel;
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
