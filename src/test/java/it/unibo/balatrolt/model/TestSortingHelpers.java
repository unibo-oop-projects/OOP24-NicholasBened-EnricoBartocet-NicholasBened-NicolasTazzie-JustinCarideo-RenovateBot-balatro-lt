package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.SortingPlayableHelpers;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;

/**
 * Class for testing SortingHelpers.
 *
 * @author Justin Carideo
 */
class TestSortingHelpers {

    private List<PlayableCard> hand;

    /**
     * Create a new instance for the hand.
     */
    @BeforeEach
    void init() {
        this.hand = fill();
    }

    /**
     * Test empty hand.
     */
    @Test
    void testSortEmptyHand() {
        final List<PlayableCard> emptyHand = Collections.emptyList();
        assertEquals(emptyHand, SortingPlayableHelpers.sortingByRank(emptyHand));
    }

    /**
     * @return a hand filled with some cards.
     */
    private List<PlayableCard> fill() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS)));
    }

    /**
     * Test sorting by rank.
     */
    @Test
    void testSortByRank() {
        final List<PlayableCard> expected = new ArrayList<>();
        expected.add(new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.CLUBS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.DIAMONDS)));
        final var result = SortingPlayableHelpers.sortingByRank(hand);
        assertEquals(expected, result);
    }

    /**
     * Test sorting by suit.
     */
    @Test
    void testSortBySuit() {
        final List<PlayableCard> expected = new ArrayList<>();
        expected.add(new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.DIAMONDS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.CLUBS)));
        expected.add(new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)));
        final var result = SortingPlayableHelpers.sortingBySuit(hand);
        assertEquals(expected, result);
    }
}
