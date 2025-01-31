package it.unibo.balatrolt.controller.api;

import com.google.common.base.Optional;

import it.unibo.balatrolt.view.api.View;

/**
 * It represents the main controller of the application.
 * It's essentally an event dispatcher, which contains ...
 */
public interface MasterController {

    void handleEvent(BalatroEvent e, Optional<?> data);

    void attachView(View v);
}
