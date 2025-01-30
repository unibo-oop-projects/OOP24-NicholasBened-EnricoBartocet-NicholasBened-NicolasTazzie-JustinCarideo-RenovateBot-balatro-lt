package it.unibo.balatrolt.controller.api;

import java.util.List;

/**
 * Controls every aspect of the player's hand
 */
public interface HandController {

    /**
     *
     * @return the number of card the player can hold.
     */
    int numHandSlot();

    /**
     *
     * @return  the name of the cards in the player's hand.
     */
    List<String> getHand();
}
