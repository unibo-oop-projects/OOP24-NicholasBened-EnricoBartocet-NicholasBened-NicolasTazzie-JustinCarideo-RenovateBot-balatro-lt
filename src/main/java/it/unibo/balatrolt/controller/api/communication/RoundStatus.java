package it.unibo.balatrolt.controller.api.communication;

/**
 * Enum used to communicate the status of a Blind.
 */
public enum RoundStatus {
    /**
     * The player is playing against the Blind.
     */
    IN_GAME,

    /**
     * The player defeated the Blind (he won the round).
     */
    BLIND_DEFEATED,

    /**
     * The player was defeated by the Blind (he lost the round).
     */
    BLIND_WON
}
