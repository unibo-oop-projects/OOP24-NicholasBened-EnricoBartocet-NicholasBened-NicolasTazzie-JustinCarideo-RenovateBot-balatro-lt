package it.unibo.balatrolt.model.impl;

import java.util.Collections;
import java.util.List;

import it.unibo.balatrolt.model.api.Combination;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayedHand;

public class PlayedHandImpl implements PlayedHand {

    private final List<PlayableCard> hand;

    public PlayedHandImpl(List<PlayableCard> hand) {
        this.hand = hand;
    }

    @Override
    public List<PlayableCard> getCards() {
        return Collections.unmodifiableList(this.hand);
    }

    @Override
    public Combination evaluateCombination() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluateCombination'");
    }
}
