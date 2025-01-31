package it.unibo.balatrolt.model.impl;

import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Support class that orders list of PlayableCard.
 * @author Justin Carideo
 */
public final class SortingPlayableHelpers {

    private SortingPlayableHelpers() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param hand
     * @return the list ordered by Rank
     */
    public static List<PlayableCard> sortingByRank(final List<PlayableCard> hand) {
        Preconditions.checkArgument(hand.isEmpty(), "Hand cannot be empty");
        return hand.stream().sorted((a, b) -> a.getRank().compareTo(b.getRank())).toList();
    }

    /**
     * @param hand
     * @return the list ordered by Suit
     */
    public static List<PlayableCard> sortingBySuit(final List<PlayableCard> hand) {
        Preconditions.checkArgument(hand.isEmpty(), "Hand cannot be empty");
        return sortingByRank(hand).stream().sorted((a, b) -> a.getSuit().compareTo(b.getSuit())).toList();
    }
}
