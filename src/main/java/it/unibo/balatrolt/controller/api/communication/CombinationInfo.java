package it.unibo.balatrolt.controller.api.communication;

/**
 * Class used to comunicate the a Combination.
 * @param name the name of the combination
 * @param points the base points given by the combination
 * @param multiplier the multiplier given by the combination
 */
public record CombinationInfo(String name, int points, double multiplier) {

}
