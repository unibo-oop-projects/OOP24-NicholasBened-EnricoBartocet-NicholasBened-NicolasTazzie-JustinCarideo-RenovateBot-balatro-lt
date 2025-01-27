package it.unibo.balatrolt.model.impl;

/**
 * Simple Pair class of two generic types.
 * @param <X> First generic class
 * @param <Y> Second generic class
 * @param e1 First object of the pair
 * @param e2 Second object of the pair
 */
public record Pair<X, Y>(X e1, Y e2) {

}
