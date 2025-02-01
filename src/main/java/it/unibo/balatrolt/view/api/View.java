package it.unibo.balatrolt.view.api;

import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;

public interface View {
    void showDecks(Set<DeckInfo> setMap);

    void showAnte(AnteInfo anteInfo);

    void showRound();

    void updateHand();

    void updateCombinationStatus();

    void updateScore();

    void updatePlayedCards();

    void showShop();

    void updateSpecialCards();
}
