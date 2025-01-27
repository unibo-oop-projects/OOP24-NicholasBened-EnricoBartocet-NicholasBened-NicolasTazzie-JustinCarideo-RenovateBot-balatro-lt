package it.unibo.balatrolt.model.impl.modifier;

import java.util.Objects;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

/**
 * A modifier which checks whether a condition is satisfied before suppling the
 * modifying functions.
 * If the game status is not set it will always return the modifier.
 *
 * @param <X> type of condition that should be satisfied.
 */
public abstract class ConditionalModifier<X> extends ModifierDecorator {
    private Optional<ModifierStatsSupplier> stats = Optional.absent();
    private final Predicate<X> condition;

    /**
     * @param condition to satisfy
     * @param modifier  base modifier
     */
    protected ConditionalModifier(final Modifier modifier, final Predicate<X> condition) {
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

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ConditionalModifier<X> other = (ConditionalModifier<X>) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        return true;
    }

    /**
     * @return current statistics
     */
    protected final Optional<ModifierStatsSupplier> getStats() {
        return this.stats;
    }

    @Override
    protected boolean canApply() {
        if (!this.stats.isPresent()) {
            return true;
        }
        return checkCondition();
    }

    /**
     * @return true if the condition is satisfied
     */
    protected abstract boolean checkCondition();
}
