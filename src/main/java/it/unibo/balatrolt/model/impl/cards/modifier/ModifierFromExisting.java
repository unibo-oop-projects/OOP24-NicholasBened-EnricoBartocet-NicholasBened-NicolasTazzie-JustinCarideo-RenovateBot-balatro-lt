package it.unibo.balatrolt.model.impl.cards.modifier;

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
}
