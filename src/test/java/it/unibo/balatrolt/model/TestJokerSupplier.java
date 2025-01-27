package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Currency;
import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.specialcard.JokerSupplierImpl;

class TestJokerSupplier {
    private final JokerSupplierImpl js = new JokerSupplierImpl();

    private Set<PlayableCard> getHandCards() {
        return Set.of(
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.FOUR, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES)),
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.DIAMONDS))
        );
    }

    @Test
    void testTheDoubler() {
        final Joker j = js.doubler();
        final double m = 1;
        final ModifierStatsSupplier stats = getStatusByCards(getHandCards());
        assertTrue(j.getModifier().isPresent());
        final Modifier mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertTrue(mod.getMultiplierMapper().isPresent());
        assertEquals(m * 2, mod.getMultiplierMapper().get().apply(m));
    }

    @Test
    void testTheDoublerWithCondition() {
        Joker j = js.diamondDoubler(); // diamond is present
        final double m = 1;
        final ModifierStatsSupplier stats = getStatusByCards(getHandCards());
        assertTrue(j.getModifier().isPresent());
        Modifier mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertTrue(mod.getMultiplierMapper().isPresent());
        assertEquals(m * 2, mod.getMultiplierMapper().get().apply(m));
        j = js.heartDoubler(); // heart is not present
        assertTrue(j.getModifier().isPresent());
        mod = getMod(j, stats);
        assertFalse(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
    }

    @Test
    void testTheDonour() {
        final Joker j = js.donour();
        final int bp = 0;
        final ModifierStatsSupplier stats = getStatusByCards(getHandCards());
        assertTrue(j.getModifier().isPresent());
        final Modifier mod = getMod(j, stats);
        assertTrue(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
        assertEquals(bp + 50, mod.getBasePointMapper().get().apply(bp));
    }

    @Test
    void testTheDonourWithCondition() {
        Joker j = js.kingDonour(); // king is present
        final int bp = 0;
        final ModifierStatsSupplier stats = getStatusByCards(getHandCards());
        assertTrue(j.getModifier().isPresent());
        Modifier mod = getMod(j, stats);
        assertTrue(mod.getBasePointMapper().isPresent());
        assertFalse(mod.getMultiplierMapper().isPresent());
        assertEquals(bp + 50, mod.getBasePointMapper().get().apply(bp));
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

    private ModifierStatsSupplier getStatusByCards(final Set<PlayableCard> cards) {
        return new ModifierStatsSupplier() {

            @Override
            public Optional<Set<PlayableCard>> getHoldingCards() {
                return Optional.of(cards);
            }

            @Override
            public Optional<Set<PlayableCard>> getPlayedCards() {
                return Optional.of(cards);

            }

            @Override
            public Optional<Currency> getCurrentCurrency() {
                return Optional.absent();
            }
        };
    }
}
