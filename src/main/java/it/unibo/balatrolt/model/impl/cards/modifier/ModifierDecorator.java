package it.unibo.balatrolt.model.impl.cards.modifier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierStatsSupplier;

/**
 * A decorator for modifier.
 * If canApply is false, then it returns an empty optional.
 * @author Nicolas Tazzieri
 */
public abstract class ModifierDecorator implements CombinationModifier {
    private Optional<ModifierStatsSupplier> stats = Optional.absent();
    private final CombinationModifier base;
    private boolean multiplierMapperReady;
    private boolean basePointsMapperReady;

    /**
     * @param modifier base modifier
     */
    public ModifierDecorator(final CombinationModifier modifier) {
        this.base = checkNotNull(modifier, "Modifier can't be null");
    }

    @Override
    public final Optional<UnaryOperator<Double>> getMultiplierMapper() {
        checkState(this.multiplierMapperReady, "Inconsistent state: game status should"
        + " be updated before getting the multiplier mapper");
        this.multiplierMapperReady = false;
        return this.canApply()
            ? mergeOperatorsMapper(this.base.getMultiplierMapper(), this.getInnerMultiplierFun())
            : Optional.absent();
    }

    @Override
    public final Optional<UnaryOperator<Integer>> getBasePointMapper() {
        checkState(this.basePointsMapperReady, "Inconsistent state: game status should"
        + " be updated before getting the basePoints mapper");
        this.basePointsMapperReady = false;
        return this.canApply()
            ? mergeOperatorsMapper(this.base.getBasePointMapper(), this.getInnerBasePointsFun())
            : Optional.absent();
    }

    @Override
    public final void setGameStatus(final ModifierStatsSupplier stats) {
        this.basePointsMapperReady = true;
        this.multiplierMapperReady = true;
        base.setGameStatus(stats);
        this.stats = Optional.fromNullable(stats);
    }

    /**
     * @return current statistics
     */
    protected final Optional<ModifierStatsSupplier> getStats() {
        return this.stats;
    }

    @Override
    public final boolean canApply() {
        return this.canApplySingle() && base.canApply();
    }

    /**
     * Utility method to merge two optional UnaryOperators.
     * @param <X> operator type
     * @param m1 first operator
     * @param m2 second operator
     * @return merged operator, absent if both are empty
     */
    protected static <X> Optional<UnaryOperator<X>> mergeOperatorsMapper(
            final Optional<UnaryOperator<X>> m1,
            final Optional<UnaryOperator<X>> m2) {
        if (m1.isPresent() && m2.isPresent()) {
            final var composed = m1.get().andThen(m2.get());
            return Optional.of(composed::apply);
        }
        if (m1.isPresent()) {
            return m1;
        }
        if (m2.isPresent()) {
            return m2;
        }
        return Optional.absent();
    }

    /**
     * @return innerMultiplierFunction to merge with base
     */
    protected abstract Optional<UnaryOperator<Double>> getInnerMultiplierFun();

    /**
     * @return innerBasePointFunction to merge with base
     */
    protected abstract Optional<UnaryOperator<Integer>> getInnerBasePointsFun();

    /**
     * @return whether the modifier can be applied or not
     */
    protected abstract boolean canApplySingle();

}
