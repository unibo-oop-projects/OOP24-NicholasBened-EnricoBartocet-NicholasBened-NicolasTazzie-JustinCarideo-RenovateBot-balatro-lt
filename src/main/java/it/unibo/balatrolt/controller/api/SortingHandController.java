package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;

/**
 * Controller that manages the sort of {@link PlayableCard}.
 */
public interface SortingHandController {
    /**
     * Given a list of cards, it returns the cards sorted by rank.
     * @param cards to sort
     * @return cards to sort
     */
    List<PlayableCardInfo> sortByRank(List<PlayableCardInfo> cards);

    /**
     * Given a list of cards, it returns the cards sorted by suit.
     * @param cards to sort
     * @return cards to sort
     */
    List<PlayableCardInfo> sortBySuit(List<PlayableCardInfo> cards);
}