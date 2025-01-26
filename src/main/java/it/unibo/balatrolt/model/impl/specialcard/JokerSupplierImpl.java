package it.unibo.balatrolt.model.impl.specialcard;

import java.util.Random;
import java.util.Set;

import it.unibo.balatrolt.model.api.Joker;
import it.unibo.balatrolt.model.api.JokerFactory;
import it.unibo.balatrolt.model.api.JokerSupplier;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.PlayableCard.Suit;
import it.unibo.balatrolt.model.impl.modifier.ModifierBuilderImpl;

/**
 * Joker supplier implementation.
 */
public final class JokerSupplierImpl implements JokerSupplier {
    private static final int NUM_METHODS = 4;
    private final JokerFactory factory = new JokerFactoryImpl();
    private final Random r = new Random();

    @Override
    public Joker getRandom() {
        return switch (r.nextInt(NUM_METHODS)) {
            case 0 -> doubleMultiplier();
            case 1 -> doubleMultiplierWithCondition();
            case 2 -> theDonour();
            case 3 -> theKingDonour();
            default -> factory.standardJoker("The bored joker", "It does nothing");
        };
    }

    private boolean checkContainsSuit(Set<PlayableCard> cards, Suit suit) {
        return cards.stream()
            .map(c -> c.getSuit())
            .anyMatch(s -> s.equals(suit));
    }

    private boolean checkContainsRank(Set<PlayableCard> cards, Rank rank) {
        return cards.stream()
            .map(c -> c.getRank())
            .anyMatch(r -> r.equals(rank));
    }

    /**
     * The doubler.
     * @return It doubles the current value of multipler without checking any condition
     */
    public Joker doubleMultiplier() {
        return factory.withModifierAndRandomPrice("The doubler",
                "It doubles the current value of multipler without checking any condition",
                new ModifierBuilderImpl()
                        .addMultiplierModifier(m -> m * 2)
                        .build());
    }

    /**
     * The diamond doubler.
     * @return It doubles the current value of multipler if one of the played cards has suit diamond
     */
    public Joker doubleMultiplierWithCondition() {
        return factory.withModifierAndRandomPrice("The diamond doubler",
                "It doubles the current value of multipler if one of " +
                "the played cards has suit diamond",
                new ModifierBuilderImpl()
                        .addMultiplierModifier(m -> m * 2)
                        .addPlayedCardBound(cards -> checkContainsSuit(cards, Suit.DIAMONDS))
                        .build());
    }

    /**
     * The donour.
     * @return It adds 50 base points
     */
    public Joker theDonour() {
        return factory.withModifierAndRandomPrice(
            "The donour",
            "It adds 50 base points",
            new ModifierBuilderImpl()
                .addBasePointsModifier(p -> p + 50)
                .build());
    }

    /**
     * The king donour.
     * @return It adds 50 base points if the played cards contains a king
     */
    public Joker theKingDonour() {
        return factory.withModifierAndRandomPrice(
            "The king donour",
            "It adds 50 base points if the played cards contains a king",
            new ModifierBuilderImpl()
                .addBasePointsModifier(p -> p + 50)
                .addPlayedCardBound(cards -> checkContainsRank(cards, Rank.KING))
                .build());
    }

}
