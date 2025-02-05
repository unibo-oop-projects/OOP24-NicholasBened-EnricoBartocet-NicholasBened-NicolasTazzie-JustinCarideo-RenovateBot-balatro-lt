package it.unibo.balatrolt.model.impl.cards;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.Slot;
import it.unibo.balatrolt.model.api.cards.Card;

/**
 * Implements Slot interface.
 * @author Benedetti Nicholas
 */
public final class SlotImpl implements Slot {
    private final int capacity;
    private final List<Card> slot;

    /**
     * Generates a Slot with the given capacity.
     *
     * @param capacity of the Slot.
     */
    public SlotImpl(final int capacity) {
        this.capacity = capacity;
        this.slot = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return this.slot.size();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void addCard(final Card card) {
        Preconditions.checkState(getSize() + 1 <= getCapacity(),
        "The slot is full, cannot add the card", card);
        this.slot.add(card);
    }

    @Override
    public void addAll(final List<? extends Card> cards) {
        for (final Card card : cards) {
            addCard(card);
        }
    }

    @Override
    public List<? extends Card> getCards() {
        return List.copyOf(this.slot);
    }

    @Override
    public void clear() {
        this.slot.clear();
    }

    @Override
    public void remove(final Card card) {
        this.slot.remove(card);
    }

    @Override
    public void remove(final int index) {
        this.slot.remove(index);
    }
}
