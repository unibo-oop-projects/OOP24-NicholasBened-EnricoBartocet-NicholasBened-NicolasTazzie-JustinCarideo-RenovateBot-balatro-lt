package it.unibo.balatrolt.model.impl.combination;

import it.unibo.balatrolt.model.api.combination.Multiplier;

/**
 * Immutable classes for representing Multiplier.
 * Simply is a wrapper of a double that represents
 * the amount of multiplier scored.
 * @author Justin Carideo
 */
public record MultiplierImpl(double multiplier) implements Multiplier{

}
