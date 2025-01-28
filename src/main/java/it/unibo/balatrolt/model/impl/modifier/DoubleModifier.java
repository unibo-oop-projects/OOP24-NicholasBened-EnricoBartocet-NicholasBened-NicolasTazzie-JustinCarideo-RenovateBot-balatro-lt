package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;

/**
 * A {@link Modifier} that has two inner Modifiers.
 */
public class DoubleModifier extends ModifierDecorator {
    private final Modifier secondBase;

    /**
     * Constructor.
     * @param baseModifier first modifier to wrap (base)
     * @param toMerge second modifier to wrap (to merge)
     */
    public DoubleModifier(final Modifier baseModifier, final Modifier toMerge) {
        super(baseModifier);
        this.secondBase = toMerge;
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
}
