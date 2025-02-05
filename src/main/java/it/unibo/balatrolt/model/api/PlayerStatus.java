package it.unibo.balatrolt.model.api;

import java.util.List;

import it.unibo.balatrolt.model.api.cards.BuffedDeck;
import it.unibo.balatrolt.model.api.cards.specialcard.SpecialCard;

/**
 * Represent the status of a Player.
 */
public interface PlayerStatus {

    /**
     * @return the deck used by the player
     */
    BuffedDeck deck();

    /**
     * @return a list containing the player's special cards
     */
    List<SpecialCard> specialCards();

    /**
     * @return the money that the player has
     */
    int currency();

}
