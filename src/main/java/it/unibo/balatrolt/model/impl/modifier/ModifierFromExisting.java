package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;

/**
 * Special modifier that wraps and existing modifier.
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
            final Modifier modifier) {
        super(modifier);
        this.basePointMod = checkNotNull(basePointMod);
        this.multiplierMod = checkNotNull(multiplierMod);
    }

    @Override
    public Optional<UnaryOperator<Integer>> getBasePointMapper() {
        return this.canApply() ? this.mergeOperatorsMapper(super.getBasePointMapper(), this.basePointMod) : Optional.absent();
    }

    @Override
    public Optional<UnaryOperator<Double>> getMultiplierMapper() {
        return this.canApply() ? this.mergeOperatorsMapper(super.getMultiplierMapper(), this.multiplierMod) : Optional.absent();
    }

    private <X> Optional<UnaryOperator<X>> mergeOperatorsMapper(
            final Optional<UnaryOperator<X>> m1,
            final Optional<UnaryOperator<X>> m2) {
        if (m1.isPresent() && m2.isPresent()) {
            final var composed = m1.get().compose(m2.get());
            return Optional.of(composed::apply);
        }
        if (m1.isPresent()) {
            return m1;
        }
        if (m2.isPresent()) {
            return m2;
        }
        return Optional.absent();
    }

    @Override
    protected boolean canApplySingle() {
        return true;
    }
}
