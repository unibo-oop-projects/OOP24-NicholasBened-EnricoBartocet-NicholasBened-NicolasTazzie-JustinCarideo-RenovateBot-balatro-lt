package it.unibo.balatrolt.model.api;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;
import com.google.errorprone.annotations.DoNotCall;

/**
 * Interface modelling the concept of Multiplier. 
 * Essentially it supplies UnaryOperators mapping BasePoints and Multipliers when some conditions are verified.
 */
public interface Modifier {
    /**
     * @return Optional.empty() if conditions are not verified, otherwise an UnaryOperator. 
     * mapping the new value a {@link Multiplier} should have
     */
    Optional<UnaryOperator<Double>> getMultiplierMapper();

    /**
     * @return Optional.empty() if conditions are not verified, otherwise an UnaryOperator.
     * mapping the new value {@link BasePoints} should have
     */
    Optional<UnaryOperator<Integer>> getBasePointMapper();

    /**
     * It sets the current game status in the modifier.
     * It's used to check if certain conditions are satisfied and a modifier should be applicated
     * @param stats current game stats.
     */
    void setGameStatus(ModifierStatsSupplier stats);

    /**
     * Returns whether the modifier can be applied or not.
     * It's used to concatenate results of different modifiers
     * @return true if can be applied
     */
    @DoNotCall
    boolean canApply();
}
