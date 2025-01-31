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
 * Test class for Combination Recognizers.
 */
class TestRecognizers {

    private final CombinationRecognizerHelpersImpl helper = new CombinationRecognizerHelpersImpl();
    private List<PlayableCard> hand;

    /**
     * Initializes a new hand before each test.
     */
    @BeforeEach
    void init() {
        this.hand = fill();
    }

    /**
     * @return a hand filled with some cards.
     */
    private List<PlayableCard> fill() {
        return List.of(
            new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.CLUBS)),
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

    private List<PlayableCard> getTestPlayedCardPair() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.TEN, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.TEN, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.THREE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.JACK, Suit.CLUBS)));
    }

    private List<PlayableCard> getTestPlayedCardTwoPair() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.FOUR, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.FOUR, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS)));
    }

    private List<PlayableCard> getTestPlayedCardThreeOfAKind() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.QUEEN, Suit.SPADES)));
    }

    private List<PlayableCard> getTestPlayedCardStraight() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.HEARTS)));
    }

    private List<PlayableCard> getTestPlayedCardFlush() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.JACK, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)));
    }

    private List<PlayableCard> getTestPlayedCardFullHouse() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.QUEEN, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.QUEEN, Suit.SPADES)));
    }

    private List<PlayableCard> getTestPlayedCardFourOfAKind() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.HEARTS)));
    }

    private List<PlayableCard> getTestPlayedCardStraightFlush() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.SEVEN, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.EIGHT, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.NINE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.TEN, Suit.SPADES)));
    }

    private List<PlayableCard> getTestPlayedCardRoyalFlush() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.TEN, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.JACK, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.QUEEN, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.HEARTS)));
    }

    /**
     * Test whether the hand is a high card.
     */
    @Test
    void testHighCard() {
        assertTrue(this.helper.highCardRecognizer().recognize(hand));
        assertTrue(this.helper.highCardRecognizer().recognize(getTestPlayedCard()));
        assertTrue(this.helper.highCardRecognizer().recognize(getTestPlayedCardPair()));
        assertTrue(this.helper.highCardRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertTrue(this.helper.highCardRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertTrue(this.helper.highCardRecognizer().recognize(getTestPlayedCardStraight()));
    }

    /**
     * Test whether the hand is a pair.
     */
    @Test
    void testPair() {
        assertFalse(this.helper.pairRecognizer().recognize(hand));
        assertFalse(this.helper.pairRecognizer().recognize(getTestPlayedCard()));
        assertTrue(this.helper.pairRecognizer().recognize(getTestPlayedCardPair()));
        assertTrue(this.helper.pairRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.pairRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.pairRecognizer().recognize(getTestPlayedCardStraight()));
    }

    /**
     * Test whether the hand is a two pair.
     */
    @Test
    void testTwoPair() {
        assertFalse(this.helper.twoPairRecognizer().recognize(hand));
        assertFalse(this.helper.twoPairRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.twoPairRecognizer().recognize(getTestPlayedCardPair()));
        assertTrue(this.helper.twoPairRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.twoPairRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.twoPairRecognizer().recognize(getTestPlayedCardStraight()));
    }

    /**
     * Test whether the hand is a three of a kind.
     */
    @Test
    void testThreeOfAKind() {
        assertFalse(this.helper.threeOfAKindRecognizer().recognize(hand));
        assertTrue(this.helper.threeOfAKindRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.threeOfAKindRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.threeOfAKindRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertTrue(this.helper.threeOfAKindRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.threeOfAKindRecognizer().recognize(getTestPlayedCardStraight()));
    }

    /**
     * Test whether the hand is a straight.
     */
    @Test
    void testStraight() {
        assertTrue(this.helper.straightRecognizer().recognize(hand));
        assertFalse(this.helper.straightRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.straightRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.straightRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.straightRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertTrue(this.helper.straightRecognizer().recognize(getTestPlayedCardStraight()));
    }

    /**
     * Test whether the hand is a flush.
     */
    @Test
    void testFlush() {
        assertFalse(this.helper.flushRecognizer().recognize(hand));
        assertFalse(this.helper.flushRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.flushRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.flushRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.flushRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.flushRecognizer().recognize(getTestPlayedCardStraight()));
        assertTrue(this.helper.flushRecognizer().recognize(getTestPlayedCardFlush()));
    }

    /**
     * Test whether the hand is a full house.
     */
    @Test
    void testFullHouse() {
        assertFalse(this.helper.fullHouseRecognizer().recognize(hand));
        assertFalse(this.helper.fullHouseRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.fullHouseRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.fullHouseRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.fullHouseRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.fullHouseRecognizer().recognize(getTestPlayedCardStraight()));
        assertTrue(this.helper.fullHouseRecognizer().recognize(getTestPlayedCardFullHouse()));
    }

    /**
     * Test whether the hand is a four of a kind.
     */
    @Test
    void testFourOfAKind() {
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(hand));
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.fourOfAKindRecognizer().recognize(getTestPlayedCardStraight()));
        assertTrue(this.helper.fourOfAKindRecognizer().recognize(getTestPlayedCardFourOfAKind()));
    }

    /**
     * Test whether the hand is a straight flush.
     */
    @Test
    void testStraightFlush() {
        assertFalse(this.helper.straightFlushRecognizer().recognize(hand));
        assertFalse(this.helper.straightFlushRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.straightFlushRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.straightFlushRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.straightFlushRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.straightFlushRecognizer().recognize(getTestPlayedCardStraight()));
        assertTrue(this.helper.straightFlushRecognizer().recognize(getTestPlayedCardStraightFlush()));
    }

    /**
     * Test whether the hand is a royal flush.
     */
    @Test
    void testRoyalFlush() {
        assertFalse(this.helper.royalFlushRecognizer().recognize(hand));
        assertFalse(this.helper.royalFlushRecognizer().recognize(getTestPlayedCard()));
        assertFalse(this.helper.royalFlushRecognizer().recognize(getTestPlayedCardPair()));
        assertFalse(this.helper.royalFlushRecognizer().recognize(getTestPlayedCardTwoPair()));
        assertFalse(this.helper.royalFlushRecognizer().recognize(getTestPlayedCardThreeOfAKind()));
        assertFalse(this.helper.royalFlushRecognizer().recognize(getTestPlayedCardStraight()));
        assertTrue(this.helper.royalFlushRecognizer().recognize(getTestPlayedCardRoyalFlush()));
    }
}
