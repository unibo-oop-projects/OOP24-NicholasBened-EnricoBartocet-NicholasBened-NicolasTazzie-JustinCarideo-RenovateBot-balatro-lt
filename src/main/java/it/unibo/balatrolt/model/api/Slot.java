package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * It models the concept of a Slot, where we
 * can place any Card we want.
 */
public interface Slot {


    /**
     * @return the size of the Slot.
     */
    int getSize();

    /**
     * @param card, to add in the Slot.
     */
    void addCard(Card card);

    /**
     * @param cards, list containing the cards to add in the Slot.
     *
     * @throws
     */
    void addAll(List<? extends Card> cards);

    /**
     * @return the list of the cards contained in the Slot.
     */
    List<Card> getCards();
}
