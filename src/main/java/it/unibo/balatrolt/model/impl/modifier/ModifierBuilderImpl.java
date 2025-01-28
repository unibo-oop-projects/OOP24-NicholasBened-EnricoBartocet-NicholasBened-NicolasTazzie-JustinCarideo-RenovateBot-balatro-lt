package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkState;

import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Builder implementation for Modifier.
 */
public final class ModifierBuilderImpl implements ModifierBuilder {
    private Optional<UnaryOperator<Double>> mFun = Optional.absent();
    private Optional<UnaryOperator<Integer>> bpFun = Optional.absent();
    private Optional<Predicate<Set<PlayableCard>>> playedCardBound = Optional.absent();
    private Optional<Predicate<Set<PlayableCard>>> holdingCardBound = Optional.absent();
    private Optional<Predicate<CombinationType>> combinationBound = Optional.absent();
    private Optional<Predicate<Integer>> currencyBound = Optional.absent();
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
        this.playedCardBound = Optional.fromNullable(condition);
        return this;
    }

    @Override
    public ModifierBuilder addHoldingCardBound(final Predicate<Set<PlayableCard>> condition) {
        this.holdingCardBound = Optional.fromNullable(condition);
        return this;
    }

    @Override
    public ModifierBuilder addCombinationBound(final Predicate<CombinationType> condition) {
        this.combinationBound = Optional.fromNullable(condition);
        return this;
    }

    @Override
    public ModifierBuilder addCurrentCurrencyBound(final Predicate<Integer> condition) {
        this.currencyBound = Optional.fromNullable(condition);
        return this;
    }

    @Override
    public ModifierBuilder merge(final Modifier toMerge) {
        if (!this.toMerge.isPresent()) {
            this.toMerge = Optional.of(new ModifierFromExisting(Optional.absent(), Optional.absent(), toMerge));
            return this;
        }
        this.toMerge = Optional.of(new DoubleModifier(this.toMerge.get(), toMerge));
        return this;
    }

    @Override
    public Modifier build() {
        checkState(this.mFun.isPresent() || this.bpFun.isPresent() || this.toMerge.isPresent(),
        "At least one between Moltiplicator and BasePoints functions,"
        + "or modifier to merge must be present!");
        Modifier modifier;
        if (this.toMerge.isPresent()) {
            modifier = new ModifierFromExisting(this.mFun, this.bpFun, this.toMerge.get());
        } else {
            modifier = new BaseModifier(this.mFun, this.bpFun);
        }
        if (this.playedCardBound.isPresent()) {
            modifier = new ModifierPlayedCardCondition(modifier, this.playedCardBound.get());
        }
        if (this.holdingCardBound.isPresent()) {
            modifier = new ModifierHoldingCardCondition(modifier, this.holdingCardBound.get());
        }
        if (this.combinationBound.isPresent()) {
            modifier = new ModifierCombinationCondition(modifier, this.combinationBound.get());
        }
        if (this.currencyBound.isPresent()) {
            modifier = new ModifierCurrencyCondition(modifier, this.currencyBound.get());
        }
        return modifier;
    }
}
