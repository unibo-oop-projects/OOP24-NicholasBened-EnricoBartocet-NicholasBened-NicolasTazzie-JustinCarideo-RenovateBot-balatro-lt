package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.Combination.CombinationType;
import it.unibo.balatrolt.model.api.ModifierStatsBuilder;
import it.unibo.balatrolt.model.api.ModifierStatsSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Implementation of {@link ModifierStatsSupplier}.
 */
public final class ModifierStatsSupplierBuilderImpl implements ModifierStatsBuilder {
    private final class ModifierStatsSupplierImpl implements ModifierStatsSupplier {
        @Override
        public Optional<Set<PlayableCard>> getHoldingCards() {
            return holdingCards;
        }

        @Override
        public Optional<Set<PlayableCard>> getPlayedCards() {
            return playedCards;
        }

        @Override
        public Optional<Integer> getCurrentCurrency() {
            return currentCurrency;
        }

        @Override
        public Optional<CombinationType> getCurrentCombinationType() {
            return currentCombination;
        }
    }

    private Optional<Set<PlayableCard>> playedCards = Optional.absent();
    private Optional<Set<PlayableCard>> holdingCards = Optional.absent();
    private Optional<Integer> currentCurrency = Optional.absent();
    private Optional<CombinationType> currentCombination = Optional.absent();

    @Override
    public ModifierStatsBuilder setPlayedCards(final Set<PlayableCard> playedCards) {
        this.playedCards = Optional.of(playedCards);
        return this;
    }

    @Override
    public ModifierStatsBuilder setHoldingCards(final Set<PlayableCard> holdingCards) {
        this.holdingCards = Optional.of(holdingCards);
        return this;
    }

    @Override
    public ModifierStatsBuilder setCurrentCurrency(final int currentCurrency) {
        this.currentCurrency = Optional.of(currentCurrency);
        return this;
    }

    @Override
    public ModifierStatsBuilder setCurrentCombination(final CombinationType combination) {
        this.currentCombination = Optional.of(combination);
        return this;
    }

    @Override
    public ModifierStatsSupplier build() {
        return new ModifierStatsSupplierImpl();
    }
}
