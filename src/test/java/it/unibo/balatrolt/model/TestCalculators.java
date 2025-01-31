package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 * Unit tests for Combination Calculators.
 * @author Justin Carideo
 */
class TestCalculators {

    private final CombinationCalculatorFactoryImpl factory = new CombinationCalculatorFactoryImpl();
    private List<PlayableCard> hand;

    @BeforeEach
    void init() {
        this.hand = fill();
    }

    private List<PlayableCard> fill() {
        return List.of(
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
            new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.HEARTS)),
            new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS))
        );
    }

    private List<PlayableCard> getTestPlayedCard() {
        return List.of(
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES))
        );
    }

    /**
     * Test whether the hand is a high card.
     */
    @Test
    void testHighCard() {
        final var expected = new CombinationImpl(14, 1, CombinationType.HIGH_CARD);
        final var result = this.factory.highCardCalculator().compute(CombinationType.HIGH_CARD, hand);
        assertEquals(expected.getBasePoints().basePoints(), result.getBasePoints().basePoints());
        assertEquals(expected.getMultiplier().multiplier(), result.getMultiplier().multiplier());
        assertEquals(expected.getCombinationType(), result.getCombinationType());
    }

    /**
     * Test whether the hand is a pair.
     */
    @Test
    void testPair() {
        final var expected = new CombinationImpl(20, 2, CombinationType.PAIR);
        final var result = this.factory.pairsCalculator().compute(CombinationType.PAIR, hand);
        assertEquals(expected.getBasePoints().basePoints(), result.getBasePoints().basePoints());
        assertEquals(expected.getMultiplier().multiplier(), result.getMultiplier().multiplier());
    }

    /**
     * Test whether the hand is a three of a kind.
     */
    @Test
    void testThree() {
        final List<PlayableCard> hand1 = List.of(
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.HEARTS)),
            new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS))
        );
        final var expected1 = new CombinationImpl(45, 3, CombinationType.THREE_OF_A_KIND);
        final var result1 = this.factory.threeOfAKindCalculator().compute(CombinationType.THREE_OF_A_KIND, hand1);
        assertEquals(expected1.getBasePoints().basePoints(), result1.getBasePoints().basePoints());
        assertEquals(expected1.getMultiplier().multiplier(), result1.getMultiplier().multiplier());

        final List<PlayableCard> hand2 = getTestPlayedCard();
        final var expected2 = new CombinationImpl(45, 3, CombinationType.THREE_OF_A_KIND);
        final var result2 = this.factory.threeOfAKindCalculator().compute(CombinationType.THREE_OF_A_KIND, hand2);
        assertEquals(expected2.getBasePoints().basePoints(), result2.getBasePoints().basePoints());
        assertEquals(expected2.getMultiplier().multiplier(), result2.getMultiplier().multiplier());
    }

    /**
     * Test whether the hand is a four of a kind.
     */
    @Test
    void testFourOfAKind() {
        final List<PlayableCard> hand = List.of(
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.HEARTS)),
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS))
        );
        final var expected = new CombinationImpl(80, 7, CombinationType.FOUR_OF_A_KIND);
        final var result = this.factory.fourOfAKindCalculator().compute(CombinationType.FOUR_OF_A_KIND, hand);
        assertEquals(expected.getBasePoints().basePoints(), result.getBasePoints().basePoints());
        assertEquals(expected.getMultiplier().multiplier(), result.getMultiplier().multiplier());
    }

    /**
     * Test whether the hand is a five-card combination.
     */
    @Test
    void testFiveCardsCombination() {
        final List<PlayableCard> hand = List.of(
            new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES)),
            new PlayableCardImpl(new Pair<>(Rank.QUEEN, Suit.HEARTS)),
            new PlayableCardImpl(new Pair<>(Rank.JACK, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.TEN, Suit.CLUBS))
        );
        final var expected = new CombinationImpl(81, 4, CombinationType.STRAIGHT);
        final var result = this.factory.fiveCardsCalculator().compute(CombinationType.STRAIGHT, hand);
        assertEquals(expected.getBasePoints().basePoints(), result.getBasePoints().basePoints());
        assertEquals(expected.getMultiplier().multiplier(), result.getMultiplier().multiplier());

        final List<PlayableCard> hand2 = List.of(
            new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.QUEEN, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.JACK, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.TEN, Suit.CLUBS))
        );
        final var expected2 = new CombinationImpl(201, 8, CombinationType.ROYAL_FLUSH);
        final var result2 = this.factory.fiveCardsCalculator().compute(CombinationType.ROYAL_FLUSH, hand2);
        assertEquals(expected2.getBasePoints().basePoints(), result2.getBasePoints().basePoints());
        assertEquals(expected2.getMultiplier().multiplier(), result2.getMultiplier().multiplier());
    }
}
