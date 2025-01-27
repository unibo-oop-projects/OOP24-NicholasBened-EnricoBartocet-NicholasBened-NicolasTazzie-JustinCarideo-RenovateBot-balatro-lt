package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.Combination;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
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
    private static final int CURRENT_CURRENCY = 10;
    private ModifierBuilder builder;

    @BeforeEach
    void init() {
        this.builder = new ModifierBuilderImpl();
    }

    @Test
    void testBaseModifier() {
        Modifier m = this.builder.addBasePointsModifier(p -> p + DELTA_B_P).build();
        final int basePoints = INIT_B_P;
        final double multipler = INIT_MUL;
        // only basePoints
        assertTrue(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        // only multiplier
        init();
        m = this.builder.addMultiplierModifier(p -> p + DELTA_MUL).build();
        assertFalse(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        // both
        init();
        m = this.builder
                .addBasePointsModifier(p -> p + DELTA_B_P)
                .addMultiplierModifier(p -> p + DELTA_MUL)
                .build();
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
    }

    @Test
    void testPlayedCardModifier() {
        final int basePoints = INIT_B_P;
        Modifier m = getModifierWithHCardCondTrue();
        m.setGameStatus(getMockStatus());
        assertTrue(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
        init();
        m = getModifierWithHCardCondFalse();
        m.setGameStatus(getMockStatus());
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
    }

    @Test
    void testHoldingCardModifier() {
        //TODO: to finish
        // both
        final double multipler = INIT_MUL;
        final int basePoints = INIT_B_P;
        init();
        Modifier m = this.builder
                .addBasePointsModifier(p -> p + DELTA_B_P)
                .addMultiplierModifier(p -> p + DELTA_MUL)
                .build();
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + DELTA_MUL, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + DELTA_B_P, m.getBasePointMapper().get().apply(basePoints));
    }



    private Modifier getModifierWithHCardCondFalse() {
        return this.builder
                .addMultiplierModifier(mul -> mul * 2)
                .addPlayedCardBound(c -> c.stream()
                        .map(PlayableCard::getSuit)
                        .anyMatch(s -> s.equals(Suit.HEARTS)))
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
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES))
        );
    }

    private Modifier getModifierWithHCardCondTrue() {
        return this.builder
                .addBasePointsModifier(p -> p + 1)
                .addPlayedCardBound(c -> c.contains(
                        new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))))
                .build();
    }

    @Test
    void testMerge() {
        Modifier base = getModifierWithHCardCondTrue();
        Modifier modifier = getMergedModifier(base);
        final double mul = 1;
        final int baseP = 1;
        modifier.setGameStatus(getMockStatus());
        // validStatus
        assertTrue(modifier.getBasePointMapper().isPresent());
        assertTrue(modifier.getMultiplierMapper().isPresent());
        // It should be f -> g = f + 1 -> h = g + 2
        assertEquals(baseP + 1 + 2, modifier.getBasePointMapper().get().apply(baseP));
        assertEquals(mul + 2.5, modifier.getMultiplierMapper().get().apply(mul));
        init();
        base = getModifierWithHCardCondFalse();
        modifier = getMergedModifier(base);
        modifier.setGameStatus(getMockStatus());
        // invalid status
        assertFalse(modifier.getBasePointMapper().isPresent());
        assertFalse(modifier.getMultiplierMapper().isPresent());
    }

    private Modifier getMergedModifier(final Modifier base) {
        return this.builder
                .merge(base)
                .addBasePointsModifier(p -> p + 2)
                .addMultiplierModifier(mul -> mul + 2.5)
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
