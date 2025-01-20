package it.unibo.balatrolt.model.api;

import java.util.List;

/** 
 * Models a normal 52 card deck
 */
public interface Deck extends Rank, Suit {

    /**
     * 
     * @return a list representing a normal 52 card deck
     */
    public List<PlayableCard> getDeck();
}
