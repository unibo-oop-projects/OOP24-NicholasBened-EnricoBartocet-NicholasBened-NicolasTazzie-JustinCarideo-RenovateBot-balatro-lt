package it.unibo.balatrolt.model.api;

/**
 * An interface modelling a PlayableCard (of standard 52 card deck),
 * it has a rank and a suit. 
 */
public interface PlayableCard<X, Y> extends Card, Rank, Suit{
    
    /**
     * 
     * @return the rank of the card
     */
    X getRank();

    /**
     * 
     * @return the suit of the card
     */
    Y getSuit();
}
