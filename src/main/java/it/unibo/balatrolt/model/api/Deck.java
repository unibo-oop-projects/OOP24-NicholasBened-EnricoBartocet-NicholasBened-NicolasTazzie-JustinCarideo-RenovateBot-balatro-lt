package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * Models a normal 52 card deck.
 */
public interface Deck {

    /**
     *
     * @return a List<PlayableCard> representing a normal 52 card deck
     */
    List<PlayableCard> getDeck();
}
