package it.unibo.balatrolt.view.api;

import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

public interface View {
    void notifyErrror(String name, String desc);

    void showDecks(Set<DeckInfo> setMap);

    void showAnte();

    void showRound();

    void updateHand();

    void updateCombinationStatus();

    void updateScore();

    void updatePlayedCards();

    void showShop(Set<SpecialCardInfo> toSell);

    void updateShopCards(Set<SpecialCardInfo> toSell);

    void updateSpecialCards();
}
