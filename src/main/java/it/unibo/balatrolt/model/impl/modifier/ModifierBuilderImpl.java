package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkState;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Builder implementation for Modifier.
 */
public final class ModifierBuilderImpl implements ModifierBuilder {
    private Optional<UnaryOperator<Double>> mFun = Optional.absent();
    private Optional<UnaryOperator<Integer>> bpFun = Optional.absent();
    private Optional<Predicate<Set<PlayableCard>>> playerCardBound = Optional.absent();
    private Optional<Modifier> toMerge = Optional.absent();

    @Override
    public ModifierBuilder addMultiplierModifier(final UnaryOperator<Double> multiplierFun) {
        this.mFun = Optional.fromNullable(multiplierFun);
        return this;
    }

    @Override
    public ModifierBuilder addBasePointsModifier(final UnaryOperator<Integer> bPFun) {
        this.bpFun = Optional.fromNullable(bPFun);
        return this;
    }

    @Override
    public ModifierBuilder addPlayedCardBound(final Predicate<Set<PlayableCard>> condition) {
        this.playerCardBound = Optional.fromNullable(condition);
        return this;
    }

    @Override
    public ModifierBuilder merge(final Modifier toMerge) {
        checkState(!this.toMerge.isPresent(), "merge() should be called once");
        this.toMerge = Optional.of(toMerge);
        return this;
    }

    @Override
    public Modifier build() {
        if (!this.mFun.isPresent() && !this.bpFun.isPresent()) {
            throw new IllegalStateException("At least one between Moltiplicator and BasePoints functions must be present!");
        }
        Modifier modifier;
        if (this.toMerge.isPresent()) {
            modifier = new ModifierFromExisting(this.mFun, this.bpFun, this.toMerge.get());
        } else {
            modifier = new BaseModifier(this.mFun, this.bpFun);
        }
        if (this.playerCardBound.isPresent()) {
            modifier = new ModifierWithCardCondition(modifier, this.playerCardBound.get());
        }
        return modifier;
    }

}
