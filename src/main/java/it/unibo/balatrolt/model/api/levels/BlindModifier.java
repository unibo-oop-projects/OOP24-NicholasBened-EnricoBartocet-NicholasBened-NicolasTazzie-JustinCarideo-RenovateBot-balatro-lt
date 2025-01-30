package it.unibo.balatrolt.model.api.levels;

/**
 * A BlindModifier is used to change the default values of a {@link Blind}.
 */
public interface BlindModifier {

    /**
     * Compute the new number of hands that the player will have.
     * @param hands the old number of hands
     * @return the new number of hands
     */
    int getNewHands(int hands);

    /**
     * Compute the new number of discards that the player will have.
     * @param discards the old number of discards
     * @return the new number of discards
     */
    int getNewDiscards(int discards);

    /**
     * Compute the number of chips that will be given to the player.
     * @param chips the original number of chips
     * @return the new number of chips
     */
    int getNewChips(int chips);
}
