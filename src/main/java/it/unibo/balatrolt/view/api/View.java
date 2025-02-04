package it.unibo.balatrolt.view.api;

import java.util.List;
import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;

/**
 * Interface representing the view in the application.
 */
public interface View {

    /**
     *
     * @param title error title.
     * @param desc error message.
     */
    void notifyErrror(String title, String desc);

    /**
     * Shows the main menu.
     */
    void showMainMenu();

    /**
     * Shows the decks to choose from.
     * @param decks to choose from.
     */
    void showDecks(List<DeckInfo> decks);

    /**
     * Shows the ante information: the current blind,
     * current ante, the number of the ante's blinds ecc..
     * @param anteInfo the ante information.
     */
    void showAnte(AnteInfo anteInfo);

    /**
     * Starts the actual game.
     * @param playableCards the cards in the player's hand.
     */
    void showRound(List<PlayableCardInfo> playableCards);

    /**
     * Shows the information about the actual game and the cards owned by the player.
     * @param info static information of the actual game.
     * @param stats statistics of the actual game.
     * @param specialCards owned special cards.
     * @param deck selected deck.
     * @param numAnte number of antes.
     */
    void showSettings(BlindInfo info, BlindStats stats, List<SpecialCardInfo> specialCards, DeckInfo deck, int numAnte);

    /**
     * Updates the score in UI.
     * @param stats blind actual statistics.
     */
    void updateScore(BlindStats stats);

    /**
     * Updates the special cards.
     */
    void updateSpecialCards(List<SpecialCardInfo> specialCards);

    /**
     * Updates the player's hand.
     * @param playableCards
     */
    void updateGameTable(List<PlayableCardInfo> playableCards, BlindStats stats);

    /**
     * Updates the combination status.
     */
    void updateCombinationStatus(CombinationInfo combination);

    /**
     * Shows the blind defeated screen.
     */
    void showBlindDefeated(BlindInfo blindInfo, BlindStats blindStats);

    void updateCurrency(int currency);

    void updateAnteInfo(AnteInfo ante);

    /**
     * Shows the shop.
     */
    void showShop();

    /**
     * Updates the shop with the given set of special cards to sell.
     *
     * @param toSell the set of special cards available for sale
     */
    void updateShopCards(Set<SpecialCardInfo> toSell);

    /**
     * Shows the game over screen.
     */
    void showGameOver();

    /**
     * Shows the WINNING screen.
     */
    void showYouWon();
}
