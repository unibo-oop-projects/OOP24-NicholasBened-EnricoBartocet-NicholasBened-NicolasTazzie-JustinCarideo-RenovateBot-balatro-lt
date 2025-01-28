package it.unibo.balatrolt.model.impl.modifier;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

/**
 * A decorator for modifier.
 * If canApply is false, then it returns an empty optional.
 */
public abstract class ModifierDecorator implements Modifier {
    private Optional<ModifierStatsSupplier> stats = Optional.absent();
    private final Modifier base;

    /**
     * @param modifier base modifier
     */
    public ModifierDecorator(final Modifier modifier) {
        this.base = checkNotNull(modifier, "Modifier can't be null");
    }

    @Override
    public Optional<UnaryOperator<Double>> getMultiplierMapper() {
        return this.canApply() ? this.base.getMultiplierMapper() : Optional.absent();
    }

    @Override
    public Optional<UnaryOperator<Integer>> getBasePointMapper() {
        return this.canApply() ? this.base.getBasePointMapper() : Optional.absent();
    }

    @Override
    public void setGameStatus(final ModifierStatsSupplier stats) {
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
     * @return whether the modifier can be applied or not
     */
    protected abstract boolean canApplySingle();
}
