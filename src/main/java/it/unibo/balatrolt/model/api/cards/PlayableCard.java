package it.unibo.balatrolt.model.api.cards;

/**
 * An interface modelling a PlayableCard (of standard 52 card deck),
 * it has a rank and a suit.
 * @author Benedetti Nicholas
 */
public interface PlayableCard extends Card {

    enum Rank {
        /**
         * This models the concept of rank in a card.
         */
        TWO, THREE, FOUR, FIVE, SIX, SEVEN,
        EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    enum Suit {
        /**
         * This models the concept of suit in a card.
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
