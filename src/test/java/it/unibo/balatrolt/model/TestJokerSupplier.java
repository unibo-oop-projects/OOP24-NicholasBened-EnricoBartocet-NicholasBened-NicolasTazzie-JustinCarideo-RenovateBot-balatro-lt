package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.modifier.ModifierStatsSupplierBuilderImpl;
import it.unibo.balatrolt.model.impl.specialcard.JokerSupplierImpl;

final class TestJokerSupplier {
    private static final int DOUBLER_INDEX = 0;
    private static final int DIAMOND_DOUBLER_INDEX = 1;
    private static final int DONOUR_INDEX = 2;
    private static final int KING_DONOUR_INDEX = 3;
    private static final int HEART_DOUBLER_INDEX = 4;
    private static final int SEVENTH_DONOUR_INDEX = 5;
    private static final double DOUBLER_MUL = 2;
    private static final int DONOUR_BP = 50;
    private static final double INIT_MUL = 1;
    private static final int INIT_BP = 0;
    private static final int CURRENT_CURRENCY = 10;
    private final JokerSupplierImpl js = new JokerSupplierImpl();

    @Test
    void testTheDoubler() {
        final Joker j = js.getJokerList().get(DOUBLER_INDEX);
        final double m = INIT_MUL;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        final Modifier mod = getMod(j, stats);
        var bp_mapper = mod.getBasePointMapper();
        var mul_mapper = mod.getMultiplierMapper();
        assertFalse(bp_mapper.isPresent());
        assertTrue(mul_mapper.isPresent());
        assertEquals(m * DOUBLER_MUL, mul_mapper.get().apply(m));
    }

    @Test
    void testTheDoublerWithCondition() {
        Joker j = js.getJokerList().get(DIAMOND_DOUBLER_INDEX); // diamond is present
        final double m = INIT_MUL;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        Modifier mod = getMod(j, stats);
        var bp_mapper = mod.getBasePointMapper();
        var mul_mapper = mod.getMultiplierMapper();
        assertFalse(bp_mapper.isPresent());
        assertTrue(mul_mapper.isPresent());
        assertEquals(m * DOUBLER_MUL, mul_mapper.get().apply(m));
        j = js.getJokerList().get(HEART_DOUBLER_INDEX); // heart is not present
        assertTrue(j.getModifier().isPresent());
        mod = getMod(j, stats);
        bp_mapper = mod.getBasePointMapper();
        mul_mapper = mod.getMultiplierMapper();
        assertFalse(bp_mapper.isPresent());
        assertFalse(mul_mapper.isPresent());
    }

    @Test
    void testTheDonour() {
        final Joker j = js.getJokerList().get(DONOUR_INDEX);
        final int bp = INIT_BP;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        final Modifier mod = getMod(j, stats);
        var bp_mapper = mod.getBasePointMapper();
        var mul_mapper = mod.getMultiplierMapper();
        assertTrue(bp_mapper.isPresent());
        assertFalse(mul_mapper.isPresent());
        assertEquals(bp + DONOUR_BP, bp_mapper.get().apply(bp));
    }

    @Test
    void testTheDonourWithCondition() {
        Joker j = js.getJokerList().get(KING_DONOUR_INDEX); // king is present
        final int bp = INIT_BP;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        Modifier mod = getMod(j, stats);
        var bp_mapper = mod.getBasePointMapper();
        var mul_mapper = mod.getMultiplierMapper();
        assertTrue(bp_mapper.isPresent());
        assertFalse(mul_mapper.isPresent());
        assertEquals(bp + DONOUR_BP, bp_mapper.get().apply(bp));
        j = js.getJokerList().get(SEVENTH_DONOUR_INDEX); // seven is not present
        mod = getMod(j, stats);
        bp_mapper = mod.getBasePointMapper();
        mul_mapper = mod.getMultiplierMapper();
        assertFalse(bp_mapper.isPresent());
        assertFalse(mul_mapper.isPresent());
    }

    private Modifier getMod(final Joker j, final ModifierStatsSupplier stats) {
        final Modifier mod = j.getModifier().get();
        mod.setGameStatus(stats);
        return mod;
    }

    private ModifierStatsSupplier getMockStatus() {
        return new ModifierStatsSupplierBuilderImpl()
                .addCurrentCombination(Combination.CombinationType.TWO_PAIR)
                .addHoldingCards(getTestHoldingCards())
                .addPlayedCards(getTestPlayedCard())
                .addCurrentCurrency(CURRENT_CURRENCY)
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
}
