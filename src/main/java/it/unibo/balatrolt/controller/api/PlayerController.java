package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.PlayerStatus;

public interface PlayerController {

    List<SpecialCardInfo> getSpecialCards();

    PlayerStatus getPlayerStatus();

    void addCurrency(int reward);
}
