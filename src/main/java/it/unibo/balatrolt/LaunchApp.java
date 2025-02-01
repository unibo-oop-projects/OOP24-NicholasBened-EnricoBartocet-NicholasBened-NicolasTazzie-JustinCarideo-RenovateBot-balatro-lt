package it.unibo.balatrolt;

import java.io.IOException;

import it.unibo.balatrolt.view.impl.SwingMainRound;

/**
 * Entry point of the app, it launches the GUI
 * so that the game can start.
 */
final class LaunchApp {

    public static final int APP_SIZE = 20;

    private LaunchApp() {}

    /**
     * Starts the application.
     *
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws java.io.IOException {
        new SwingMainRound(APP_SIZE);
    }
}
