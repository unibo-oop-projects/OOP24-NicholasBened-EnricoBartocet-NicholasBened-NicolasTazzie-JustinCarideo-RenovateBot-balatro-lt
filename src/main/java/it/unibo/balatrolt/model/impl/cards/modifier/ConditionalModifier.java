package it.unibo.balatrolt.model.impl.cards.modifier;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * A modifier which checks whether a condition is satisfied before giving the
 * modifying functions.
 * If the game status is not set it will always return the modifier.
 * @author Nicolas Tazzieri
 *
 * @param <X> type of condition that should be satisfied.
 */
public abstract class ConditionalModifier<X> extends ModifierDecorator {
    private final Predicate<X> condition;

    /**
     * @param condition to satisfy
     * @param modifier  base modifier
     */
    protected ConditionalModifier(final CombinationModifier modifier, final Predicate<X> condition) {
        super(modifier);
        this.condition = checkNotNull(condition, "Condition can't be null");
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
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConditionalModifier<X> other = (ConditionalModifier<X>) obj;
        return Objects.equals(other.condition, this.condition)
        && super.equals(other);
    }

    @Override
    protected final boolean canApplySingle() {
        return this.checkCondition();
    }

    @Override
    protected final Optional<UnaryOperator<Integer>> getInnerBasePointsFun() {
        return Optional.absent();
    }

    @Override
    protected final Optional<UnaryOperator<Double>> getInnerMultiplierFun() {
        return Optional.absent();
    }

    @Override
    public final String toString() {
        return "ConditionalModifier [condition=" + condition + "]";
    }

    /**
     * @return true if the condition is satisfied
     */
    protected abstract boolean checkCondition();
}
