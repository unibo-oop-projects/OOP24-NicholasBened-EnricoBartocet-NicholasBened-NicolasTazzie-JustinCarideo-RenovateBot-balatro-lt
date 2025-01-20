package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.balatrolt.model.api.Deck;
import it.unibo.balatrolt.model.api.PlayableCard;

public class DeckImpl<X, Y> implements  Deck<X, Y> {
    
    private final List<PlayableCard<X, Y>> deck; 

    @SuppressWarnings({ "unchecked", "rawtypes" }) //TO MODIFY
    public DeckImpl() {
        this.deck = new ArrayList<>();
        for (Suits suit : Suits.values()) {
            for (Ranks rank : Ranks.values()) {
                this.deck.add(new PlayableCardImpl(new Pair<>(rank, suit)));
            }
        }
    }
    
    @Override
    public List<PlayableCard<X, Y>> getDeck() {
        return List.copyOf(this.deck);
    }
}
