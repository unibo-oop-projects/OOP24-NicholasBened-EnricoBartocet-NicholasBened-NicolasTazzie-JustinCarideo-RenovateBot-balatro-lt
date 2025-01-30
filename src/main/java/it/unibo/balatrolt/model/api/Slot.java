package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * It models the concept of a Slot, where we
 * can place any Card we want.
 * @author Benedetti Nicholas
 */
public interface Slot {


    /**
     * @return the elements of the Slot.
     */
    int getSize();

    /**
     * @return how many elements the Slot can hold.
     */
    int getCapacity();

    /**
     * @param card to add in the Slot.
     */
    void addCard(Card card);

    /**
     * @param cards list containing the cards to add in the Slot.
     *
     * @throws IllegalArgumentException if List.size() > Slot.size()
     */
    void addAll(List<? extends Card> cards);

    /**
     * removes every element in the slot.
     */
    void clear();

    /**
     * It removes the element given, otherwise it'll do nothing.
     * The card given will be compared using equals() method.
     *
     * @param card
     */
    void remove(Card card);

    /**
     * Removes the element at the given index.
     *
     * @param index
     *
     * @throws IndexOutOfBoundException
     */
    void remove(int index);

    /**
     * @return the list of the cards contained in the Slot.
     */
    List<Card> getCards();
}
