package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.combination.CombinationCalculatorFactoryImpl;
import it.unibo.balatrolt.model.impl.combination.CombinationImpl;

/**
 * @author Justin Carideo
 */
public class TestCalculators {

    private final CombinationCalculatorFactoryImpl factory = new CombinationCalculatorFactoryImpl();
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
			new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
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
        var expected = new CombinationImpl(14, 1, CombinationType.HIGH_CARD);
        var result = this.factory.highCardCalculator().compute(CombinationType.HIGH_CARD, hand);
        assertTrue(expected.getBasePoints().basePoints() == result.getBasePoints().basePoints());
        assertTrue(expected.getMultiplier().multiplier() == result.getMultiplier().multiplier());
        assertEquals(expected.getCombinationType(), result.getCombinationType());
    }

    /**
     * Test whether the hand is a pair.
     */
    @Test
    public void testPair() {
        var expected = new CombinationImpl(20, 2, CombinationType.PAIR);
        var result = this.factory.pairsCalculator().compute(CombinationType.PAIR, hand);
        assertEquals(expected.getBasePoints().basePoints(), result.getBasePoints().basePoints());
        assertTrue(expected.getMultiplier().multiplier() == result.getMultiplier().multiplier());
    }

    /**
     * Test whether the hand is a pair.
     */
    @Test
    public void testThree() {
        this.hand = List.of(
			new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
			new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
			new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.HEARTS)),
			new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.DIAMONDS)),
			new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS))
        );
        var expected = new CombinationImpl(45, 3, CombinationType.THREE_OF_A_KIND);
        var result = this.factory.threeOfAKindCalculator().compute(CombinationType.THREE_OF_A_KIND, hand);
        assertEquals(expected.getBasePoints().basePoints(), result.getBasePoints().basePoints());
        assertTrue(expected.getMultiplier().multiplier() == result.getMultiplier().multiplier());
    }
}
