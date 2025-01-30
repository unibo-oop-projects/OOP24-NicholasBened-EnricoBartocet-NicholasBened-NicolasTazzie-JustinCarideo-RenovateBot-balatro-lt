package it.unibo.balatrolt.model.impl.levels;

import java.util.function.UnaryOperator;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.levels.BlindModifier;

/**
 * An implementation of the interface {@link BlindModifier} that follows the Strategy pattern.
 * @author Bartocetti Enrico
 */
public final class BlindModifierImpl implements BlindModifier {

    private final UnaryOperator<Integer> handsCalculator;
    private final UnaryOperator<Integer> discardsCalculator;
    private final UnaryOperator<Integer> chipsCalculator;

    /**
     * Create the modifier using the following strategies.
     * @param hand the function that changes the default hands number
     * @param discard the function that changes the default discards number
     * @param chip the function that changes the chips earned every time an hand is played
     */
    public BlindModifierImpl(
        final UnaryOperator<Integer> hand,
        final UnaryOperator<Integer> discard,
        final UnaryOperator<Integer> chip
    ) {
        this.handsCalculator = Preconditions.checkNotNull(hand);
        this.discardsCalculator = Preconditions.checkNotNull(discard);
        this.chipsCalculator = Preconditions.checkNotNull(chip);
    }

    @Override
    public int getNewHands(final int hands) {
        return this.handsCalculator.apply(hands);
    }

    @Override
    public int getNewDiscards(final int discards) {
        return this.discardsCalculator.apply(discards);
    }

    @Override
    public int getNewChips(final int chips) {
        return this.chipsCalculator.apply(chips);
    }

}
