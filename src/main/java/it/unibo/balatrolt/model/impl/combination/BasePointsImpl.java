package it.unibo.balatrolt.model.impl.combination;

import it.unibo.balatrolt.model.api.combination.BasePoints;

/**
 * Immutable classes for representing BasePoints.
 * Simply is a wrapper of intergers that represents
 * the amount of points scored.
 * @author Justin Carideo
 */
public record BasePointsImpl(int basePoints) implements BasePoints{

}
