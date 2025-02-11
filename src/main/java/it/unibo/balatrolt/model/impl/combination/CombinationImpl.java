package it.unibo.balatrolt.model.impl.combination;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.combination.BasePoints;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.Multiplier;

/**
 * Classes that represents the concept of {@link Combination}.
 * A combination is represented as an object that has an amount
 * of points (points scored with combination + points that depends on
 * the hand played, check {@link PlayedHand} for more details) and a
 * multiplier (multiplier scored with the combination).
 * This class can be modify only with {@link applyModifier} method.
 * @author Justin Carideo
 */
public final class CombinationImpl implements Combination {

    private static final int EMPTY_VALUE = 0;
    private final CombinationType type;
    private Multiplier multiplier;
    private BasePoints points;

    /**
     * Constructor for declaring a combination.
     * @param points
     * @param multiplier
     * @param t
     */
    public CombinationImpl(final int points, final double multiplier, final CombinationType t) {
        Preconditions.checkArgument(points >= EMPTY_VALUE, "Points can't negative");
        Preconditions.checkArgument(multiplier >= EMPTY_VALUE, "Multiplier can't negative");
        this.multiplier = new MultiplierImpl(multiplier);
        this.type = t;
        this.points = new BasePointsImpl(points);
    }

    @Override
    public Multiplier getMultiplier() {
        return this.multiplier;
    }

    @Override
    public BasePoints getBasePoints() {
        return this.points;
    }

    @Override
    public void applyModifier(final CombinationModifier mod) {
        final var multiplierMapper = mod.getMultiplierMapper();
        final var basePointsMapper = mod.getBasePointMapper();
        if (basePointsMapper.isPresent()) {
            this.points = new BasePointsImpl(basePointsMapper.get().apply(this.points.basePoints()));
        }
        if (multiplierMapper.isPresent()) {
            this.multiplier = new MultiplierImpl(multiplierMapper.get().apply(this.multiplier.multiplier()));
        }
    }

    @Override
    public int getChips() {
        return (int) Math.round(this.points.basePoints() * this.multiplier.multiplier());
    }

    @Override
    public CombinationType getCombinationType() {
        return this.type;
    }
}
