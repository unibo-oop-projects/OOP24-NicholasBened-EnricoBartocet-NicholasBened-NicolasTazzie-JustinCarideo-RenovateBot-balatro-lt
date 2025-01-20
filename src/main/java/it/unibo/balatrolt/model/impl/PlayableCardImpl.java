package it.unibo.balatrolt.model.impl;

import java.util.Optional;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.PlayableCard;

public class PlayableCardImpl implements PlayableCard {
    
    private final Pair<Ranks, Suits> card;

    public PlayableCardImpl(Pair<Ranks, Suits> card) {
        this.card = card;
    }

    @Override
    public Ranks getRank() {
        return this.card.get1(); 
    }

    @Override
    public Suits getSuit() {
        return this.card.get2();
    }

    @Override
    public Optional<Modifier> getModifier() {
        return Optional.empty();
    }

}
