package it.unibo.balatrolt.model.impl.combination;

import it.unibo.balatrolt.model.api.combination.Multiplier;

/**
 * Immutable classes for representing {@link Multiplier}.
 * Simply is a wrapper of a double that represents
 * the amount of multiplier scored.
 * @author Justin Carideo
 * @param multiplier
 */
public record MultiplierImpl(double multiplier) implements Multiplier {
}
