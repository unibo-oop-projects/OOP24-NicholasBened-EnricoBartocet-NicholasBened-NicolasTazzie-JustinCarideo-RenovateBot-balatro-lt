package it.unibo.balatrolt.model.impl.modifier;

import java.util.Objects;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;

public class ConditionalModifier<X> extends ModifierDecorator {
    private Optional<ModifierStatsSupplier> stats = Optional.absent();
    private final Predicate<X> condition;

    public ConditionalModifier(Predicate<X> condition, Modifier modifier) {
        super(modifier);
        this.condition = Objects.requireNonNull(condition, "Condition can't be null");
    }

    @Override
    public void setGameStatus(ModifierStatsSupplier stats) {
        this.stats = Optional.of(stats);
    }

    protected Predicate<X> getCondition() {
        return this.condition;
    }

    protected Optional<ModifierStatsSupplier> getStats() {
        return this.stats;
    }

    @Override
    protected boolean canApply() {
        return true;
    }
}
