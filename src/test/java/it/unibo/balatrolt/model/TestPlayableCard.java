package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;

public class TestPlayableCard {
    private PlayableCard playableCard;
    private Rank rank = Rank.ACE;
    private Suit suit = Suit.SPADES;

    @BeforeEach
    void init() {
        this.playableCard = new PlayableCardImpl(new Pair<>(rank, suit));
    }

    @Test
    void testConfiguration() {
        assertEquals(rank, this.playableCard.getRank());
        assertEquals(suit, this.playableCard.getSuit());
        assertEquals(Optional.absent(), this.playableCard.getModifier());
    }

    @Test
    void testEquals() {
        assertTrue(this.playableCard.equals(this.playableCard));
        PlayableCard otherCard = new PlayableCardImpl(new Pair<>(Rank.KING, Suit.DIAMONDS));
        assertFalse(this.playableCard.equals(otherCard));
    }
}
