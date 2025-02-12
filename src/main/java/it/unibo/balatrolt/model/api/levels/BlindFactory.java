package it.unibo.balatrolt.model.api.levels;

import java.util.List;

/**
 * Provides some Factory Methods (according to the pattern) for creating {@link Blind} objects.
 */
public interface BlindFactory {

    /**
     * Creates a {@link Blind} without debuffs starting from its ID and the ID of its Ante.
     * @param anteId the ID of its Ante
     * @param blindId its ID
     * @return a new Blind
     */
    Blind baseFromIds(int anteId, int blindId);

    /**
     * Creates a {@link Blind} with debuffs starting from its ID and the ID of its Ante.
     * @param anteId the ID of its Ante
     * @param blindId its ID
     * @return a new Blind
     */
    Blind bossFromIds(int anteId, int blindId);

    /**
     * Creates a list of Blinds.
     * All the blinds are base blinds, except the last one that is a boss blind.
     * @param numBlinds the number of the Blinds
     * @param anteId the ID of theirs Ante
     * @return a List with the given number of Blinds
     */
    List<Blind> createList(int numBlinds, int anteId);
}
