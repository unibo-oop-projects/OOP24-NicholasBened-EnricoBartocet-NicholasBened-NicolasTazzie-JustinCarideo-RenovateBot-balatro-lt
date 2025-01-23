package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.BasePoint;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.Multiplier;

/**
 * A basic modifier. It returns the multiplier and the basepoints if are present.
 * It can contain one of them or both depending on how is constructed.
 * modification without checking any condition and doesn't need any status
 */
public final class BaseModifier implements Modifier {
    private final Optional<UnaryOperator<Multiplier>> multiplierMod;
    private final Optional<UnaryOperator<BasePoint>> basePointMod;

    /**
     * @param multiplierMod UnaryOperator that modifies the multiplier
     * @param basePointMod UnaryOperator that modifies the basePonit
     */
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
    public Optional getBasePointMapper() {
        return this.basePointMod;
    }

    @Override
    public void setGameStatus(final ModifierStatsSupplier stats) {
        // it doesn't set any game status since it's not required.
    }

}
