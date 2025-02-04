package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.Map;
import java.util.function.UnaryOperator;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Catalog of base {@link Joker} with {@link JokerTier} epic.
 */
public final class JokerCatalogBase extends AbstractJokerCatalog {
    private final static JokerTier TIER = JokerTier.EPIC;

    public JokerCatalogBase() {
        super();
    }

    private Joker fiveBasePoints() {
        return super.getFactory().withModifierAndRandomPrice(
            "the fifth point",
         "it adds 5 base points",
         this.getBasePointModifier(b -> b + 5),
         TIER);
    }

    private Joker tenBasePoints() {
        return super.getFactory().withModifierAndRandomPrice(
            "the tenth point",
         "it adds 10 base points",
         this.getBasePointModifier(b -> b + 10),
         TIER);
    }

    private Joker twoMultiplier() {
        return super.getFactory().withModifierAndRandomPrice(
            "the second multiplier",
         "it adds 2 multiplier",
         this.getMultiplierModifier(m -> m + 2),
         TIER);
    }

    private Joker fourMultiplier() {
        return super.getFactory().withModifierAndRandomPrice(
            "the fourth multiplier",
         "it adds 4 multiplier",
         getMultiplierModifier(m -> m + 4),
         TIER);
    }

    private Joker eighthMultiplier() {
        return super.getFactory().withModifierAndRandomPrice(
            "the eighth multiplier",
         "it adds 8 multiplier",
         getMultiplierModifier(m -> m + 8),
         TIER);
    }

    private CombinationModifier getMultiplierModifier(final UnaryOperator<Double> op) {
        return new ModifierBuilderImpl()
         .addMultiplierModifier(op)
         .build();
    }

    private CombinationModifier getBasePointModifier(final UnaryOperator<Integer> op) {
        return new ModifierBuilderImpl()
         .addBasePointsModifier(op)
         .build();
    }

    @Override
    protected Map<String, Joker> getJokersMap() {
        return Map.of(
            fiveBasePoints().getName(), fiveBasePoints(),
            tenBasePoints().getName(), tenBasePoints(),
            twoMultiplier().getName(), twoMultiplier(),
            fourMultiplier().getName(), fourMultiplier(),
            eighthMultiplier().getName(), eighthMultiplier()
        );
    }
}
