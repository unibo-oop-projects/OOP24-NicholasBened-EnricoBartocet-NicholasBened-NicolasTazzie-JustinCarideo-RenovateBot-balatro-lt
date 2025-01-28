package it.unibo.balatrolt.model.impl;

import it.unibo.balatrolt.model.api.Multiplier;

/**
 * Immutable classes for representing Multiplier.
 * Simply is a wrapper of a double that represents
 * the amount of multiplier scored.
 */
public record MultiplierImpl(double multiplier) implements Multiplier{

}
