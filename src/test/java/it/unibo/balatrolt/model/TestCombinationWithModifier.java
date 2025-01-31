package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.PlayedHand;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierStatsSupplierBuilderImpl;
import it.unibo.balatrolt.model.impl.cards.specialcard.JokerSupplierImpl;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;

class TestCombinationWithModifier {

    private static final int EXPECTED_MULTIPLIER_STD = 3;
    private static final int EXPECTED_POINTS_STD = 45;

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
        assertEquals(3, combination.getMultiplier().multiplier());
        final int indexSelected = 3;
        final var joker = new JokerSupplierImpl().getJokerList().get(indexSelected);
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
        final int indexSelected = 3;
        final var joker = new JokerSupplierImpl().getJokerList().get(indexSelected);
        setJokerStatus(hand, combination, joker);
        combination.applyModifier(joker.getModifier().get());
        assertEquals(EXPECTED_POINTS_STD, combination.getBasePoints().basePoints());
        assertEquals(EXPECTED_MULTIPLIER_STD, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerAllApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard());
        final Combination combination = hand.evaluateCombination();
        final int indexSelected = 3;
        final var joker1 = new JokerSupplierImpl().getJokerList().get(indexSelected);
        setJokerStatus(hand, combination, joker1);
        final int indexSelected2 = 1;
        final var joker2 = new JokerSupplierImpl().getJokerList().get(indexSelected2);
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
        final int indexSelected = 3;
        final var joker1 = new JokerSupplierImpl().getJokerList().get(indexSelected);
        setJokerStatus(hand, combination, joker1);
        final int indexSelected2 = 1;
        final var joker2 = new JokerSupplierImpl().getJokerList().get(indexSelected2);
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
        final int indexSelected = 3;
        final var joker1 = new JokerSupplierImpl().getJokerList().get(indexSelected);
        setJokerStatus(hand, combination, joker1);
        final int indexSelected2 = 4;
        final var joker2 = new JokerSupplierImpl().getJokerList().get(indexSelected2);
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        assertEquals(EXPECTED_POINTS_STD, combination.getBasePoints().basePoints());
        final int mul = 2;
        assertEquals(EXPECTED_MULTIPLIER_STD * mul, combination.getMultiplier().multiplier());
    }

}
