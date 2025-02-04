package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;

public interface PlayerController {

    List<SpecialCardInfo> getSpecialCards();

    PlayerStatus getPlayerStatus();

    void addCurrency(int reward);

    void spendCurrency(int money);

    void addSpecialCard(SpecialCard card);

    void sellSpecialCard(SpecialCardInfo card);

    int getMaxSpecialCards();

    int getCurrency();

    DeckInfo getDeck();

}
