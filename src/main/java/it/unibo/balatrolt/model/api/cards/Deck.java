package it.unibo.balatrolt.model.api.cards;

import java.util.List;

/**
 * Interface to represent a deck of {@link PlayableCard Playable Cards}.
 */
public interface Deck {

    /**
     * Returns an immutable list of cards that composes the deck.
     * @return a List<PlayableCard> representing a deck
     */
    List<PlayableCard> getCards();

    /**
     * Returns a shuffled copy of the cards.
     * @return a list containing a shuffled copy of the cards
     */
    List<PlayableCard> getShuffledCards();
}
