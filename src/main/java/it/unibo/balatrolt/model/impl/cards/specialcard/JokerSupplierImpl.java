package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Supplier;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerFactory;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerSupplier;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Joker supplier implementation.
 * @author Nicolas Tazzieri - nicolas.tazzieri@studio.unibo.it
 */
public final class JokerSupplierImpl implements JokerSupplier, Supplier<Joker> {
    private static final int DONOUR_ADDER = 50;
    private final List<Joker> jokers;
    private final JokerFactory factory = new JokerFactoryImpl();
    private final Random r = new Random();

    /**
     * Constructor.
     */
    public JokerSupplierImpl() {
        this.jokers = List.of(
            this.doubler(),
            this.diamondDoubler(),
            this.donour(),
            this.kingDonour(),
            this.heartDoubler(),
            this.seventhDonour()
        );
    }

    @Override
    public Joker get() {
        return this.getRandom();
    }

    @Override
    public Joker getRandom() {
        return this.jokers.get(innerListIndex());
    }

    @Override
    public List<Joker> getJokerList() {
        return List.copyOf(this.jokers);
    }

    private int innerListIndex() {
        return r.nextInt(this.jokers.size());
    }

    private boolean checkContainsSuit(final Set<PlayableCard> cards, final Suit suit) {
        return cards.stream()
            .map(PlayableCard::getSuit)
            .anyMatch(s -> s.equals(suit));
    }

    private boolean checkContainsRank(final Set<PlayableCard> cards, final Rank rank) {
        return cards.stream()
            .map(PlayableCard::getRank)
            .anyMatch(r -> r.equals(rank));
    }

    private Joker doubler() {
        return factory.withModifierAndRandomPrice(
            "The doubler",
            "It doubles the current value of multipler without checking any condition",
            new ModifierBuilderImpl()
                    .addMultiplierModifier(m -> {
                        final int toMultiply = 2;
                        return m * toMultiply;
                    })
                    .build(),
            JokerTier.LEGENDARY
            );
    }

    private Joker diamondDoubler() {
        return factory.addPlayedCardBoundToJoker(
            "The diamond doubler",
            "It doubles the current value of multipler if one of "
            + "the played cards has suit diamond",
            doubler(),
            cards -> checkContainsSuit(cards, Suit.DIAMONDS),
            JokerTier.EPIC
        );
    }

    private Joker heartDoubler() {
        return factory.addPlayedCardBoundToJoker(
            "The heart doubler",
            "It doubles the current value of multipler if one of "
            + "the played cards has suit heart",
            this.doubler(),
            cards -> checkContainsSuit(cards, Suit.HEARTS),
            JokerTier.EPIC
        );
    }

    private Joker donour() {
        return factory.withModifierAndRandomPrice(
        "The donour",
        "It adds 50 base points",
        new ModifierBuilderImpl()
            .addBasePointsModifier(p -> {
                return p + DONOUR_ADDER;
            })
            .build(),
            JokerTier.LEGENDARY
        );
    }

    private Joker kingDonour() {
        return this.factory.addPlayedCardBoundToJoker(
            "The king donour",
            "It adds 50 base points if the played cards contains a king",
            this.donour(),
            cards -> checkContainsRank(cards, Rank.KING),
            JokerTier.EPIC
        );
    }

    private Joker seventhDonour() {
        return this.factory.addPlayedCardBoundToJoker(
            "The seventh donour",
            "It adds 50 base points if the played cards contains a seven",
            this.donour(),
            cards -> checkContainsRank(cards, Rank.SEVEN),
            JokerTier.EPIC
        );
    }
}
