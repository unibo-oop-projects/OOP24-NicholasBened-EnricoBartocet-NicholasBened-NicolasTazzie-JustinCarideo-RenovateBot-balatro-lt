package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.PlayedHand;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;
import it.unibo.balatrolt.model.impl.modifier.ModifierStatsSupplierBuilderImpl;
import it.unibo.balatrolt.model.impl.specialcard.JokerSupplierImpl;

class TestCombinationWithModifier {

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
        assertEquals(45, combination.getBasePoints().basePoints());
        assertEquals(3, combination.getMultiplier().multiplier());
        final var joker = new JokerSupplierImpl().getJokerList().get(3);
        setJokerStatus(hand, combination, joker);
        combination.applyModifier(joker.getModifier().get());
        assertEquals(95, combination.getBasePoints().basePoints());
        assertEquals(3, combination.getMultiplier().multiplier());
    }

    @Test
    void testSingleJokerNotApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard2());
        final Combination combination = hand.evaluateCombination();
        final var joker = new JokerSupplierImpl().getJokerList().get(3);
        setJokerStatus(hand, combination, joker);
        combination.applyModifier(joker.getModifier().get());
        assertEquals(45, combination.getBasePoints().basePoints());
        assertEquals(3, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerAllApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard());
        final Combination combination = hand.evaluateCombination();
        final var joker1 = new JokerSupplierImpl().getJokerList().get(3);
        setJokerStatus(hand, combination, joker1);
        final var joker2 = new JokerSupplierImpl().getJokerList().get(1);
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        assertEquals(45 + 50, combination.getBasePoints().basePoints());
        assertEquals(3 * 2, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerNoneApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard2());
        final Combination combination = hand.evaluateCombination();
        final var joker1 = new JokerSupplierImpl().getJokerList().get(3);
        setJokerStatus(hand, combination, joker1);
        final var joker2 = new JokerSupplierImpl().getJokerList().get(1);
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        assertEquals(45, combination.getBasePoints().basePoints());
        assertEquals(3, combination.getMultiplier().multiplier());
    }

    @Test
    void testMultipleJokerOneApplied() {
        final PlayedHand hand = new PlayedHandImpl(getTestPlayedCard2());
        final Combination combination = hand.evaluateCombination();
        final var joker1 = new JokerSupplierImpl().getJokerList().get(3);
        setJokerStatus(hand, combination, joker1);
        final var joker2 = new JokerSupplierImpl().getJokerList().get(4);
        setJokerStatus(hand, combination, joker2);
        combination.applyModifier(joker1.getModifier().get());
        combination.applyModifier(joker2.getModifier().get());
        assertEquals(45, combination.getBasePoints().basePoints());
        assertEquals(3 * 2, combination.getMultiplier().multiplier());
    }

}