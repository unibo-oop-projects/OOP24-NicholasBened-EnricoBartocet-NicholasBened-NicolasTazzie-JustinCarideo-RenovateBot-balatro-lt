package it.unibo.balatrolt.model.impl.modifier;

import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.BasePoints;
import it.unibo.balatrolt.model.api.Modifier;
import it.unibo.balatrolt.model.api.ModifierBuilder;
import it.unibo.balatrolt.model.api.Multiplier;
import it.unibo.balatrolt.model.api.PlayableCard;

/**
 * Builder implementation for Modifier.
 */
public class ModifierBuilderImpl implements ModifierBuilder {
    private Modifier modifier = new BaseModifier(Optional.absent(), Optional.absent());

    @Override
    public ModifierBuilder addMultiplierModifier(UnaryOperator<Multiplier> multiplierFun) {
        this.modifier = new BaseModifier(Optional.of(multiplierFun), modifier.getBasePointMapper());
        return this;
    }

    @Override
    public ModifierBuilder addBasePointsModifier(UnaryOperator<BasePoints> bPFun) {
        this.modifier = new BaseModifier(modifier.getMultiplierMapper(), Optional.of(bPFun));
        return this;
    }

    @Override
    public ModifierBuilder addPlayedCardBound(Predicate<Set<PlayableCard>> condition) {
        this.modifier = new ModifierWithCardCondition(this.modifier, condition);
        return this;
    }

    @Override
    public Modifier build() {
        return this.modifier;
    }
}
