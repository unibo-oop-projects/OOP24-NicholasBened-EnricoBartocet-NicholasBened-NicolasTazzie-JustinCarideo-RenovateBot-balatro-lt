package it.unibo.balatrolt.model.api.levels;

/**
 * Interface used to represent a Blind.
 */
public interface Blind {

    /**
     * Returns the number of the Blind.
     * @return the number of the blind
     */
    int getBlindNumber();

    /**
     * Returns the number of chips that the player needs to defeat the Blind.
     * @return the minimum amount of chip to defeat the blind
     */
    int getMinimumChips();

    /**
     * Returns the chip that the player has.
     * @return the acurrent amount of chips
     */
    int getCurrentChips();

    /**
     * Increment the chips that the player has.
     * @param handChips the amount of chip to increment
     */
    void incrementChips(int handChips);

    /**
     * Returns wether the Blind is over or not.
     * @return true if the Blind is over, false otherwise
     */
    boolean isOver();

    /**
     * Returns the amount of currency that the player will get if he will defeat the Blind.
     * @return the amount of the reward in case the Blind will be defeated
     */
    int getReward();
}
