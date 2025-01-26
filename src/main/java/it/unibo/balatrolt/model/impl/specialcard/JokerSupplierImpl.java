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
 * @author Nicolas Tazzieri - nicolas.tazzieri@studio.unibo.it
 */
public final class JokerSupplierImpl implements JokerSupplier {
    private static final int NUM_JOKERS = 6;
    private final JokerFactory factory = new JokerFactoryImpl();
    private final Random r = new Random();

    @Override
    public Joker getRandom() {
        return switch (r.nextInt(NUM_JOKERS)) {
            case 0 -> this.doubler();
            case 1 -> this.diamondDoubler();
            case 2 -> this.donour();
            case 3 -> this.kingDonour();
            case 4 -> this.heartDoubler();
            case 5 -> this.seventhDonour();
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
    public Joker doubler() {
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
    public Joker diamondDoubler() {
        return factory.addPlayableCardBoundToJoker("The diamond doubler",
                "It doubles the current value of multipler if one of " +
                "the played cards has suit diamond",
                doubler(),
                cards -> checkContainsSuit(cards, Suit.DIAMONDS)
            );
    }

    /**
     * The heart doubler.
     * @return It doubles the current value of multipler if one of the played cards has suit heart
     */
    public Joker heartDoubler() {
        return factory.addPlayableCardBoundToJoker("The heart doubler",
                "It doubles the current value of multipler if one of " +
                "the played cards has suit heart",
                this.doubler(),
                cards -> checkContainsSuit(cards, Suit.HEARTS)
            );
    }

    /**
     * The donour.
     * @return It adds 50 base points
     */
    public Joker donour() {
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
    public Joker kingDonour() {
        return this.factory.addPlayableCardBoundToJoker(
            "The king donour",
            "It adds 50 base points if the played cards contains a king",
            this.donour(),
            cards -> checkContainsRank(cards, Rank.KING)
        );
    }

    /**
     * The seventh donour.
     * @return It adds 50 base points if the played cards contains a seven
     */
    public Joker seventhDonour() {
        return this.factory.addPlayableCardBoundToJoker(
            "The seventh donour",
            "It adds 50 base points if the played cards contains a seven",
            this.donour(),
            cards -> checkContainsRank(cards, Rank.SEVEN)
        );
    }

}
