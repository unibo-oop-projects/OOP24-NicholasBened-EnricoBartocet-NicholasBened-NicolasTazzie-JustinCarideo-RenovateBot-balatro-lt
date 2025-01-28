package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.balatrolt.model.api.Deck;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;

/**
 * Implementation of a normal 52 card deck.
 */
public final class DeckImpl implements Deck {

    private final List<PlayableCard> deck;

    /**
     * Constructor that generates a 52 card deck.
     */
    public DeckImpl() {
        this.deck = new ArrayList<>();
        for (final Suit suit : Suit.values()) {
            for (final Rank rank : Rank.values()) {
                this.deck.add(new PlayableCardImpl(new Pair<>(rank, suit)));
            }
        }
    }

    @Override
    public List<PlayableCard> getDeck() {
        return List.copyOf(this.deck);
    }
}
