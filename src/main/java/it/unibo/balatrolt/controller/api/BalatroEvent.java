package it.unibo.balatrolt.controller.api;

import java.util.Set;

/**
 * It represents the events that the view can generate.
 */
public enum BalatroEvent {
    /**
     * TODO: add description.
     */
    MAIN_MENU,
    /**
     * The user started a new game.
     */
    INIT_GAME,
    /**
     * The deck was chosen.
     */
    CHOOSE_DECK,
    /**
     * The Blind to play was chosen.
     */
    CHOOSE_BLIND,
    /**
     * The player discarded some cards.
     */
    DISCARD_CARDS,
    /**
     * The player played some cards.
     */
    PLAY_CARDS,
    STAGE_CARDS,
    /**
     * The player opened the shop.
     */
    OPEN_SHOP,
    /**
     * The player bought a card from the shop.
     */
    BUY_CARD,
    SELL_CARD,
    /**
     * Th eplayer closed the shop.
     */
    CLOSE_SHOP;

    /**
     * @return returns the set of the allowed events that can happen after everyone
     */
    public Set<BalatroEvent> getNextPossibleEvents() {
        return switch (this) {
            case MAIN_MENU -> Set.of(INIT_GAME);
            case INIT_GAME -> Set.of(CHOOSE_DECK);
            case CHOOSE_DECK -> Set.of(CHOOSE_BLIND, SELL_CARD);
            case CHOOSE_BLIND, DISCARD_CARDS -> Set.of(STAGE_CARDS, SELL_CARD);
            case STAGE_CARDS -> Set.of(PLAY_CARDS, DISCARD_CARDS ,STAGE_CARDS, SELL_CARD);
            case PLAY_CARDS -> Set.of(STAGE_CARDS, OPEN_SHOP, MAIN_MENU, SELL_CARD);
            case BUY_CARD, OPEN_SHOP -> Set.of(BUY_CARD, CLOSE_SHOP, SELL_CARD);
            case CLOSE_SHOP -> Set.of(CHOOSE_BLIND, SELL_CARD);
            case SELL_CARD -> Set.of(BalatroEvent.values());
        };
    }
}
