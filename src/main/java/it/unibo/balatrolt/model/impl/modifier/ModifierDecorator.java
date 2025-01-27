package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

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
    protected ModifierDecorator(final Modifier modifier) {
        this.base = Preconditions.checkNotNull(modifier, "Modifier cant't be null");
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

    /**
     * @return whether the modifier can be applied or not
     */
    protected abstract boolean canApply();
}
