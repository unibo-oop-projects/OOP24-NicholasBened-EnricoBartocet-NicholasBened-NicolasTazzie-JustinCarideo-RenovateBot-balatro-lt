package it.unibo.balatrolt.model.api.levels;

import java.util.List;

/**
 * Provides some Factory Methods (according to the pattern) for creating {@link Ante} objects.
 */
public interface AnteFactory {

    /**
     * Creates an Ante starting from its ID.
     * @param id the ID of the Ante
     * @return an Ante with the specified ID
     */
    Ante fromId(int id);

    /**
     * Returns a list whose length is decided by the user.
     * The IDs start from 0 and are progressive.
     * @param size the size of the list
     * @return a list of Ante of the specified length
     */
    List<Ante> generateList(int size);
}
