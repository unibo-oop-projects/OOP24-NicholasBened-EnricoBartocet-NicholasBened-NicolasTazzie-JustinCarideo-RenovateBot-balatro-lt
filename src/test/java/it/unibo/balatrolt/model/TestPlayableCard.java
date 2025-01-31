package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;

/**
 * Tester for a playable card constructor and it's methods.
 */
final class TestPlayableCard {
    private PlayableCard playableCard;
    private static final Rank RANK = Rank.ACE;
    private static final Suit SUIT = Suit.SPADES;

    @BeforeEach
    void init() {
        this.playableCard = new PlayableCardImpl(new Pair<>(RANK, SUIT));
    }

    @Test
    void testConfiguration() {
        assertEquals(RANK, this.playableCard.getRank());
        assertEquals(SUIT, this.playableCard.getSuit());
        assertEquals(Optional.absent(), this.playableCard.getModifier());
    }

    @Test
    void testEquals() {
        assertEquals(this.playableCard, this.playableCard);
        final PlayableCard otherCard = new PlayableCardImpl(new Pair<>(Rank.KING, Suit.DIAMONDS));
        assertNotEquals(this.playableCard, otherCard);
    }
}
