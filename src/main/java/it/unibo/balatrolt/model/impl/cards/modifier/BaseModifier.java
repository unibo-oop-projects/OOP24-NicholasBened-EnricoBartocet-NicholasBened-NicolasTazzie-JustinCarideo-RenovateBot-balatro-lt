package it.unibo.balatrolt.model.impl.cards.modifier;

import java.util.Objects;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.modifier.ModifierStatsSupplier;

/**
 * A basic modifier.
 * It returns the multiplier and the basepoints if are present.
 * It can contain one of them or both depending on how is constructed.
 * It doesn't need any status and doesn't check any condition.
 * @author Nicolas Tazzieri
 */
public final class BaseModifier implements CombinationModifier {
    private final Optional<UnaryOperator<Double>> multiplierMod;
    private final Optional<UnaryOperator<Integer>> basePointMod;

    /**
     * @param multiplierMod UnaryOperator that modifies the multiplier
     * @param basePointMod UnaryOperator that modifies the basePonit
     */
    public BaseModifier(final Optional<UnaryOperator<Double>> multiplierMod,
            final Optional<UnaryOperator<Integer>> basePointMod) {
        this.multiplierMod = multiplierMod;
        this.basePointMod = basePointMod;
    }

    @Override
    public Optional<UnaryOperator<Double>> getMultiplierMapper() {
        return this.multiplierMod;
    }

    @Override
    public Optional<UnaryOperator<Integer>> getBasePointMapper() {
        return this.basePointMod;
    }

    @Override
    public void setGameStatus(final ModifierStatsSupplier stats) {
        // it doesn't set any game status since it's not required.
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((multiplierMod == null) ? 0 : multiplierMod.hashCode());
        result = prime * result + ((basePointMod == null) ? 0 : basePointMod.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final BaseModifier other = (BaseModifier) obj;
        return Objects.equals(other.multiplierMod, this.multiplierMod)
            && Objects.equals(other.basePointMod, this.basePointMod);
    }

    @Override
    public boolean canApply() {
        return true;
    }

    @Override
    public String toString() {
        return "BaseModifier [multiplierMod=" + multiplierMod + ", basePointMod=" + basePointMod + "]";
    }
}
