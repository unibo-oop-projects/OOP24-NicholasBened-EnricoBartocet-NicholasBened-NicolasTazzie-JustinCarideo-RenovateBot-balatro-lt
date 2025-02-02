package it.unibo.balatrolt.view.api;

import java.util.List;
import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

/**
 * Interface representing the view in the application.
 */
public interface View {

    void notifyErrror(String name, String desc);

    /**
     * Shows the main menu.
     */
    void showMainMenu();

    /**
     * Shows the decks to choose from.
     * @param decks to choose from.
     */
    void showDecks(Set<DeckInfo> decks);

    /**
     * Shows the ante information: the current blind,
     * current ante, the number of the ante's blinds ecc..
     * @param anteInfo the ante information.
     */
    void showAnte(AnteInfo anteInfo);

    /**
     * Starts the actual game.
     *
     * @param info the information of the blind (blind's id, reward, minimum chips).
     * @param stats the statistics of the blind (remaining hands, discards and chips).
     * @param specialCards the special cards the player has.
     * @param playableCards the cards in the player's hand.
     */
    void showRound(BlindInfo info, BlindStats stats, List<SpecialCardInfo> specialCards, List<PlayableCardInfo> playableCards);

    /**
     * Shows the blind defeated screen.
     */
    void showBlindDefeated();

    /**
     * Shows the game over screen.
     */
    void showGameOver();

    /**
     * Updates the player's hand.
     * @param playableCards
     */
    void updateHand(List<PlayableCardInfo> playableCards);

    /**
     * Updates the blind statistics.
     * @param stats remaining hands, discards and chips.
     */
    void updateBlindStatistics(BlindStats stats);

    /**
     * Updates the combination status.
     */
    void updateCombinationStatus();

    /**
     * Updates the score.
     */
    void updateScore();

    /**
     * Shows the played cards.
     */
    void updatePlayedCards();

    /**
     * Shows the shop with the given set of special cards to sell.
     *
     * @param toSell the set of special cards available for sale
     */
    void showShop(Set<SpecialCardInfo> toSell);

    /**
     * Updates the shop with the given set of special cards to sell.
     *
     * @param toSell the set of special cards available for sale
     */
    void updateShopCards(Set<SpecialCardInfo> toSell);

    /**
     * Updates the special cards.
     */
    void updateSpecialCards();

    /**
     * Shows the WINNING screen.
     */
    void showYouWon();
}
