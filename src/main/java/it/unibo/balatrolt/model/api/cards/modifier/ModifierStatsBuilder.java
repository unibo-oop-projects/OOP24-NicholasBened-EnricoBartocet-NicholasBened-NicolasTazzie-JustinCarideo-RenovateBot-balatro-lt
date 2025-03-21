package it.unibo.balatrolt.model.api.cards.modifier;

import java.util.Set;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.Combination.CombinationType;

/**
 * Builder for ModifierStats.
 */
public interface ModifierStatsBuilder {

    /**
     * Sets current played cards.
     * @param playedCards current played cards
     * @return builder state
     */
    ModifierStatsBuilder addPlayedCards(Set<PlayableCard> playedCards);

    /**
     * Sets current holding cards.
     * @param holdingCards current holding cards
     * @return builder state
     */
    ModifierStatsBuilder addHoldingCards(Set<PlayableCard> holdingCards);

    /**
     * Sets current currency.
     * @param currentCurrency current currency
     * @return builder state
     */
    ModifierStatsBuilder addCurrentCurrency(int currentCurrency);

    /**
     * Sets current combination.
     * @param combination current combination
     * @return builder state
     */
    ModifierStatsBuilder addCurrentCombination(CombinationType combination);

    /**
     * builds ModifierStats.
     * @return builded ModidierStats with all parameters set
     */
    ModifierStatsSupplier build();

}
