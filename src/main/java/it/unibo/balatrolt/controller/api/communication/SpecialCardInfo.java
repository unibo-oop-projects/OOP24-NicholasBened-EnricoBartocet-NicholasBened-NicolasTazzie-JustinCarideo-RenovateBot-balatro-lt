package it.unibo.balatrolt.controller.api.communication;

/**
 * Record used to communicate {@link SpecialCard} info between the controller and the view.
 */
public record SpecialCardInfo(String name, String description, int price) {

}
