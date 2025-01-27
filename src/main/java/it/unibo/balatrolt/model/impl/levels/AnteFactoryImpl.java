package it.unibo.balatrolt.model.impl.levels;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.AnteFactory;
import it.unibo.balatrolt.model.api.levels.Blind;

/**
 * A factory for {@link Ante} objects.
 * Every Ante created using this factory has the same number of Blinds.
 * @author Enrico Bartocetti
 */
public final class AnteFactoryImpl implements AnteFactory {

    private final int numBlinds;
    private final BinaryOperator<Integer> baseChipCalc;
    private final UnaryOperator<Integer> rewardCalc;

    /**
     * Initialize the factory specifying the number of blinds and the functions needed for the Blinds.
     * The number of the Blinds of each Ante must be a positive value.
     * @param numBlinds the number of {@link Blind} present in the Ante
     * @param baseChipCalc the function that computes the minimum chips required to defeat the Blind, given the Ante and Blind IDs
     * @param rewardCalc the function that computes the reward given when defeating the Blind, given the Blind ID
     */
    public AnteFactoryImpl(
        final int numBlinds,
        final BinaryOperator<Integer> baseChipCalc,
        final UnaryOperator<Integer> rewardCalc
    ) {
        Preconditions.checkArgument(numBlinds > 0, "The number of blinds must be positive");
        this.numBlinds = numBlinds;
        this.baseChipCalc = Preconditions.checkNotNull(baseChipCalc);
        this.rewardCalc = Preconditions.checkNotNull(rewardCalc);
    }

    @Override
    public Ante fromId(final int id) {
        return new AnteImpl(new AnteConfiguration(id, numBlinds, baseChipCalc, rewardCalc));
    }

    @Override
    public List<Ante> generateList(final int size) {
        return Stream.iterate(0, i -> i + 1)
            .limit(size)
            .map(this::fromId)
            .toList();
    }
}
