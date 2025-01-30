package it.unibo.balatrolt.controller.api;

import it.unibo.balatrolt.model.api.PlayableCard;

public interface HandController {
    /**
     * @return
     */
    boolean hasNext();

    /**
     * @return
     */
    PlayableCard next();

    /**
     *
     * @return the number of slots in the hand.
     */
    int numHandSlot();
}
