package it.unibo.balatrolt.view.api;

import java.util.List;
import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

public interface View {
    void showDecks(Set<DeckInfo> setMap);

    void showAnte(AnteInfo anteInfo);

    void showRound(BlindInfo info, BlindStats stats, List<SpecialCardInfo> specialCards, List<PlayableCardInfo> playableCards);

    void updateHand(List<PlayableCardInfo> hand);

    void updateCombinationStatus();

    void updateScore();

    void updatePlayedCards();

    void showShop();

    void updateSpecialCards();
}
