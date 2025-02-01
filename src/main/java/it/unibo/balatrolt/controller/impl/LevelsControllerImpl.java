package it.unibo.balatrolt.controller.impl;

import java.util.List;

import it.unibo.balatrolt.controller.api.LevelsController;
import it.unibo.balatrolt.model.api.levels.Ante;

public class LevelsControllerImpl implements LevelsController {
    private final List<Ante> antes;
    private final int numHandSlot;
    private int currAnte;
    private int currBlind;

    /**
     * constructor of the hand's controller.
     */
    public LevelsControllerImpl(List<Ante> antes) {
        this.currAnte = 0;
        this.currBlind = 0;
        this.antes = antes;
        this.numHandSlot = this.antes.get(this.currAnte).getBlinds().get(this.currBlind).getHandCards().size();
    }

    @Override
    public int numHandSlot() {
        return this.numHandSlot;
    }

    @Override
    public List<String> getHand() {
        return this.antes.get(this.currAnte).getBlinds().get(this.currBlind).getHandCards()
            .stream()
            .map(e -> e.getRank().name() + e.getSuit().name())
            .toList();
    }
}
