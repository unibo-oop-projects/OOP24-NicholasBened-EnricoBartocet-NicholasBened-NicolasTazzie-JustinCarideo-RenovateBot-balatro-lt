package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.PlayedHand;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierStatsSupplierBuilderImpl;
import it.unibo.balatrolt.model.impl.cards.specialcard.JokerCatalogMisc;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;

class TestCombinationWithModifier {
    private static final String HEART_DOUBLER = "the heart doubler";
    private static final String KING_DONOUR = "the king donour";
    private static final String DIAMOND_DOUBLER = "the diamond doubler";
    private static final int EXPECTED_MULTIPLIER_STD = 4;
    private static final int EXPECTED_POINTS_STD = 35;
    private final JokerCatalog misc = new JokerCatalogMisc();

    private List<PlayableCard> getTestPlayedCard() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.HEARTS)));
    }

    private List<PlayableCard> getTestPlayedCard2() {
        return List.of(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.HEARTS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.SIX, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.HEARTS)));
    }

    private void setJokerStatus(final PlayedHand hand, final Combination combination, final Joker joker) {
        joker.getModifier().get().setGameStatus(new ModifierStatsSupplierBuilderImpl()
            .addCurrentCombination(combination.getCombinationType())
            .addPlayedCards(hand.getCards().stream().collect(Collectors.toSet()))
            .build());
    }

    @Test
    void testSingleJoker() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard());
        final Combination combination = hand.evaluateCombination();
        assertEquals(EXPECTED_POINTS_STD, combination.getBasePoints().basePoints());
        assertEquals(4, combination.getMultiplier().multiplier());
        final var joker = this.misc.getJoker(KING_DONOUR).get();
        setJokerStatus(hand, combination, joker);
        combination.applyModifier(joker.getModifier().get());
        final int p = 50;
        assertEquals(EXPECTED_POINTS_STD + p, combination.getBasePoints().basePoints());
        assertEquals(EXPECTED_MULTIPLIER_STD, combination.getMultiplier().multiplier());
    }

    @Test
    void testSingleJokerNotApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard2());
        final Combination combination = hand.evaluateCombination();
        final var joker = this.misc.getJoker(KING_DONOUR).get();
        setJokerStatus(hand, combination, joker);
        combination.applyModifier(joker.getModifier().get());
        assertEquals(EXPECTED_POINTS_STD, combination.getBasePoints().basePoints());
        assertEquals(EXPECTED_MULTIPLIER_STD, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerAllApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard());
        final Combination combination = hand.evaluateCombination();
        final var joker1 = this.misc.getJoker(KING_DONOUR).get();
        setJokerStatus(hand, combination, joker1);
        final var joker2 = this.misc.getJoker(DIAMOND_DOUBLER).get();
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        final int p = 50;
        final int mul = 2;
        assertEquals(EXPECTED_POINTS_STD + p, combination.getBasePoints().basePoints());
        assertEquals(EXPECTED_MULTIPLIER_STD * mul, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerNoneApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard2());
        final Combination combination = hand.evaluateCombination();
        final var joker1 = this.misc.getJoker(KING_DONOUR).get();
        setJokerStatus(hand, combination, joker1);
        final var joker2 = this.misc.getJoker(DIAMOND_DOUBLER).get();
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        assertEquals(EXPECTED_POINTS_STD, combination.getBasePoints().basePoints());
        assertEquals(EXPECTED_MULTIPLIER_STD, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerOneApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard2());
        final Combination combination = hand.evaluateCombination();
        final var joker1 = this.misc.getJoker(KING_DONOUR).get();
        setJokerStatus(hand, combination, joker1);
        final var joker2 = this.misc.getJoker(HEART_DOUBLER).get();
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        assertEquals(EXPECTED_POINTS_STD, combination.getBasePoints().basePoints());
        final int mul = 2;
        assertEquals(EXPECTED_MULTIPLIER_STD * mul, combination.getMultiplier().multiplier());
    }

}
