package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;

class TestModifier {
    private ModifierBuilder builder;

    @BeforeEach
    void init() {
        this.builder = new ModifierBuilderImpl();
    }

    @Test
    void testBaseModifier() {
        Modifier m = this.builder.addBasePointsModifier(p -> p + 1).build();
        final int basePoints = 1;
        final double multipler = 1;
        // only basePoints
        assertTrue(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + 1, m.getBasePointMapper().get().apply(basePoints));
        // only multiplier
        init();
        m = this.builder.addMultiplierModifier(p -> p + 0.5).build();
        assertFalse(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + 0.5, m.getMultiplierMapper().get().apply(multipler));
        // both
        init();
        m = this.builder
                .addBasePointsModifier(p -> p + 2)
                .addMultiplierModifier(p -> p + 1.5)
                .build();
        assertTrue(m.getBasePointMapper().isPresent());
        assertTrue(m.getMultiplierMapper().isPresent());
        assertEquals(multipler + 1.5, m.getMultiplierMapper().get().apply(multipler));
        assertEquals(basePoints + 2, m.getBasePointMapper().get().apply(basePoints));
    }

    @Test
    void testCardPredicateModifier() {
        final int basePoints = 1;
        Modifier m = getModifierWithHCardCondTrue();
        final Set<PlayableCard> cards = getTestCards();
        assertTrue(cards.contains(new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))));
        m.setGameStatus(getStatusByCards(cards));
        assertTrue(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + 1, m.getBasePointMapper().get().apply(basePoints));
        init();
        m = getModifierWithHCardCondFalse();
        m.setGameStatus(getStatusByCards(cards));
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
    }

    private Modifier getModifierWithHCardCondFalse() {
        return this.builder
                .addMultiplierModifier(mul -> mul * 2)
                .addPlayedCardBound(c -> c.stream()
                        .map(PlayableCard::getSuit)
                        .anyMatch(s -> s.equals(Suit.HEARTS)))
                .build();
    }

    private Set<PlayableCard> getTestCards() {
        return Set.of(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.FOUR, Suit.DIAMONDS)),
                new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES)),
                new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
                new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.DIAMONDS)));
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
        modifier.setGameStatus(getStatusByCards(getTestCards()));
        // validStatus
        assertTrue(modifier.getBasePointMapper().isPresent());
        assertTrue(modifier.getMultiplierMapper().isPresent());
        // It should be f -> g = f + 1 -> h = g + 2
        assertEquals(baseP + 1 + 2, modifier.getBasePointMapper().get().apply(baseP));
        assertEquals(mul + 2.5, modifier.getMultiplierMapper().get().apply(mul));
        init();
        base = getModifierWithHCardCondFalse();
        modifier = getMergedModifier(base);
        modifier.setGameStatus(getStatusByCards(getTestCards()));
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
            public Optional<Integer> getCurrentCurrency() {
                return Optional.absent();
            }

            @Override
            public Optional<CombinationType> getCurrentCombinationType() {
                return Optional.of(CombinationType.TWOPAIR);
            }

        };
    }
}
