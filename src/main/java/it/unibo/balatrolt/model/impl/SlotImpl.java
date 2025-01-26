package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.balatrolt.model.api.Card;
import it.unibo.balatrolt.model.api.Slot;

public class SlotImpl implements Slot{
    private final int size;
    private List<Card> slot;

    public SlotImpl(int size) {
        this.size = size;
        this.slot = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return this.slot.size();
    }

    @Override
    public int getCapacity() {
        return this.size;
    }

    @Override
    public void addCard(Card card) {
        try {
            assert(getSize() + 1 <= getCapacity());
        } catch (AssertionError e) {
            throw new IllegalArgumentException("The Slot is full! ");
        }
        this.slot.add(card);
    }

    @Override
    public void addAll(List<? extends Card> cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }

    @Override
    public List<Card> getCards() {
        return List.copyOf(this.slot);
    }

    @Override
    public void clear() {
        this.slot.clear();
    }

    @Override
    public void remove(Card card) {
        this.slot.remove(card);
    }

    @Override
    public void remove(int index) {
        this.slot.remove(index);
    }
}
