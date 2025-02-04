package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerFactory;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Implementation of {@link JokerFactory}.
 */
public final class JokerFactoryImpl implements JokerFactory {
    private static final int MAX_PRICE = 10;
    private static final int MIN_PRICE = 3;
    private final Random priceSupplier = new Random();

    @Override
    public Joker standardJoker(final String name, final String description) {
        return new JokerImpl(
            name,
            description,
            getRandomPrice(JokerTier.COMMON),
            null,
            JokerTier.COMMON);
    }

    @Override
    public Joker withModifier(
        final String name,
        final String description,
        final int basePrice,
        final CombinationModifier modifier,
        final JokerTier tier) {
        return new JokerImpl(name, description, basePrice, modifier, tier);
    }

    @Override
    public Joker withModifierAndRandomPrice(
        final String name,
        final String description,
        final CombinationModifier modifier,
        final JokerTier tier) {
        return new JokerImpl(name, description, getRandomPrice(tier), modifier, tier);
    }

    @Override
    public Joker addPlayableCardBoundToJoker(
            final String name,
            final String description,
            final Joker j,
            final Predicate<Set<PlayableCard>> bound,
            final JokerTier newTier) {
        return new JokerImpl(
                name,
                description,
                getRandomPrice(j.getTier()),
                new ModifierBuilderImpl()
                    .merge(j.getModifier().get())
                    .addPlayedCardBound(bound)
                    .build(),
                newTier
        );
    }

    @Override
    public Joker merge(final String newName, final String newDescription, final Joker j1, final Joker j2) {
        final var tier = Stream.of(j1.getTier(), j2.getTier())
                .max(((a, b) -> a.compareTo(b)))
                .get();
        return new JokerImpl(
            newName,
            newDescription,
            getRandomPrice(tier),
            new ModifierBuilderImpl()
                .merge(j1.getModifier().get())
                .merge(j2.getModifier().get())
                .build(),
                tier);
    }

    private int getRandomPrice(JokerTier tier) {
        return priceSupplier.nextInt(MIN_PRICE, MAX_PRICE);
    }
}
