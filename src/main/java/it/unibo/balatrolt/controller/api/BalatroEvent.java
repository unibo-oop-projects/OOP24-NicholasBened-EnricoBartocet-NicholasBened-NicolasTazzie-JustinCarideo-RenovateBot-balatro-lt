package it.unibo.balatrolt.controller.api;

import java.util.Set;

/**
 * It represents the events that the view can generate.
 */
public enum BalatroEvent {
    CHOOSE_BLIND,
    CHOOSE_DECK,
    DISCARD_CARDS,
    PLAY_CARDS,
    BUY_CARD,
    OPEN_SHOP,
    CLOSE_SHOP;

    /**
     * @return returns the possible events th
     */
    public Set<BalatroEvent> getNextPossibleEvents() {
        return switch (this) {
            case CHOOSE_DECK -> Set.of(CHOOSE_BLIND);
            case CHOOSE_BLIND, DISCARD_CARDS -> Set.of(DISCARD_CARDS, PLAY_CARDS);
            case PLAY_CARDS -> Set.of(DISCARD_CARDS, PLAY_CARDS, OPEN_SHOP);
            case BUY_CARD, OPEN_SHOP -> Set.of(BUY_CARD, CLOSE_SHOP);
            case CLOSE_SHOP -> Set.of(CHOOSE_BLIND);
        };
    }
}
