package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;

public interface LevelsController {

    AnteInfo getCurrentAnteInfo();

    List<String> getHand();
}
