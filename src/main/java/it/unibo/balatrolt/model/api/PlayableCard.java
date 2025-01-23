package it.unibo.balatrolt.model.api;

/**
 * An interface modelling a PlayableCard (of standard 52 card deck),
 * it has a rank and a suit.
 */
public interface PlayableCard extends Card {

    /**
     *
     * @return the rank of the card
     */
    Rank getRank();

    /**
     *
     * @return the suit of the card
     */
    Suit getSuit();
}
