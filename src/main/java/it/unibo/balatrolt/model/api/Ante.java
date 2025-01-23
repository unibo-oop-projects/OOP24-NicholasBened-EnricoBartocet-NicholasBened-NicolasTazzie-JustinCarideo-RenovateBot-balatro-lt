package it.unibo.balatrolt.model.api;

import java.util.List;

/**
 * Interface used to represent an Ante.
 */
public interface Ante {

    int getAnteNumber();

    void nextAnte();

    Blind getCurrentBlind();

    List<Blind> getBlinds();
}
