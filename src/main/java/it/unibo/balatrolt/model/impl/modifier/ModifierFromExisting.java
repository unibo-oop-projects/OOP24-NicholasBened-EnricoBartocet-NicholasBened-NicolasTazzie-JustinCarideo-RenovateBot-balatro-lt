package it.unibo.balatrolt.model.impl.modifier;

import java.util.function.UnaryOperator;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

/**
 * Special modifier that wraps and existing modifier.
 */
public final class ModifierFromExisting extends ModifierDecorator {
    private final Optional<UnaryOperator<Double>> multiplierMod;
    private final Optional<UnaryOperator<Integer>> basePointMod;

    public ModifierFromExisting(
            final Optional<UnaryOperator<Double>> multiplierMod,
            final Optional<UnaryOperator<Integer>> basePointMod,
            final Modifier modifier) {
        super(modifier);
        this.basePointMod = checkNotNull(basePointMod);
        this.multiplierMod = checkNotNull(multiplierMod);
    }

    @Override
    public final Optional<UnaryOperator<Integer>> getBasePointMapper() {
        return this.mergeOperatorsMapper(super.getBasePointMapper(), this.basePointMod);
    }

    @Override
    public final Optional<UnaryOperator<Double>> getMultiplierMapper() {
        return this.mergeOperatorsMapper(super.getMultiplierMapper(), this.multiplierMod);
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
    public void setGameStatus(final ModifierStatsSupplier stats) {
        super.setGameStatus(stats);
    }

    @Override
    protected boolean canApply() {
        return true;
    }
}
