package it.unibo.balatrolt.controller.impl;

import java.util.List;

import it.unibo.balatrolt.controller.api.HandController;
import it.unibo.balatrolt.model.impl.levels.BlindCards;

/**
 * implementation of the hand's controller.
 */
public class HandControllerImpl implements HandController{
    private final BlindCards blindCards;
    private final int numHandSlot;

    /**
     * constructor of the hand's controller.
     */
    public HandControllerImpl() {
        this.blindCards = new BlindCards();
        this.numHandSlot = this.blindCards.getHandCards().size();
    }

    @Override
    public int numHandSlot() {
        return this.numHandSlot;
    }

    @Override
    public List<String> getHand() {
        return this.blindCards.getHandCards().stream()
            .map(e -> e.getRank().name() + e.getSuit().name())
            .toList();
    }
}