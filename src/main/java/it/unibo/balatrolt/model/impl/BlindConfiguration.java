package it.unibo.balatrolt.model.impl;

/**
 * Represents the characteristics of a {@link it.unibo.balatrolt.model.api.Blind}.
 * @author @EnryBarto
 * @param id the id of the Blind
 * @param baseChip the minium of chips required to defeat the Blind
 * @param reward the reward which will be awarded to the player if he defeats the blind
 */
public record BlindConfiguration(int id, int baseChip, int reward) {
}
