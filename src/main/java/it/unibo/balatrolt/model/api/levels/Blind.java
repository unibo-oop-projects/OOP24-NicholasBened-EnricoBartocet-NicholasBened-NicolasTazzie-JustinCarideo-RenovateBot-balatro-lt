package it.unibo.balatrolt.model.api.levels;

import java.util.List;

import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Interface used to represent a Blind.
 */
public interface Blind {

    /**
     * Represent the status of a Blind.
     */
    enum Status {
        /**
         * There are hands left.
         * The player still has the chance to defeat the Blind.
         */
        IN_GAME,

        /**
         * The Blind hasn't been defeated.
         * This happens when there aren't hands left, and
         * the amount of chips earned isn't enough to defeat the Blind.
         */
        GAME_OVER,

        /**
         * The Blind has been defeated.
         * The player earned a sufficient amount of chips.
         */
        DEFEATED
    }

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
     * Returns the amount of currency that the player will get if he will defeat the Blind.
     * @return the amount of the reward in case the Blind will be defeated
     */
    int getReward();

    /**
     * Returns the cards still present in the Deck.
     * @return the list of cards in the Deck not already used
     */
    List<PlayableCard> getRemainingDeckCards();

    /**
     * Returns the list of the Cards that the Player can use to create combinations.
     * @return the list of cards available
     */
    List<PlayableCard> getHandCards();

    /**
     * Play a list of cards.
     * @param toPlay the list of cards to play
     */
    void playHand(List<PlayableCard> toPlay);

    /**
     * Discard a list of cards.
     * @param toDiscard the list of cards to discard
     */
    void discardPlayableCards(List<PlayableCard> toDiscard);

    /**
     * Get the numbers of hand that the user can play.
     * @return the number of hands left
     */
    int getRemainingHands();

    /**
     * Get the numbers of times that the user can discard cards.
     * @return the number of discards left
     */
    int getRemainingDiscards();

    /**
     * Returns wether the Blind is over or not.
     * @return true if the Blind is over, false otherwise
     */
    Status getStatus();
}
