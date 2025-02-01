package it.unibo.balatrolt.controller.api;

import java.util.Set;

/**
 * It represents the events that the view can generate.
 */
public enum BalatroEvent {
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
    /**
     * The player opened the shop.
     */
    OPEN_SHOP,
    /**
     * The player bought a card from the shop.
     */
    BUY_CARD,
    /**
     * Th eplayer closed the shop.
     */
    CLOSE_SHOP;

    /**
     * @return returns the set of the allowed events that can happen after everyone
     */
    public Set<BalatroEvent> getNextPossibleEvents() {
        return switch (this) {
            case INIT_GAME -> Set.of(CHOOSE_DECK);
            case CHOOSE_DECK -> Set.of(CHOOSE_BLIND);
            case CHOOSE_BLIND, DISCARD_CARDS -> Set.of(DISCARD_CARDS, PLAY_CARDS);
            case PLAY_CARDS -> Set.of(DISCARD_CARDS, PLAY_CARDS, OPEN_SHOP);
            case BUY_CARD, OPEN_SHOP -> Set.of(BUY_CARD, CLOSE_SHOP);
            case CLOSE_SHOP -> Set.of(CHOOSE_BLIND);
        };
    }
}
