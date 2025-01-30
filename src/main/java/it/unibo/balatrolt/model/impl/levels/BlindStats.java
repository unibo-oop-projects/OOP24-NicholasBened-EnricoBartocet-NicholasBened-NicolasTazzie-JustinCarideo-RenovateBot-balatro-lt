package it.unibo.balatrolt.model.impl.levels;

import it.unibo.balatrolt.model.api.DeckModifier;

public class BlindStats {
    private static final int BASE_HANDS = 4;
    private static final int BASE_DISCARDS = 4;
    private int chips;
    private int remainingHands;
    private int remainingDiscards;

    public BlindStats(final DeckModifier modifier) {
        this.chips = 0;
        this.remainingHands = modifier.newHands(BASE_HANDS);
        this.remainingDiscards = modifier.newDiscards(BASE_DISCARDS);
    }

    public int getCurrentChips() {
        return this.chips;
    }

    public void incrementChips(int handChips) {
        assert handChips >= 0;
        this.chips += handChips;
    }

    public int getRemainingHands() {
        return this.remainingHands;
    }

    public int getRemainingDiscards() {
        return this.remainingDiscards;
    }

    public void decrementDiscards() {
        this.remainingDiscards--;
    }

    public void decrementHands() {
        this.remainingHands--;
    }
}
