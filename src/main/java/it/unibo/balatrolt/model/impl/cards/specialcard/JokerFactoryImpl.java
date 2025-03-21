package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerFactory;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Implementation of {@link JokerFactory}.
 * @author Nicolas Tazzieri
 */
public final class JokerFactoryImpl implements JokerFactory {
    private static final int MAX_PRICE = 10;
    private static final int MIN_PRICE = 3;
    private final Random priceSupplier = new Random();

    @Override
    public Joker standardJoker(final String name, final String description) {
        return new JokerImpl(
            name.toLowerCase(Locale.getDefault()),
            description.toLowerCase(Locale.getDefault()),
            getRandomPrice(),
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
        return new JokerImpl(
            name.toLowerCase(Locale.getDefault()),
            description.toLowerCase(Locale.getDefault()),
            basePrice,
            modifier,
            tier);
    }

    @Override
    public Joker withModifierAndRandomPrice(
        final String name,
        final String description,
        final CombinationModifier modifier,
        final JokerTier tier) {
        return new JokerImpl(
            name.toLowerCase(Locale.getDefault()),
            description.toLowerCase(Locale.getDefault()),
            getRandomPrice(),
            modifier,
            tier);
    }

    @Override
    public Joker addPlayedCardBoundToJoker(
            final String name,
            final String description,
            final Joker j,
            final Predicate<Set<PlayableCard>> bound,
            final JokerTier newTier) {
        return new JokerImpl(
                name.toLowerCase(Locale.getDefault()),
                description.toLowerCase(Locale.getDefault()),
                getRandomPrice(),
                new ModifierBuilderImpl()
                    .merge(j.getModifier().get())
                    .addPlayedCardBound(bound)
                    .build(),
                newTier
        );
    }

    @Override
    public Joker merge(final String newName, final String newDescription, final Joker j1, final Joker j2, final JokerTier tier) {
        return new JokerImpl(
            newName.toLowerCase(Locale.getDefault()),
            newDescription.toLowerCase(Locale.getDefault()),
            getRandomPrice(),
            new ModifierBuilderImpl()
                .merge(j1.getModifier().get())
                .merge(j2.getModifier().get())
                .build(),
                tier);
    }

    private int getRandomPrice() {
        return priceSupplier.nextInt(MIN_PRICE, MAX_PRICE);
    }

    @Override
    public Joker addHoldingCardBoundToJoker(final String newName, final String newDescription, final Joker j,
            final Predicate<Set<PlayableCard>> bound, final JokerTier newTier) {
                return new JokerImpl(
                    newName.toLowerCase(Locale.getDefault()),
                    newDescription.toLowerCase(Locale.getDefault()),
                    getRandomPrice(),
                    new ModifierBuilderImpl()
                        .merge(j.getModifier().get())
                        .addHoldingCardBound(bound)
                        .build(),
                    newTier
            );
    }

    @Override
    public Joker addMoneyBoundToJoker(
        final String newName,
        final String newDescription,
        final Joker j,
        final Predicate<Integer> bound,
        final JokerTier newTier) {
            return new JokerImpl(
                newName.toLowerCase(Locale.getDefault()),
                newDescription.toLowerCase(Locale.getDefault()),
                getRandomPrice(),
                new ModifierBuilderImpl()
                    .merge(j.getModifier().get())
                    .addCurrentCurrencyBound(bound)
                    .build(),
                newTier
        );
    }

    @Override
    public Joker addCombinationCardBoundToJoker(final String newName, final String newDescription, final Joker j,
            final Predicate<CombinationType> bound, final JokerTier newTier) {
                return new JokerImpl(
                    newName.toLowerCase(Locale.getDefault()),
                    newDescription.toLowerCase(Locale.getDefault()),
                    getRandomPrice(),
                    new ModifierBuilderImpl()
                        .merge(j.getModifier().get())
                        .addCombinationBound(bound)
                        .build(),
                    newTier
            );
    }
}
