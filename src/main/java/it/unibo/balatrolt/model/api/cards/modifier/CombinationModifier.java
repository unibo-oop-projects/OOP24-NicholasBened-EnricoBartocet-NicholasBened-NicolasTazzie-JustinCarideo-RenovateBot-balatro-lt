package it.unibo.balatrolt.model.api.cards.modifier;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.combination.BasePoints;
import it.unibo.balatrolt.model.api.combination.Multiplier;

/**
 * Interface modelling the concept of Multiplier.
 * Essentially it supplies UnaryOperators mapping BasePoints and Multipliers when some conditions are verified.
 */
public interface CombinationModifier {
    /**
     * Getter for {@link Multiplier} mapper.
     * Before calling this method setGameStatus() should be called.
     * @return Optional.empty() if conditions are not verified, otherwise an UnaryOperator.
     * mapping the new value a {@link Multiplier} should have
     * @throws IllegalStateException if the Modifier value is inconsistent (e.g. when the game status is not set before
     * calling the method)
     */
    Optional<UnaryOperator<Double>> getMultiplierMapper();

    /**
     * Getter for {@link BasePoints} mapper.
     * Before calling this method setGameStatus() should be called.
     * @return Optional.empty() if conditions are not verified, otherwise an UnaryOperator.
     * mapping the new value a {@link BasePoints} should have
     * @throws IllegalStateException if the Modifier value is inconsistent (e.g. when the game status is not set before
     * calling the method)
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
     * It's used to concatenate results of different mofi
     * @return true if can be applied
     */
    boolean canApply();
}
