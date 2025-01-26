package it.unibo.balatrolt.model.impl.specialcard;

import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerFactory;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;

/**
 * Implementation of {@link JokerFactory}.
 */
public final class JokerFactoryImpl implements JokerFactory {
    private static final int MAX_PRICE = 10;
    private static final int MIN_PRICE = 3;
    private final Random priceSupplier = new Random();

    @Override
    public Joker standardJoker(final String name, final String description) {
        return new JokerImpl(name, description, getRandomPrice(), null);
    }

    @Override
    public Joker withModifier(final String name, final String description, final int basePrice,
            final Modifier modifier) {
        return new JokerImpl(name, description, basePrice, modifier);
    }

    @Override
    public Joker withModifierAndRandomPrice(final String name, final String description, final Modifier modifier) {
        return new JokerImpl(name, description, getRandomPrice(), modifier);
    }

    @Override
    public Joker addPlayableCardBoundToJoker(
            final String name,
            final String description,
            final Joker j,
            final Predicate<Set<PlayableCard>> bound) {
        return new JokerImpl(
                name,
                description,
                getRandomPrice(),
                new ModifierBuilderImpl()
                    .merge(j.getModifier().get())
                    .addPlayedCardBound(bound)
                    .build()
        );
    }

    @Override
    public Joker merge(final String newName, final String newDescription, final Joker j1, final Joker j2) {
        Modifier newModifier = new ModifierBuilderImpl()
            .merge(j1.getModifier().get())
            .build();
        return new JokerImpl(
            newName,
            newDescription,
            getRandomPrice(),
            new ModifierBuilderImpl()
                .merge(newModifier)
                .build());
    }

    private int getRandomPrice() {
        return priceSupplier.nextInt(MIN_PRICE, MAX_PRICE);
    }
}
