package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.RoundStatus;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.PlayableCard;

public interface LevelsController {

    AnteInfo getCurrentAnte();

    BlindInfo getCurrentBlindInfo();

    BlindStats getCurrentBlindStats();

    List<PlayableCardInfo> getHand();

    void discardCards(List<PlayableCardInfo> cards);

    void playCards(List<PlayableCardInfo> cards, PlayerStatus player);

    RoundStatus getRoundStatus();

    List<PlayableCard> translatePlayableCard(List<PlayableCardInfo> cards);

    void updateAnte();

    boolean isOver();
}
