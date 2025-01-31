package it.unibo.balatrolt.model.impl.levels;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.levels.BlindModifier;

/**
 * Implementation which holds the player's in game
 * statistics and manage them.
 * @author Benedetti Nicholas
 */
public class BlindStats {
    /**
     * Constant for the base number of hand the player can play.
     */
    public static final int BASE_HANDS = 4;
    /**
     * Constant for the base number of discards the player can do.
     */
    public static final int BASE_DISCARDS = 4;
    private final BlindModifier modifier;
    private int chips;
    private int remainingHands;
    private int remainingDiscards;

    /**
     * Sets all the fields.
     *
     * @param modifier given by the choosen deck.
     */
    public BlindStats(final BlindModifier modifier) {
        this.chips = 0;
        this.modifier = Preconditions.checkNotNull(modifier);
        this.remainingHands = modifier.getNewHands(BASE_HANDS);
        this.remainingDiscards = modifier.getNewDiscards(BASE_DISCARDS);
    }

    /**
     *
     * @return current player's chips
     */
    public int getCurrentChips() {
        return this.chips;
    }

    /**
     * Adds the chips given to the player's total.
     *
     * @param handChips chips to add to the total.
     */
    public void incrementChips(final int handChips) {
        Preconditions.checkArgument(handChips >= 0, "The number of chips earned cannot be negative");
        this.chips += this.modifier.getNewChips(handChips);
    }

    /**
     *
     * @return remaining player's playable hands.
     */
    public int getRemainingHands() {
        return this.remainingHands;
    }

    /**
     *
     * @return remaining player's discards.
     */
    public int getRemainingDiscards() {
        return this.remainingDiscards;
    }

    /**
     * Decrements remaining discards.
     */
    public void decrementDiscards() {
        this.remainingDiscards--;
    }

    /**
     * Decrements remaining playable hands.
     */
    public void decrementHands() {
        this.remainingHands--;
    }
}
