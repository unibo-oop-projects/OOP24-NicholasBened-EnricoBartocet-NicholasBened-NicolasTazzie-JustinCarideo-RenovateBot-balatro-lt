package it.unibo.balatrolt.model.api;

public interface Player {

    Deck getDeck();

    Slot getHandSlot();

    Slot getSpecialCardSlot();

    void addCurrency(Currency value);

    Currency getCurrency();

    Ante getCurrentAnte();

}
