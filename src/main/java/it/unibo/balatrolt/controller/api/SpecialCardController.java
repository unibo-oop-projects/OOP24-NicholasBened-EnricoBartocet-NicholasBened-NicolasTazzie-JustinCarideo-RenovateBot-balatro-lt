package it.unibo.balatrolt.controller.api;

import it.unibo.balatrolt.model.api.SpecialCard;

public interface SpecialCardController {

    /**
     *
     * @return
     */
    boolean hasNext();
    /**
     *
     * @return the next special card.
     */
    SpecialCard next();
}
