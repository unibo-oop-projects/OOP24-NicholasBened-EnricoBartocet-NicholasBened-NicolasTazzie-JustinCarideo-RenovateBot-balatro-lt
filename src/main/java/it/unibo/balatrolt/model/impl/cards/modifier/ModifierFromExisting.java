package it.unibo.balatrolt.model.impl.cards.modifier;

import java.util.Objects;
import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * Special modifier that wraps and existing modifier.
 * @author Nicolas Tazzieri
 */
public final class ModifierFromExisting extends ModifierDecorator {
    private final Optional<UnaryOperator<Double>> multiplierMod;
    private final Optional<UnaryOperator<Integer>> basePointMod;

    /**
     * Modifier from existing builder.
     * @param multiplierMod multiplier function
     * @param basePointMod base points function
     * @param modifier base
     */
    public ModifierFromExisting(
            final Optional<UnaryOperator<Double>> multiplierMod,
            final Optional<UnaryOperator<Integer>> basePointMod,
            final CombinationModifier modifier) {
        super(modifier);
        this.basePointMod = checkNotNull(basePointMod);
        this.multiplierMod = checkNotNull(multiplierMod);
    }

    @Override
    protected boolean canApplySingle() {
        return true;
    }

    @Override
    protected Optional<UnaryOperator<Double>> getInnerMultiplierFun() {
        return this.multiplierMod;
    }

    @Override
    protected Optional<UnaryOperator<Integer>> getInnerBasePointsFun() {
        return this.basePointMod;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((multiplierMod == null) ? 0 : multiplierMod.hashCode());
        result = prime * result + ((basePointMod == null) ? 0 : basePointMod.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModifierFromExisting other = (ModifierFromExisting) obj;
        return Objects.equals(other.multiplierMod, this.multiplierMod)
        && Objects.equals(other.basePointMod, this.basePointMod)
        && super.equals(other);
    }

    @Override
    public String toString() {
        return "ModifierFromExisting [multiplierMod=" + multiplierMod + ", basePointMod=" + basePointMod + "]";
    }
}
