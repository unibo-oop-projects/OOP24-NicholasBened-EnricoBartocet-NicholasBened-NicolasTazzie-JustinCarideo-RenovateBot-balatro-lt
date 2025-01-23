package it.unibo.balatrolt.model.api;

import java.util.List;

public interface Slot {

    int getSize();

    void addCard(Card card);

    List<Card> getCards();

}
