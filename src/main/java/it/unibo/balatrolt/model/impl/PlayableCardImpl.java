package it.unibo.balatrolt.model.impl;

import java.util.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.PlayableCard;

public class PlayableCardImpl<X, Y> implements PlayableCard<X, Y> {
    
    private final Pair<X, Y> card;

    public PlayableCardImpl(Pair<X, Y> card) {
        this.card = card;
    }

    @Override
    public X getRank() {
        return this.card.get1(); 
    }

    @Override
    public Y getSuit() {
        return this.card.get2();
    }

    @Override
    public Optional<Modifier> getModifier() {
        return Optional.empty();
    }
}
