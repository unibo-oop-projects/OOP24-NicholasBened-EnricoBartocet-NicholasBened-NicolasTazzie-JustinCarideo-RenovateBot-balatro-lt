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
    private static final double DOUBLER_MUL = 2;
    private static final int DONOUR_BP = 50;
    private static final double INIT_MUL = 1;
    private static final int INIT_BP = 0;
    private static final int CURRENT_CURRENCY = 10;
    private final JokerSupplierImpl js = new JokerSupplierImpl();

    @Test
    void testTheDoubler() {
        final Joker j = js.doubler();
        final double m = INIT_MUL;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        final Modifier mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertTrue(mod.getMultiplierMapper().isPresent());
        assertEquals(m * DOUBLER_MUL, mod.getMultiplierMapper().get().apply(m));
    }

    @Test
    void testTheDoublerWithCondition() {
        Joker j = js.diamondDoubler(); // diamond is present
        final double m = INIT_MUL;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        Modifier mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertTrue(mod.getMultiplierMapper().isPresent());
        assertEquals(m * DOUBLER_MUL, mod.getMultiplierMapper().get().apply(m));
        j = js.heartDoubler(); // heart is not present
        assertTrue(j.getModifier().isPresent());
        mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
    }

    @Test
    void testTheDonour() {
        final Joker j = js.donour();
        final int bp = INIT_BP;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        final Modifier mod = getMod(j, stats);
        assertTrue(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
        assertEquals(bp + DONOUR_BP, mod.getBasePointMapper().get().apply(bp));
    }

    @Test
    void testTheDonourWithCondition() {
        Joker j = js.kingDonour(); // king is present
        final int bp = INIT_BP;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        Modifier mod = getMod(j, stats);
        assertTrue(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
        assertEquals(bp + DONOUR_BP, mod.getBasePointMapper().get().apply(bp));
        j = js.seventhDonour(); // seven is not present
        mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
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
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.SPADES))
        );
    }
}
