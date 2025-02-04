package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierStatsSupplierBuilderImpl;
import it.unibo.balatrolt.model.impl.cards.specialcard.JokerCatalogBase;
import it.unibo.balatrolt.model.impl.cards.specialcard.JokerCatalogMisc;

final class TestJokerCatalog {
    private static final String SEVENTH_DONOUR = "the seventh donour";
    private static final String KING_DONOUR = "the king donour";
    private static final String DONOUR = "the donour";
    private static final String HEART_DOUBLER = "the heart doubler";
    private static final String DOUBLER = "the doubler";
    private static final String DIAMOND_DOUBLER = "the diamond doubler";
    private static final double DOUBLER_MUL = 2;
    private static final int DONOUR_BP = 50;
    private static final double INIT_MUL = 1;
    private static final int INIT_BP = 0;
    private static final int CURRENT_CURRENCY = 10;
    private final JokerCatalog base = new JokerCatalogBase();
    private final JokerCatalog misc = new JokerCatalogMisc();

    @Test
    void testTheDoubler() {
        assertTrue(this.base.getJoker(DOUBLER).isPresent());
        final Joker j = this.base.getJoker(DOUBLER).get();
        final double m = INIT_MUL;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        final CombinationModifier mod = getMod(j, stats);
        final var bpMapper = mod.getBasePointMapper();
        final var mulMapper = mod.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(m * DOUBLER_MUL, mulMapper.get().apply(m));
    }

    @Test
    void testTheDoublerWithCondition() {
        assertTrue(this.misc.getJoker(DIAMOND_DOUBLER).isPresent());
        Joker j = this.misc.getJoker(DIAMOND_DOUBLER).get(); // diamond is present
        final double m = INIT_MUL;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        CombinationModifier mod = getMod(j, stats);
        var bpMapper = mod.getBasePointMapper();
        var mulMapper = mod.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertTrue(mulMapper.isPresent());
        assertEquals(m * DOUBLER_MUL, mulMapper.get().apply(m));
        assertTrue(this.misc.getJoker(HEART_DOUBLER).isPresent());
        j = this.misc.getJoker(HEART_DOUBLER).get(); // heart is not present
        assertTrue(j.getModifier().isPresent());
        mod = getMod(j, stats);
        bpMapper = mod.getBasePointMapper();
        mulMapper = mod.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    @Test
    void testTheDonour() {
        assertTrue(base.getJoker(DONOUR).isPresent());
        final Joker j = base.getJoker(DONOUR).get();
        final int bp = INIT_BP;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        final CombinationModifier mod = getMod(j, stats);
        final var bpMapper = mod.getBasePointMapper();
        final var mulMapper = mod.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
        assertEquals(bp + DONOUR_BP, bpMapper.get().apply(bp));
    }

    @Test
    void testTheDonourWithCondition() {
        assertTrue(this.misc.getJoker(KING_DONOUR).isPresent());
        Joker j = this.misc.getJoker(KING_DONOUR).get(); // king is present
        final int bp = INIT_BP;
        final ModifierStatsSupplier stats = getMockStatus();
        assertTrue(j.getModifier().isPresent());
        CombinationModifier mod = getMod(j, stats);
        var bpMapper = mod.getBasePointMapper();
        var mulMapper = mod.getMultiplierMapper();
        assertTrue(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
        assertEquals(bp + DONOUR_BP, bpMapper.get().apply(bp));
        assertTrue(this.misc.getJoker(SEVENTH_DONOUR).isPresent());
        j = this.misc.getJoker(SEVENTH_DONOUR).get(); // seven is not present
        mod = getMod(j, stats);
        bpMapper = mod.getBasePointMapper();
        mulMapper = mod.getMultiplierMapper();
        assertFalse(bpMapper.isPresent());
        assertFalse(mulMapper.isPresent());
    }

    private CombinationModifier getMod(final Joker j, final ModifierStatsSupplier stats) {
        final CombinationModifier mod = j.getModifier().get();
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
