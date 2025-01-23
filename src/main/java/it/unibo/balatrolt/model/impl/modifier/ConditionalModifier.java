package it.unibo.balatrolt.model.impl.modifier;

import java.util.Objects;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

/**
 * A modifier which checks whether a condition is satisfied before suppling the modifying functions.
 * If the game status is not set it will always return the modifier.
 * @param <X> type of condition that should be satisfied.
 */
public abstract class ConditionalModifier<X> extends ModifierDecorator {
    private Optional<ModifierStatsSupplier> stats = Optional.absent();
    private final Predicate<X> condition;

    /**
     * @param condition to satisfy
     * @param modifier base modifier
     */
    protected ConditionalModifier(final Predicate<X> condition, final Modifier modifier) {
        super(modifier);
        this.condition = Objects.requireNonNull(condition, "Condition can't be null");
    }

    @Override
    public final void setGameStatus(final ModifierStatsSupplier stats) {
        this.stats = Optional.of(stats);
    }

    /**
     * @return current condition
     */
    protected final Predicate<X> getCondition() {
        return this.condition;
    }

    /**
     * @return current statistics
     */
    protected final Optional<ModifierStatsSupplier> getStats() {
        return this.stats;
    }
}
