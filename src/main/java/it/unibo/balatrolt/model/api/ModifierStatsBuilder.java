package it.unibo.balatrolt.model.api;

import java.util.Set;

/**
 * Builder for ModifierStats.
 */
public interface ModifierStatsBuilder {

    /**
     * Sets current played cards.
     *
     * @param playedCards current played cards
     * @return builder state
     */
    ModifierStatsBuilder setPlayedCards(Set<PlayableCard> playedCards);

    /**
     * Sets current holding cards.
     *
     * @param holdingCards current holding cards
     * @return builder state
     */
    ModifierStatsBuilder setHoldingCards(Set<PlayableCard> holdingCards);

    /**
     * Sets current currency.
     *
     * @param currentCurrency current currency
     * @return builder state
     */
    ModifierStatsBuilder setCurrentCurrency(int currentCurrency);

    /**
     * Sets current combination.
     *
     * @param combination current combination
     * @return builder state
     */
    ModifierStatsBuilder setCurrentCombination(Combination.CombinationType combination);

    /**
     * builds ModifierStats.
     *
     * @return builded ModidierStats with all parameters set
     */
    ModifierStatsSupplier build();

}