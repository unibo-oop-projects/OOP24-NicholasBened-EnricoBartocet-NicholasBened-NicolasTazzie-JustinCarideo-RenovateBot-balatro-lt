package it.unibo.balatrolt.controller.api;

import com.google.common.base.Optional;

import it.unibo.balatrolt.view.api.View;

/**
 * It represents the main controller of the application.
 * It's essentally an event dispatcher, which wraps multiple sub-controllers
 * that interact with the main entry points of the model and eventually updates
 * all the views attached to it.
 */
public interface MasterController {

    /**
     * Handles a {@link BalatroEvent} and interacts with the model entry points.
     * @param e event type
     * @param data eventual data that should be passed if the event handler requires
     * @throws IllegalArgumentException if data is not compatible with the passed event
     */
    void handleEvent(BalatroEvent e, Optional<?> data);

    /**
     * It attaches a {@link View} to the controller.
     * @param v {@link View} to attach
     * @throws NullPointerException if v is null
     */
    void attachView(View v);
}
