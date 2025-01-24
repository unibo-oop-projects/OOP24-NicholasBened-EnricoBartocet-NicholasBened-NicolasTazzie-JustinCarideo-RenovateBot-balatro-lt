package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * Interface used to represent an Ante.
 */
public interface Ante {

    int getAnteNumber();

    List<Blind> getBlinds();

    Blind getCurrentBlind();

    void nextBlind();

    boolean isOver();
}
