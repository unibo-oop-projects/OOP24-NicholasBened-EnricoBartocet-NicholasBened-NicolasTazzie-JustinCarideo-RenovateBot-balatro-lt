package it.unibo.balatrolt;

import java.io.IOException;

import it.unibo.balatrolt.view.impl.GUI;
import it.unibo.balatrolt.view.impl.ShopGUI;

/**
 * Entry point of the app, it launches the GUI
 * so that the game can start.
 */
final class LaunchApp {

    public static final int APP_SIZE = 10;

    private LaunchApp() {}

    /**
     * Starts the application.
     *
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws java.io.IOException {
        new ShopGUI(APP_SIZE);
    }
}
