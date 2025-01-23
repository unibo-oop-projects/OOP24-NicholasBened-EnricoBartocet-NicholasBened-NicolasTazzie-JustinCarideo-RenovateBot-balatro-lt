package it.unibo.balatrolt.model.api;

import java.util.List;

public interface Ante {

    int getAnteNumber();

    void nextAnte();

    Blind getCurrentBlind();

    List<Blind> getBlinds();
}
