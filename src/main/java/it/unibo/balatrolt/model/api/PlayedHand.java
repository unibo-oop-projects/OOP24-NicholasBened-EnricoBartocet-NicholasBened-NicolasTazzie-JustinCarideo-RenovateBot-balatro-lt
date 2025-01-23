package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * Interface that models the concept of the cards
 * selected by the player.
 */
public interface PlayedHand {

    /**
     * In this case we use a list because
     * sorting could be important for evaluating
     * the combination
     * @return the list of cards played
     */
    List<PlayableCard> getCards();

    /**
     * @return the Combination that has been recognized
     */
    Combination evaluateCombination();
}
