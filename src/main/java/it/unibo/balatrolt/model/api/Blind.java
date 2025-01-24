package it.unibo.balatrolt.model.api;

public interface Blind {

    int getBlindNumber();

    int getMinimumChips();

    int getCurrentChips();

    void incrementChips(int handChips);

    boolean isOver();

    int getReward();
}
