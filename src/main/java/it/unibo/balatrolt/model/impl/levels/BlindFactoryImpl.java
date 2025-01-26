package it.unibo.balatrolt.model.impl.levels;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindFactory;

/**
 * A factory for {@link Blind} objects.
 * @author Bartocetti Enrico
 */
public class BlindFactoryImpl implements BlindFactory {

    private final BiFunction<Integer, Integer, Integer> baseChipsCalculator;
    private final Function<Integer, Integer> rewardCalculator;

    /**
     * Initialize the factory using the strategy pattern to compute the base chips and the reward of the blinds.
     * @param chips a bifunction that takes the ante id and the blind id, and gives the base chips
     * @param reward a function that takes the blind id to compute the reward
     */
    public BlindFactoryImpl(final BiFunction<Integer, Integer, Integer> chips, final Function<Integer, Integer> reward) {
        Objects.requireNonNull(chips);
        Objects.requireNonNull(reward);
        this.baseChipsCalculator = chips;
        this.rewardCalculator = reward;
    }

    @Override
    public Blind fromIds(final int anteId, final int blindId) {
        return new BlindImpl(new BlindConfiguration(blindId, baseChipsCalculator.apply(anteId, blindId), rewardCalculator.apply(blindId)));
    }

    @Override
    public List<Blind> createList(final int numBlinds, final int anteId) {
        if (numBlinds <= 0) {
            throw new IllegalArgumentException();
        }
        return Stream.iterate(0, i -> i + 1).limit(numBlinds).map(n -> this.fromIds(anteId, n)).toList();
    }
}
