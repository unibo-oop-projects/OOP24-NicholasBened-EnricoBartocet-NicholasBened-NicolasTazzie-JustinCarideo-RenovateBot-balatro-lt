package it.unibo.balatrolt;

import java.io.IOException;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.impl.MasterControllerImpl;
import it.unibo.balatrolt.view.api.View;
import it.unibo.balatrolt.view.impl.SwingView;

/**
 * Entry point of the app, it launches the GUI
 * so that the game can start.
 */
final class LaunchApp {

    private LaunchApp() { }

    /**
     * Starts the application.
     *
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        final MasterController controller = new MasterControllerImpl();
        final View view = new SwingView(controller);
        controller.attachView(view);
        controller.handleEvent(BalatroEvent.MAIN_MENU, null);
    }
}
