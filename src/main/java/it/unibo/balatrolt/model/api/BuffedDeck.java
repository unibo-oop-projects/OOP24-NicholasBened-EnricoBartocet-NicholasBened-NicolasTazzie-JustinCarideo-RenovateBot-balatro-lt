package it.unibo.balatrolt.model.api;

import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindModifier;

/**
 * Specialization of a {@link Deck}, which allows to modify the characteristics of a {@link Blind}.
 */
public interface BuffedDeck extends Deck {

    /**
     * Get the name of the deck.
     * @return the name of the deck
     */
    String getName();

    /**
     * Get the description of how the deck modifies the blind.
     * @return the description of how the deck modifies the blind
     */
    String getDescription();

    /**
     * Get the modifier for the Blinds.
     * @return the modifier of the deck
     */
    BlindModifier getModifier();

}
