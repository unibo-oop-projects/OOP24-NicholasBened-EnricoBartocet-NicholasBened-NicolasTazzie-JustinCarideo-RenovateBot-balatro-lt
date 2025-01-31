package it.unibo.balatrolt.model.api.cards;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;

/**
 * This models the most abstract concept of a card.
 */
public interface Card {

    /**
     *
     * @return the Modifier of the card, if it's present.
     */
    Optional<CombinationModifier> getModifier();
}
