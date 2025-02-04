package it.unibo.balatrolt.controller.api.communication;

/**
 * Class used to comunicate the statistics of a Blind.
 * @param chips the amount of chips that the player has earned so far
 * @param hands the number of remaining hand to play
 * @param discards the number of remaining discards
 */
public record BlindStats(int chips, int hands, int discards) {

}
