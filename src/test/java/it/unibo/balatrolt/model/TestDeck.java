package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.cards.Deck;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.cards.deck.DeckImpl;

/**
 * Tester for deck and it's methods.
 */
class TestDeck {
    private static final PlayableCard TWO_HEARTS = new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.HEARTS));
    private static final PlayableCard ACE_SPADES = new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES));
    private static final int DECK_SIZE = 52;

    private Deck deck;

    @BeforeEach
    void init() {
        this.deck = new DeckImpl();
    }

    @Test
    void testNormal() {
        assertEquals(this.deck.getCards().size(), DECK_SIZE);
        assertEquals(this.deck.getCards().get(0), TWO_HEARTS);
        assertEquals(this.deck.getCards().get(DECK_SIZE - 1), ACE_SPADES);
    }

    @Test
    void testShuffled() {
        assertNotEquals(
            this.deck.getCards(),
            this.deck.getShuffledCards());
    }
}
