package it.unibo.balatrolt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Currency;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;

public class TestModifier {
    ModifierBuilder builder;

    @BeforeEach
    public void init() {
        this.builder = new ModifierBuilderImpl();
    }

    @Test
    public void testBaseModifier() {
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
    public void testCardPredicateModifier() {
        final int basePoints = 1;
        // double multipler = 1;
        Modifier m = this.builder
            .addBasePointsModifier(p -> p + 1)
            .addPlayedCardBound(c -> c.contains(
                new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))
            ))
            .build();
        final Set<PlayableCard> cards = Set.of(
            new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.FOUR, Suit.DIAMONDS)),
            new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES)),
            new PlayableCardImpl(new Pair<>(Rank.KING, Suit.CLUBS)),
            new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.DIAMONDS))
        );
        assertTrue(cards.contains(new PlayableCardImpl(new Pair<>(Rank.FIVE, Suit.CLUBS))));
        m.setGameStatus(getStatusByCards(cards));
        assertTrue(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
        assertEquals(basePoints + 1, m.getBasePointMapper().get().apply(basePoints));
        init();
        m = this.builder
            .addMultiplierModifier(mul -> mul * 2)
            .addPlayedCardBound(c -> c.stream()
                .map(e -> e.getSuit())
                .anyMatch(s -> s.equals(Suit.HEARTS)))
            .build();
        m.setGameStatus(getStatusByCards(cards));
        assertFalse(m.getBasePointMapper().isPresent());
        assertFalse(m.getMultiplierMapper().isPresent());
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
