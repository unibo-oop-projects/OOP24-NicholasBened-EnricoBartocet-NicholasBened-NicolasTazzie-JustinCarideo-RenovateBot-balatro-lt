package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.Map;
import java.util.function.UnaryOperator;

import it.unibo.balatrolt.model.api.cards.modifier.CombinationModifier;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Catalog of base {@link Joker} with {@link JokerTier} epic or legendary.
 * @author Nicolas Tazzieri
 */
public final class JokerCatalogBase extends AbstractJokerCatalog {
    private static final int BP_FIVE = 5;
    private static final int BP_TEN = 10;
    private static final int MUL_TWO = 2;
    private static final int MUL_FOUR = 4;
    private static final int MUL_EIGHT = 8;
    private static final int MUL_MULTIPLICATOR = 2;
    private static final JokerTier EPIC = JokerTier.EPIC;
    private static final Integer DONOUR_ADDER = 50;

    private Joker fiveBasePoints() {
        return super.getFactory().withModifierAndRandomPrice(
            "the fifth point",
            "it adds 5 base points",
            this.getBasePointModifier(b -> b + BP_FIVE),
            EPIC);
    }

    private Joker tenBasePoints() {
        return super.getFactory().withModifierAndRandomPrice(
            "the tenth point",
            "it adds 10 base points",
            this.getBasePointModifier(b -> b + BP_TEN),
            EPIC);
    }

    private Joker twoMultiplier() {
        return super.getFactory().withModifierAndRandomPrice(
            "the second multiplier",
            "it adds 2 multiplier",
            this.getMultiplierModifier(m -> m + MUL_TWO),
            EPIC);
    }

    private Joker fourMultiplier() {
        return super.getFactory().withModifierAndRandomPrice(
            "the fourth multiplier",
            "it adds 4 multiplier",
            getMultiplierModifier(m -> m + MUL_FOUR),
            EPIC);
    }

    private Joker eighthMultiplier() {
        return super.getFactory().withModifierAndRandomPrice(
            "the eighth multiplier",
            "it adds 8 multiplier",
            getMultiplierModifier(m -> m + MUL_EIGHT),
            EPIC);
    }

    private Joker doubler() {
        return super.getFactory().withModifierAndRandomPrice(
            "the doubler",
            "It doubles the current value of multipler without checking any condition",
            new ModifierBuilderImpl()
                    .addMultiplierModifier(m -> m * MUL_MULTIPLICATOR)
                    .build(),
            JokerTier.LEGENDARY);
    }

    private Joker donour() {
        return super.getFactory().withModifierAndRandomPrice(
            "the donour",
            "It adds 50 base points",
            new ModifierBuilderImpl()
                    .addBasePointsModifier(p -> p + DONOUR_ADDER)
                    .build(),
            JokerTier.LEGENDARY);
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
            this.fiveBasePoints().getName(), this.fiveBasePoints(),
            this.tenBasePoints().getName(), this.tenBasePoints(),
            this.twoMultiplier().getName(), this.twoMultiplier(),
            this.fourMultiplier().getName(), this.fourMultiplier(),
            this.eighthMultiplier().getName(), this.eighthMultiplier(),
            this.donour().getName(), this.donour(),
            this.doubler().getName(), this.doubler());
    }
}
