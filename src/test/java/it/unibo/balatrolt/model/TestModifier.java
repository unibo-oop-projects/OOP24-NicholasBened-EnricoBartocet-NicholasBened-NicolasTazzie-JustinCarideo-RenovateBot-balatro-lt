package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.Combination;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;
import it.unibo.balatrolt.model.impl.modifier.ModifierStatsSupplierBuilderImpl;

class TestModifier {
    private static final int INIT_B_P = 1;
    private static final double INIT_MUL = 1;
    private static final int DELTA_B_P = 2;
    private static final double DELTA_MUL = 2.5;
    private static final int DELTA_B_P2 = 3;
    private static final double DELTA_MUL2 = 2;
    private static final int CURRENT_CURRENCY = 10;

    private ModifierBuilder builder() {
        return new ModifierBuilderImpl();
    }

    @Test
    void testBaseModifier() {
        Modifier m = builder().addBasePointsModifier(p -> p + DELTA_B_P).build();
        final int basePoints = INIT_B_P;
        final double multipler = INIT_MUL;
        // only basePoints
        assertTrue(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        // only multiplier
        m = builder().addMultiplierModifier(p -> p + DELTA_MUL).build();
        assertFalse(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        // both
        m = getStandardModifier();
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
    }

    private Modifier getStandardModifier() {
        return builder()
                .addBasePointsModifier(p -> p + DELTA_B_P)
                .addMultiplierModifier(p -> p + DELTA_MUL)
                .build();
    }

    @Test
    void testMerge() {
        Modifier base = getModifierWithPCardCondTrue();
        Modifier modifier = getMergedModifier(base);
        final double mul = INIT_MUL;
        final int baseP = INIT_B_P;
        modifier.setGameStatus(getMockStatus());
        // validStatus
        assertTrue(modifier.getBasePointMapper().isPresent());
        assertTrue(modifier.getMultiplierMapper().isPresent());
        // It should be f -> g = f + DELTA_B_P -> h = g + DELTA_B_P2
        assertEquals(baseP + DELTA_B_P + DELTA_B_P2, modifier.getBasePointMapper().get().apply(baseP));
        assertEquals(mul + DELTA_MUL2, modifier.getMultiplierMapper().get().apply(mul));
        base = getModifierWithPCardCondFalse();
        modifier = getMergedModifier(base);
        modifier.setGameStatus(getMockStatus());
        // invalid status
        assertFalse(modifier.getBasePointMapper().isPresent());
        assertFalse(modifier.getMultiplierMapper().isPresent());
    }

    @Test
    void testPlayedCardModifier() {
        final int basePoints = INIT_B_P;
        Modifier m = getModifierWithPCardCondTrue();
        m.setGameStatus(getMockStatus());
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        m = getModifierWithPCardCondFalse();
        m.setGameStatus(getMockStatus());
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
    }

    private Modifier getModifierWithPCardCondFalse() {
        return builder()
                .merge(getStandardModifier())
                .addPlayedCardBound(c -> c.stream()
                        .map(PlayableCard::getSuit)
                        .anyMatch(s -> s.equals(Suit.HEARTS)))
                .build();
    }

    private Modifier getModifierWithPCardCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addPlayedCardBound(c -> c.contains(
                        new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))))
                .build();
    }

    @Test
    void testHoldingCardModifier() {
        final double multipler = INIT_MUL;
        final int basePoints = INIT_B_P;
        Modifier m = getModifierWithHCardCondTrue();
        m.setGameStatus(getMockStatus());
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        m = getModifierWithHCardCondFalse();
        m.setGameStatus(getMockStatus());
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
    }

    private Modifier getModifierWithHCardCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addHoldingCardBound(c -> c.contains(
                        new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))))
                .build();
    }

    private Modifier getModifierWithHCardCondFalse() {
        return builder()
                .merge(getStandardModifier())
                .addHoldingCardBound(c -> c.stream()
                        .map(PlayableCard::getSuit)
                        .anyMatch(s -> s.equals(Suit.HEARTS)))
                .build();
    }

    @Test
    void testCurrentCombinationModifier() {
        final double multipler = INIT_MUL;
        final int basePoints = INIT_B_P;
        Modifier m = getModifierWithCombCondTrue();
        m.setGameStatus(getMockStatus());
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        m = getModifierWithCombCondFalse();
        m.setGameStatus(getMockStatus());
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
    }

    private Modifier getModifierWithCombCondFalse() {
        return builder()
                .merge(getStandardModifier())
                .addCombinationBound(c -> c.equals(CombinationType.ROYALFLUSH))
                .build();
    }

    private Modifier getModifierWithCombCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addCombinationBound(c -> c.equals(CombinationType.TWOPAIR))
                .build();
    }

    @Test
    void testCurrentCurrencyModifier() {
        final double multipler = INIT_MUL;
        final int basePoints = INIT_B_P;
        Modifier m = getModifierWithCurrCondTrue();
        m.setGameStatus(getMockStatus());
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        m = getModifierWithCombCurrFalse();
        m.setGameStatus(getMockStatus());
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
    }

    private Modifier getModifierWithCurrCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addCurrentCurrencyBound(c -> c < 20)
                .build();
    }

    private Modifier getModifierWithCombCurrFalse() {
        return builder()
                .merge(getStandardModifier())
                .addCurrentCurrencyBound(c -> c < 5)
                .build();
    }

    private Set<PlayableCard> getTestHoldingCards() {
        return Set.of(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FOUR, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.DIAMONDS)));
    }

    private Set<PlayableCard> getTestPlayedCard() {
        return Set.of(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES)));
    }

    private Modifier getMergedModifier(final Modifier base) {
        return builder()
                .merge(base)
                .addBasePointsModifier(p -> p + DELTA_B_P2)
                .addMultiplierModifier(mul -> mul + DELTA_MUL2)
                .build();
    }

    private ModifierStatsSupplier getMockStatus() {
        return new ModifierStatsSupplierBuilderImpl()
                .setCurrentCombination(Combination.CombinationType.TWOPAIR)
                .setHoldingCards(getTestHoldingCards())
                .setPlayedCards(getTestPlayedCard())
                .setCurrentCurrency(CURRENT_CURRENCY)
                .build();
    }
}
