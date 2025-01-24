package it.unibo.balatrolt.model.impl;

import java.util.List;

import it.unibo.balatrolt.model.api.PlayableCard;

public final class SortingPlayableHelpers {

    private SortingPlayableHelpers() {
        throw new UnsupportedOperationException();
    }

    public static List<PlayableCard> sortingByRank(final List<PlayableCard> hand) {
        return hand.stream().sorted((a, b) -> Integer.compare(a.getRank().ordinal(), b.getRank().ordinal())).toList();
    }

    public static List<PlayableCard> sortingBySuit(final List<PlayableCard> hand) {
        return hand.stream().sorted((a, b) -> Integer.compare(a.getSuit().ordinal(), b.getSuit().ordinal())).toList();
    }
}
