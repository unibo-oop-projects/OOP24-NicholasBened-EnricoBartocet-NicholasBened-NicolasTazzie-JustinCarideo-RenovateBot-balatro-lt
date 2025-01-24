package it.unibo.balatrolt.model.impl;

import it.unibo.balatrolt.model.api.BasePoints;
import it.unibo.balatrolt.model.api.Chip;
import it.unibo.balatrolt.model.api.Combination;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.Multiplier;

public class CombinationImpl implements Combination {

    private Multiplier multiplier;
    private BasePoints points;
    private CombinationType type = CombinationType.HIGHCARD;

    public CombinationImpl(int points, double multiplier, CombinationType t) {
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
    public void applyModifier(Modifier mod) {
        if (mod.getBasePointMapper().isPresent()) {
            this.points = new BasePointsImpl(mod.getBasePointMapper().get().apply(this.points.basePoints()));
        }
        if (mod.getMultiplierMapper().isPresent()) {
            this.multiplier = new MultiplierImpl(mod.getMultiplierMapper().get().apply(this.multiplier.multiplier()));
        }
    }

    @Override
    public Chip getChips() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChips'");
    }

    @Override
    public CombinationType getCombinationType() {
        return this.type;
    }
}
