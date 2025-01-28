package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.combination.CombinationRecognizerHelpersImpl;

/**
 * @author Justin Carideo
 */
public class TestRecognizers {

    private final CombinationRecognizerHelpersImpl helper = new CombinationRecognizerHelpersImpl();
    private List<PlayableCard> hand;

	/**
	 * Create a new instance for the hand.
	 */
	@BeforeEach
	public void init() {
		this.hand = fill();
	}

	/**
	 * @return a hand filled with some cards.
	 */
	public List<PlayableCard> fill() {
		return List.of(
			new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.CLUBS)),
			new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
			new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.HEARTS)),
			new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.DIAMONDS)),
			new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS))
		);
	}

    /**
     * Test whether the hand is an high card.
     */
    @Test
    public void testHighCard() {
        assertTrue(this.helper.highCardRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a pair.
     */
    @Test
    public void testPair() {
        assertFalse(this.helper.pairRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a two pair.
     */
    @Test
    public void testTwoPair() {
        assertFalse(this.helper.twoPairRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a three of a kind.
     */
    @Test
    public void testThreeOfAKind() {
        assertFalse(this.helper.threeOfAKindRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a straight.
     */
    @Test
    public void testStraight() {
        assertTrue(this.helper.straightRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a flush.
     */
    @Test
    public void testFlush() {
        assertFalse(this.helper.flushRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a full house.
     */
    @Test
    public void testFullHouse() {
        assertFalse(this.helper.fullHouseRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a four a kind.
     */
    @Test
    public void testFourOfAKind() {
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a straight flush.
     */
    @Test
    public void testStraightFlush() {
        assertFalse(this.helper.straightFlushRecognizer().recognize(hand));
    }

    /**
     * Test whether the hand is a royal flush.
     */
    @Test
    public void testRoyalFlush() {
        assertFalse(this.helper.royalFlushRecognizer().recognize(hand));
    }
}
