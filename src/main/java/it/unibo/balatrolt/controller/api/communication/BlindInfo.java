package it.unibo.balatrolt.controller.api.communication;

/**
 * Class used to comunicate the informations of a Blind.
 * @param id the id of the Blind
 * @param minimumChips the minimum amount of chip that the player must have to defeat the Blind
 * @param reward the money given to the Player if he defeats the Blind
 */
public record BlindInfo(int id, int minimumChips, int reward) {

}
