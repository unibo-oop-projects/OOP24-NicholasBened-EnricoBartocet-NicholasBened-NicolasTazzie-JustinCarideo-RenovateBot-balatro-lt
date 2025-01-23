package it.unibo.balatrolt.model.impl.modifier;

import java.util.Objects;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.BasePoints;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.Multiplier;

/**
 * A decorator for modifier.
 * If canApply is false, then it returns an empty optional.
 */
public abstract class ModifierDecorator implements Modifier {
    private final Modifier base;

    /**
     * @param modifier base modifier
     */
    protected ModifierDecorator(final Modifier modifier) {
        this.base = Objects.requireNonNull(modifier, "Modifier cant't be null");
    }

    @Override
    public final Optional<UnaryOperator<Multiplier>> getMultiplierMapper() {
        return this.canApply() ? this.base.getMultiplierMapper() : Optional.absent();
    }

    @Override
    public final Optional<UnaryOperator<BasePoints>> getBasePointMapper() {
        return this.canApply() ? this.base.getBasePointMapper() : Optional.absent();
    }

    /**
     * @return whether the modifier can be applied or not 
     */
    protected abstract boolean canApply();
}
