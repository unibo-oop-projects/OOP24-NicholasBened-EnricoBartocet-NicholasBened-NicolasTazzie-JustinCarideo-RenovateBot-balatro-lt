package it.unibo.balatrolt.model.api;

import com.google.common.base.Optional;

/**
 * This models the most abstract concept of a card.
 */
public interface Card {

    /**
     *
     * @return the Modifier of the card, if it's present.
     */
    Optional<Modifier> getModifier();
}
