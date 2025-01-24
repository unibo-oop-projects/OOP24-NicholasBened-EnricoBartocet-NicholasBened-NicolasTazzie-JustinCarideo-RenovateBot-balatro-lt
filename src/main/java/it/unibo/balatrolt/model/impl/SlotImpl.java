package it.unibo.balatrolt.model.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.balatrolt.model.api.Card;
import it.unibo.balatrolt.model.api.Slot;

public class SlotImpl implements Slot{

    private List<Card> slot;

    public SlotImpl(int size) {
        this.slot = new ArrayList<>(size);
    }

    @Override
    public int getSize() {
        return this.slot.size();
    }

    @Override
    public void addCard(Card card) {
        assert(card != null);
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
        for (int i = 0; i < this.slot.size(); i++) {
            remove(i);
        }
    }

    @Override
    public void remove(Card card) {
        assert(card != null);
        this.slot.remove(card);
    }

    @Override
    public void remove(int index) {
        assert(index < this.slot.size());
        this.slot.remove(index);
    }
}
