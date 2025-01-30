package it.unibo.balatrolt.model.impl.levels;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.levels.BlindModifier;

public class BlindStats {
    public static final int BASE_HANDS = 4;
    public static final int BASE_DISCARDS = 4;
    private final BlindModifier modifier;
    private int chips;
    private int remainingHands;
    private int remainingDiscards;

    public BlindStats(final BlindModifier modifier) {
        this.chips = 0;
        this.modifier = Preconditions.checkNotNull(modifier);
        this.remainingHands = modifier.getNewHands(BASE_HANDS);
        this.remainingDiscards = modifier.getNewDiscards(BASE_DISCARDS);
    }

    public int getCurrentChips() {
        return this.chips;
    }

    public void incrementChips(final int handChips) {
        Preconditions.checkArgument(handChips >= 0, "The number of chips earned cannot be negative");
        this.chips += this.modifier.getNewChips(handChips);
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
