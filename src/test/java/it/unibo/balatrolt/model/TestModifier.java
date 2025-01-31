package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierBuilder;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierStatsSupplierBuilderImpl;

final class TestModifier {
    private static final int CURR_QTY_FALSE = 5;
    private static final int CURR_QTY_TRUE = 20;
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
        CombinationModifier m = builder().addBasePointsModifier(p -> p + DELTA_B_P).build();
        final int basePoints = INIT_B_P;
        final double multipler = INIT_MUL;
        // only basePoints
        var bpMapper = m.getBasePointMapper();
        var mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
        assertEquals(basePoints + DELTA_B_P, bpMapper.get().apply(basePoints));
        // only multiplier
        m = builder().addMultiplierModifier(p -> p + DELTA_MUL).build();
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(multipler + DELTA_MUL, mulMapper.get().apply(multipler));
        // both
        m = getStandardModifier();
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
    }

    private CombinationModifier getStandardModifier() {
        return builder()
                .addBasePointsModifier(p -> p + DELTA_B_P)
                .addMultiplierModifier(p -> p + DELTA_MUL)
                .build();
    }

    @Test
    void testMerge() {
        CombinationModifier base = getModifierWithPCardCondTrue();
        CombinationModifier modifier = getMergedModifier(base);
        final double mul = INIT_MUL;
        final int baseP = INIT_B_P;
        modifier.setGameStatus(getMockStatus());
        // valid status
        var bpMapper = modifier.getBasePointMapper();
        var mulMapper = modifier.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        // It should be f -> g = f + DELTA_B_P -> h = g + DELTA_B_P2
        // and          f -> g = f + DELTA_MUL -> h = g * DELTA_MUL2
        assertEquals(baseP + DELTA_B_P + DELTA_B_P2, bpMapper.get().apply(baseP));
        assertEquals((mul + DELTA_MUL) * DELTA_MUL2, mulMapper.get().apply(mul));
        base = getModifierWithPCardCondFalse();
        modifier = getMergedModifier(base);
        modifier.setGameStatus(getMockStatus());
        bpMapper = modifier.getBasePointMapper();
        mulMapper = modifier.getMultiplierMapper();
        // invalid status
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    @Test
    void testPlayedCardModifier() {
        final int basePoints = INIT_B_P;
        CombinationModifier m = getModifierWithPCardCondTrue();
        m.setGameStatus(getMockStatus());
        var bpMapper = m.getBasePointMapper();
        var mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(basePoints + DELTA_B_P, bpMapper.get().apply(basePoints));
        m = getModifierWithPCardCondFalse();
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    private CombinationModifier getModifierWithPCardCondFalse() {
        return builder()
                .merge(getStandardModifier())
                .addPlayedCardBound(c -> c.stream()
                        .map(PlayableCard::getSuit)
                        .anyMatch(s -> s.equals(Suit.HEARTS)))
                .build();
    }

    private CombinationModifier getModifierWithPCardCondTrue() {
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
        CombinationModifier m = getModifierWithHCardCondTrue();
        m.setGameStatus(getMockStatus());
        var bpMapper = m.getBasePointMapper();
        var mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(multipler + DELTA_MUL, mulMapper.get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, bpMapper.get().apply(basePoints));
        m = getModifierWithHCardCondFalse();
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    private CombinationModifier getModifierWithHCardCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addHoldingCardBound(c -> c.contains(
                        new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))))
                .build();
    }

    private CombinationModifier getModifierWithHCardCondFalse() {
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
        CombinationModifier m = getModifierWithCombCondTrue();
        m.setGameStatus(getMockStatus());
        var bpMapper = m.getBasePointMapper();
        var mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(multipler + DELTA_MUL, mulMapper.get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, bpMapper.get().apply(basePoints));
        m = getModifierWithCombCondFalse();
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    private CombinationModifier getModifierWithCombCondFalse() {
        return builder()
                .merge(getStandardModifier())
                .addCombinationBound(c -> c.equals(CombinationType.ROYAL_FLUSH))
                .build();
    }

    private CombinationModifier getModifierWithCombCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addCombinationBound(c -> c.equals(CombinationType.TWO_PAIR))
                .build();
    }

    @Test
    void testCurrentCurrencyModifier() {
        final double multipler = INIT_MUL;
        final int basePoints = INIT_B_P;
        CombinationModifier m = getModifierWithCurrCondTrue();
        m.setGameStatus(getMockStatus());
        var bpMapper = m.getBasePointMapper();
        var mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(multipler + DELTA_MUL, mulMapper.get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, bpMapper.get().apply(basePoints));
        m = getModifierWithCombCurrFalse();
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    @Test
    void testMergedConditionalModifiers() {
        final double multipler = INIT_MUL;
        final int basePoints = INIT_B_P;
        // both with true conditions
        CombinationModifier m = getModifierFromTwoMerges(getModifierWithCombCondTrue(), getModifierWithHCardCondTrue());
        m.setGameStatus(getMockStatus());
        var bpMapper = m.getBasePointMapper();
        var mulMapper = m.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(basePoints + DELTA_B_P + DELTA_B_P, bpMapper.get().apply(INIT_B_P));
        assertEquals(multipler + DELTA_MUL + DELTA_MUL, mulMapper.get().apply(INIT_MUL));
        // first with false condition
        m = getModifierFromTwoMerges(getModifierWithCombCondFalse(), getModifierWithHCardCondTrue());
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
        // second with false condition
        m = getModifierFromTwoMerges(getModifierWithCombCondTrue(), getModifierWithHCardCondFalse());
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
        // both with false conditions
        m = getModifierFromTwoMerges(getModifierWithCombCondFalse(), getModifierWithHCardCondFalse());
        m.setGameStatus(getMockStatus());
        bpMapper = m.getBasePointMapper();
        mulMapper = m.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    private CombinationModifier getModifierFromTwoMerges(final CombinationModifier m1, final CombinationModifier m2) {
        return builder()
            .merge(m1)
            .merge(m2)
            .build();
    }

    @Test
    void testInconsistentState() {
        final CombinationModifier m = getModifierWithCombCondTrue();
        // without setting state
        assertThrows(IllegalStateException.class, m::getBasePointMapper);
        assertThrows(IllegalStateException.class, m::getMultiplierMapper);
        // setting state once and then forgetting to set
        m.setGameStatus(getMockStatus());
        assertDoesNotThrow(m::getBasePointMapper);
        assertDoesNotThrow(m::getMultiplierMapper);
        assertThrows(IllegalStateException.class, m::getBasePointMapper);
        assertThrows(IllegalStateException.class, m::getMultiplierMapper);
    }

    @Test
    void testBuilderFails() {
        // EmptyBuilder
        assertThrows(IllegalStateException.class, () -> builder().build());
        // Empty, only with condition
        assertThrows(IllegalStateException.class, () -> builder().addPlayedCardBound(c -> true).build());
    }

    private CombinationModifier getModifierWithCurrCondTrue() {
        return builder()
                .merge(getStandardModifier())
                .addCurrentCurrencyBound(c -> c < CURR_QTY_TRUE)
                .build();
    }

    private CombinationModifier getModifierWithCombCurrFalse() {
        return builder()
                .merge(getStandardModifier())
                .addCurrentCurrencyBound(c -> c < CURR_QTY_FALSE)
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

    private CombinationModifier getMergedModifier(final CombinationModifier base) {
        return builder()
                .merge(base)
                .addBasePointsModifier(p -> p + DELTA_B_P2)
                .addMultiplierModifier(mul -> mul * DELTA_MUL2)
                .build();
    }

    private ModifierStatsSupplier getMockStatus() {
        return new ModifierStatsSupplierBuilderImpl()
                .addCurrentCombination(Combination.CombinationType.TWO_PAIR)
                .addHoldingCards(getTestHoldingCards())
                .addPlayedCards(getTestPlayedCard())
                .addCurrentCurrency(CURRENT_CURRENCY)
                .build();
    }
}
