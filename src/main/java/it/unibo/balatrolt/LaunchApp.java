package it.unibo.balatrolt;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.impl.MasterControllerImpl;
import it.unibo.balatrolt.view.api.View;
import it.unibo.balatrolt.view.impl.SwingView;

/**
 * Entry point of the app, it creates a controller and launches the GUI
 * so that the game can start.
 */
final class LaunchApp {

    private LaunchApp() { }

    /**
     * Starts the application.
     * @param args unused
     */
    public static void main(final String[] args) {
        final MasterController controller = new MasterControllerImpl();
        final View view = new SwingView(controller);
        controller.attachView(view);
        controller.handleEvent(BalatroEvent.MAIN_MENU, null);
    }
}
