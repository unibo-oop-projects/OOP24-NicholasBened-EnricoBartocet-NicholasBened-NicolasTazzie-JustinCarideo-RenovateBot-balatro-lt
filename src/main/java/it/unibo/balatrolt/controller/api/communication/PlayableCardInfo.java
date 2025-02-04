package it.unibo.balatrolt.controller.api.communication;

/**
 * Class used to communicate the info of a Playable Card.
 * @param rank the rank of the card
 * @param suit the suit of the card
 */
public record PlayableCardInfo(String rank, String suit) {

}
