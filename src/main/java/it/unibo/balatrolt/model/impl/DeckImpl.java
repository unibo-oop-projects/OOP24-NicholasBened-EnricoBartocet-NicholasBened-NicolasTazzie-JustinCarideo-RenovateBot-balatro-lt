package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.balatrolt.model.api.Deck;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.Rank;
import it.unibo.balatrolt.model.api.Suit;

public class DeckImpl implements Deck {

    private final List<PlayableCard> deck;

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
