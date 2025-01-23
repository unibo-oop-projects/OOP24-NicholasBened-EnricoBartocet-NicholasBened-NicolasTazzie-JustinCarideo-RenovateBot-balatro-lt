package it.unibo.balatrolt.model.api;

import java.util.List;

public interface PlayedHand {

    List<PlayableCard> getCards();

    Combination evaluateCombination();
}
