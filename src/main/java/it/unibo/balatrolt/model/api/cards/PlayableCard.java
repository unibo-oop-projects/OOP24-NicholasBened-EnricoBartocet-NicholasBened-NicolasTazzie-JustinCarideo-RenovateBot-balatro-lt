package it.unibo.balatrolt.model.api.cards;

/**
 * An interface modelling a PlayableCard (of standard 52 card deck),
 * it has a rank and a suit.
 * @author Benedetti Nicholas
 */
public interface PlayableCard extends Card {

    /**
     * This enum models the concept of rank in a card.
     */
    enum Rank {
        /**
         * The ranks of the cards.
         */
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    /**
     * This enum models the concept of suit in a card.
     */
    enum Suit {
        /**
         * The suits of the cards.
         */
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    /**
     *
     * @return the rank of the card.
     */
    Rank getRank();

    /**
     *
     * @return the suit of the card.
     */
    Suit getSuit();
}
