package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.BasePoint;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.Multiplier;

public class BaseModifier implements Modifier {
    private final Optional<UnaryOperator<Multiplier>> multiplierMod;
    private final Optional<UnaryOperator<BasePoint>> basePointMod;

    public BaseModifier(final Optional<UnaryOperator<Multiplier>> multiplierMod,
            final Optional<UnaryOperator<BasePoint>> basePointMod) {
        this.multiplierMod = multiplierMod;
        this.basePointMod = basePointMod;
    }

    @Override
    public Optional<UnaryOperator<Multiplier>> getMultiplierMapper() {
        return this.multiplierMod;
    }

    @Override
    public Optional<UnaryOperator<BasePoint>> getBasePointMapper() {
        return this.basePointMod;
    }

}
