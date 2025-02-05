package it.unibo.balatrolt.controller.api;

import java.util.Set;

/**
 * It represents the events that the views can generate and that the Controller will handle.
 */
public enum BalatroEvent {
    /**
     * Notify the views to show the main menu.
     */
    MAIN_MENU,
    /**
     * Notify the views to show the decks.
     */
    INIT_GAME,
    /**
     * The deck was chosen.
     */
    CHOOSE_DECK,
    /**
     * The player confirmed the start the Blind.
     */
    SHOW_BLINDS,
    /**
     * The player discarded some cards.
     */
    DISCARD_CARDS,
    /**
     * The player played some cards.
     */
    PLAY_CARDS,
    /**
     * The player added some cards to the stage.
     */
    STAGE_CARDS,
    /**
     * The player wants to sort hand by rank.
     */
    SORT_BY_RANK,
    /**
     * The player wantsa to sort hand by suit.
     */
    SORT_BY_SUIT,
    /**
     * The player opened the shop.
     */
    OPEN_SHOP,
    /**
     * The player bought a card from the shop.
     */
    BUY_CARD,
    /**
     * The player sold a card.
     */
    SELL_CARD,
    /**
     * Th player closed the shop.
     */
    CLOSE_SHOP;

    /**
     * Every event has a set of possible events that can happen after it.
     * @return returns the set of the allowed events that can happen after every event.
     */
    public Set<BalatroEvent> getNextPossibleEvents() {
        return switch (this) {
            case MAIN_MENU -> Set.of(INIT_GAME);
            case INIT_GAME -> Set.of(CHOOSE_DECK);
            case CHOOSE_DECK, CLOSE_SHOP -> Set.of(SHOW_BLINDS, SELL_CARD);
            case SHOW_BLINDS, DISCARD_CARDS -> Set.of(
                STAGE_CARDS,
                SELL_CARD,
                SORT_BY_RANK,
                SORT_BY_SUIT
            );
            case STAGE_CARDS -> Set.of(
                PLAY_CARDS,
                DISCARD_CARDS,
                STAGE_CARDS,
                SELL_CARD
            );
            case PLAY_CARDS, SORT_BY_RANK, SORT_BY_SUIT -> Set.of(
                STAGE_CARDS,
                OPEN_SHOP,
                MAIN_MENU,
                SELL_CARD,
                SORT_BY_RANK,
                SORT_BY_SUIT
            );
            case BUY_CARD, OPEN_SHOP -> Set.of(BUY_CARD, CLOSE_SHOP, SELL_CARD);
            case SELL_CARD -> Set.of(values());
        };
    }
}
