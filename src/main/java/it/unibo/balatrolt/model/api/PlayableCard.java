package it.unibo.balatrolt.model.api;

/**
 * An interface modelling a PlayableCard (of standard 52 card deck),
 * it has a rank and a suit.
 */
public interface PlayableCard extends Card {

    /**
     * This models the concept of rank of the card.
    */
    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN,
        EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    /**
     * This models the concept of suit in the card.
    */
    public enum Suit {
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

    /**
     * @param card, to compare
     * @return true if the card is equal to this one, false otherwise.
     */
    boolean equals(Card card);
}
