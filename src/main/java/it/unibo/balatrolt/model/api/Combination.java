package it.unibo.balatrolt.model.api;

public interface Combination {

    Multiplier getMultiplier();

    BasePoints getBasePoints();

    void applyModifier(Modifier mod);

    Chip getChips();
}
