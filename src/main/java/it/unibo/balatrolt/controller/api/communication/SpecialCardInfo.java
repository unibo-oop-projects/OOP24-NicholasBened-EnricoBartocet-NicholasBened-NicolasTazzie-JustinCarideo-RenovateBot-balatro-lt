package it.unibo.balatrolt.controller.api.communication;

/**
 * Record used to communicate the info of a Special Card.
 * @param name the name of the card
 * @param description the description of the card
 * @param price the price of the card
 */
public record SpecialCardInfo(String name, String description, int price) {

}
