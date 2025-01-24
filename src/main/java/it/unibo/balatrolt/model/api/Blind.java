package it.unibo.balatrolt.model.api;

public interface Blind {

    int getBlindNumber();

    void playHand(PlayedHand hand);

    Chip getMinimunChips();

    Chip getCurrentChips();

    boolean isOver();

    Currency getEarnedCurrency();
}
