package it.unibo.balatrolt.model.api;

import java.util.List;

import it.unibo.balatrolt.model.api.Combination.CombinationType;

/**
 * Interface modelling the concept of creating
 * Combination objects given the type of combination
 * and the hand played.
 */
@FunctionalInterface
public interface CombinationCalculator {

    /**
     * compute the combination using the parameters given.
     * @param type of combination
     * @param hand played
     * @return the combination with points scored
     */
    public Combination compute(CombinationType type, List<PlayableCard> hand);
}
