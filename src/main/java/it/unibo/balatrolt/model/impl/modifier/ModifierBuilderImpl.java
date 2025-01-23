package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.BasePoints;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.Multiplier;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Builder implementation for Modifier.
 */
public final class ModifierBuilderImpl implements ModifierBuilder {
    private Optional<UnaryOperator<Multiplier>> mFun = Optional.absent();
    private Optional<UnaryOperator<BasePoints>> bpFun = Optional.absent();
    private Optional<Predicate<Set<PlayableCard>>> playerCardBound = Optional.absent();

    @Override
    public ModifierBuilder addMultiplierModifier(final UnaryOperator<Multiplier> multiplierFun) {
        this.mFun = Optional.fromNullable(multiplierFun);
        return this;
    }

    @Override
    public ModifierBuilder addBasePointsModifier(final UnaryOperator<BasePoints> bPFun) {
        this.bpFun = Optional.fromNullable(bPFun);
        return this;
    }

    @Override
    public ModifierBuilder addPlayedCardBound(final Predicate<Set<PlayableCard>> condition) {
        this.playerCardBound = Optional.fromNullable(condition);
        return this;
    }

    @Override
    public Modifier build() {
        if (!mFun.isPresent() && !bpFun.isPresent()) {
            throw new IllegalStateException("At least one between Moltiplicator and BasePoints functions must be present!");
        }
        Modifier modifier = new BaseModifier(mFun, bpFun);
        if (playerCardBound.isPresent()) {
            modifier = new ModifierWithCardCondition(modifier, playerCardBound.get());
        }
        return modifier;
    }
}
