package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

public interface PlayerController {

    List<SpecialCardInfo> getSpecialCards();

}
