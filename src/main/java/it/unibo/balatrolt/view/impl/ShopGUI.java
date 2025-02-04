package it.unibo.balatrolt.view.impl;

import javax.swing.JPanel;

import it.unibo.balatrolt.controller.impl.MasterControllerImpl;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * Test class. To remove
 */
public class ShopGUI extends JFrame {

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
    public ShopGUI(final int size) throws IOException {
        // creating left panel
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WIDTH_EXPAND * size, HEIGHT_EXPAND * size);
        //System.out.println(this.getSize());
        buildShopPanel();
        setVisible(true);
    }

    private void buildShopPanel() {
        // creating left panel
        final JPanel shopPanel = new ShopViewImpl(new MasterControllerImpl());
        this.add(shopPanel, BorderLayout.CENTER);
    }
}
