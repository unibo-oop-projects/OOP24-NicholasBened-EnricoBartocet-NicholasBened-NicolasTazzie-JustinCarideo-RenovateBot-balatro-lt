package it.unibo.balatrolt.model.impl.cards.modifier;

import java.util.Objects;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * A {@link CombinationModifier} that has two inner Modifiers.
 * @author Nicolas Tazzieri
 */
public final class DoubleModifier extends ModifierDecorator {
    private final CombinationModifier secondBase;

    /**
     * Constructor.
     * @param baseModifier first modifier to wrap (base)
     * @param toMerge second modifier to wrap (to merge)
     */
    public DoubleModifier(final CombinationModifier baseModifier, final CombinationModifier toMerge) {
        super(baseModifier);
        this.secondBase = new ModifierFromExisting(Optional.absent(), Optional.absent(), toMerge);
    }

    @Override
    protected Optional<UnaryOperator<Double>> getInnerMultiplierFun() {
        return this.secondBase.getMultiplierMapper();
    }

    @Override
    protected Optional<UnaryOperator<Integer>> getInnerBasePointsFun() {
        return this.secondBase.getBasePointMapper();
    }

    @Override
    protected boolean canApplySingle() {
        if (super.getStats().isPresent()) {
            secondBase.setGameStatus(super.getStats().get());
        }
        return secondBase.canApply();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((secondBase == null) ? 0 : secondBase.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final DoubleModifier other = (DoubleModifier) obj;
        return Objects.equals(other.secondBase, this.secondBase)
        && super.equals(other);
    }

    @Override
    public String toString() {
        return "DoubleModifier [secondBase=" + secondBase + "]";
    }
}
