package it.unibo.balatrolt.model.api;

import java.util.Set;

public interface ModifierStatsBuilder {

    ModifierStatsBuilder setPlayedCards(Set<PlayableCard> playedCards);

    ModifierStatsBuilder setHoldingCards(Set<PlayableCard> holdingCards);

    ModifierStatsBuilder setCurrentCurrency(int currentCurrency);

    ModifierStatsBuilder setCurrentCombination(Combination.CombinationType combination);

    ModifierStatsSupplier build();

}