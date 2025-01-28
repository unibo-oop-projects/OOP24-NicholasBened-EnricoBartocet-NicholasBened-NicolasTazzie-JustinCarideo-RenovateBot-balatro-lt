package it.unibo.balatrolt.model.impl.levels;

import com.google.common.base.Preconditions;

/**
 * Represents the characteristics of a {@link it.unibo.balatrolt.model.api.levels.Blind}.
 * @author Bartocetti Enrico
 * @param id the id of the Blind
 * @param baseChip the minium of chips required to defeat the Blind
 * @param reward the reward which will be awarded to the player if he defeats the blind
 */
public record BlindConfiguration(int id, int baseChip, int reward) {
    /**
     * Check that the chips required to defeat the blind and the reward aren't negative.
     */
    public BlindConfiguration {
        Preconditions.checkArgument(baseChip >= 0, "The chips required to defeat the blind and the reward cannot be negative");
        Preconditions.checkArgument(reward >= 0, "The reward cannot be negative");
    }
}
