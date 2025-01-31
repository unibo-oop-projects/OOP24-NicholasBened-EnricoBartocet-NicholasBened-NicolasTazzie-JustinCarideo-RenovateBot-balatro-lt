package it.unibo.balatrolt.model.api.levels;

/**
 * Represents the static characteristics of a {@link Blind}.
 */
public interface BlindConfiguration {

    /**
     * @return the id of the blind
     */
    int id();

    /**
     * @return the minium chips required to defeat the Blind
     */
    int baseChip();

    /**
     * @return the reward which will be awarded to the player if he defeats the blind
     */
    int reward();
}
