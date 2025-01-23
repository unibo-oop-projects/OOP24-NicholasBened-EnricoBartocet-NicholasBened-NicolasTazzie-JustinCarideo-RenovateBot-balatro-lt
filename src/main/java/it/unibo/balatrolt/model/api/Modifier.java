package it.unibo.balatrolt.model.api;

import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

/**
 * Interface modelling the of Multiplier.
 * Essentially it supplies UnaryOperators mapping BasePoints and Multipliers when some conditions are verified.
 */
public interface Modifier {
    /**
     * @return Optional.empty() if conditions are not verified, otherwise an UnaryOperator. 
     * mapping the new value a {@link Multiplier} should have
     */
    Optional<UnaryOperator<Multiplier>> getMultiplierMapper();

    /**
     * @return Optional.empty() if conditions are not verified, otherwise an UnaryOperator.
     * mapping the new value {@link BasePoints} should have
     */
    Optional<UnaryOperator<BasePoints>> getBasePointMapper();
}
