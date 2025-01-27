package it.unibo.balatrolt.model.impl.levels;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.Blind;

/**
 * Represents the characteristics of an {@link Ante}.
 * @author Bartocetti Enrico
 * @param id the id of the Ante
 * @param numBlinds the number of {@link Blind} present in the Ante
 * @param baseChipCalc the function that computes the minimum chips required to defeat the Blind, given the Ante ID and Blind ID
 * @param rewardCalc the function that computes the reward given when defeating the Blind, given the Blind ID
 */
public record AnteConfiguration(
    int id,
    int numBlinds,
    BinaryOperator<Integer> baseChipCalc,
    UnaryOperator<Integer> rewardCalc
) {
    /**
     * Check that the functions aren't null and the number of blinds is positive.
     */
    public AnteConfiguration {
        if (rewardCalc == null || baseChipCalc == null) {
            throw new IllegalArgumentException(new NullPointerException());
        }
        if (numBlinds <= 0) {
            throw new IllegalArgumentException("The Ante must contain at least 1 Blind");
        }
    }
}
