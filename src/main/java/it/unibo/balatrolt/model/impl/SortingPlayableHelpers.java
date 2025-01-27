package it.unibo.balatrolt.model.impl;

import java.util.List;

import it.unibo.balatrolt.model.api.PlayableCard;

public final class SortingPlayableHelpers {

    private SortingPlayableHelpers() {
        throw new UnsupportedOperationException();
    }

    public static List<PlayableCard> sortingByRank(final List<PlayableCard> hand) {
        return hand.stream().sorted((a, b) -> a.getRank().compareTo(b.getRank())).toList();
    }

    public static List<PlayableCard> sortingBySuit(final List<PlayableCard> hand) {
        return sortingByRank(hand).stream().sorted((a, b) -> a.getSuit().compareTo(b.getSuit())).toList();
    }
}
