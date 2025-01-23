package it.unibo.balatrolt.model.impl.modifier;

import java.util.Objects;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.BasePoint;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.Multiplier;

public abstract class ModifierDecorator implements Modifier {
    protected final Modifier base;

    protected ModifierDecorator(Modifier modifier) {
        this.base = Objects.requireNonNull(modifier, "Modifier cant't be null");
    }

    @Override
    public Optional<UnaryOperator<Multiplier>> getMultiplierMapper() {
        return this.base.getMultiplierMapper();
    }

    @Override
    public Optional<UnaryOperator<BasePoint>> getBasePointMapper() {
        return this.base.getBasePointMapper();
    }

    protected abstract boolean canApply();
}
