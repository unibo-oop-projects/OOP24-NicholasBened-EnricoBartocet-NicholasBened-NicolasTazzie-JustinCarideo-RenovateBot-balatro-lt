package it.unibo.balatrolt.model.impl.combination;

import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.combination.BasePoints;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.api.combination.Multiplier;

/**
 * Classes that represents the concept of combination.
 * A combination is represented as an object that has an amount
 * of points (points scored with combination + points that depends on
 * the hand played, check PlayedHandImpl for more details) and a
 * multiplier (multiplier scored with the combination).
 * This class can be modify only with applyModifier method.
 * @author Justin Carideo
 */
public class CombinationImpl implements Combination {

    private Multiplier multiplier;
    private BasePoints points;
    private CombinationType type = CombinationType.HIGH_CARD;

    public CombinationImpl(final int points, final double multiplier, final CombinationType t) {
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
    public void applyModifier(final Modifier mod) {
        var multiplierMapper = mod.getMultiplierMapper();
        var basePointsMapper = mod.getBasePointMapper();
        if (basePointsMapper.isPresent()) {
            this.points = new BasePointsImpl(basePointsMapper.get().apply(this.points.basePoints()));
        }
        if (multiplierMapper.isPresent()) {
            this.multiplier = new MultiplierImpl(multiplierMapper.get().apply(this.multiplier.multiplier()));
        }
    }

    @Override
    public int getChips() {
        return (int) Math.round((double)this.points.basePoints() * this.multiplier.multiplier());
    }

    @Override
    public CombinationType getCombinationType() {
        return this.type;
    }
}
