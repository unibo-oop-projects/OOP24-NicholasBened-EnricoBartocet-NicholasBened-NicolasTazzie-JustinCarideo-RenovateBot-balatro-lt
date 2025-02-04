package it.unibo.balatrolt.controller.api;

import java.util.List;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.RoundStatus;
import it.unibo.balatrolt.model.api.PlayerStatus;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.Blind;

/**
 * Part of the controller that has to deal with levels ({@link Ante} and {@link Blind}).
 */
public interface LevelsController {

    /**
     * Discard cards that the player has in hand.
     * @param cards the list of cards to discard
     */
    void discardCards(List<PlayableCardInfo> cards);

    /**
     * Play cards that the player has in hand.
     * @param cards the list of cards to play
     * @param player the status of the {@link it.unibo.balatrolt.model.api.Player}
     */
    void playCards(List<PlayableCardInfo> cards, PlayerStatus player);

    /**
     * Update the status of the rounds (like Blind advancement, Ante advancement, ecc...).
     */
    void updateAnte();

    /**
     * @return the info of the current {@link Ante}
     */
    AnteInfo getCurrentAnte();

    /**
     * @return the info of the current {@link Blind}
     */
    BlindInfo getCurrentBlindInfo();

    /**
     * @return the statistics of the current {@link Blind}
     */
    BlindStats getCurrentBlindStats();

    /**
     * @return the Cards that the player has in his hand.
     */
    List<PlayableCardInfo> getHand();

    /**
     * @return the number of {@link Ante} that compose the game
     */
    int getNumAnte();

    /**
     * @return the status of the current round
     */
    RoundStatus getRoundStatus();

    /**
     * Transform the PlayableCards from the communication class to the effective PlayableCard of the Model.
     * @param cards the cards of the communication class
     * @return the cards of the Model class
     */
    List<PlayableCard> translatePlayableCard(List<PlayableCardInfo> cards);

    /**
     * Tells if the game is over.
     * @return true if the game is over, false otherwise.
     */
    boolean isOver();
}
