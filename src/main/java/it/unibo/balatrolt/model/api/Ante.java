package it.unibo.balatrolt.model.api;

import java.util.List;

import com.google.common.base.Optional;

/**
 * Interface used to represent an Ante.
 */
public interface Ante {

    /**
     * Returns the number of the Ante.
     * @return the number of the Ante
     */
    int getAnteNumber();

    /**
     * Returns the list of the {@link Blind}.
     * @return a list containing the Blinds for this Ante
     */
    List<Blind> getBlinds();

    /**
     * Returns an Optional containing the current {@link Blind}.
     * If the Ante is over (so it isn't poiting to any Blind) returns an empty Optional.
     * @return the current Blind which the Ante is pointing to
     */
    Optional<Blind> getCurrentBlind();

    /**
     * Makes the Ante point to the next Blind.
     */
    void nextBlind();

    /**
     * Tells if the Ante is over.
     * @return true if the Ante is over, false otherwise
     */
    boolean isOver();
}
