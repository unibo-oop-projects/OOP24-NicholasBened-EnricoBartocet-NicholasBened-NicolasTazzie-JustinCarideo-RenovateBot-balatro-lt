package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerFactory;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerSupplier;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;
import it.unibo.balatrolt.model.impl.cards.modifier.ModifierBuilderImpl;

/**
 * Supplier of {@link Joker} with {@link JokerTier} common.
 */
public class JokerSupplierCommon implements JokerSupplier {
    private static final JokerTier TIER = JokerTier.COMMON;
    final List<Joker> commonJokers;
    final JokerFactory factory = new JokerFactoryImpl();

    /**
     * Constructor.
     */
    public JokerSupplierCommon() {
        this.commonJokers = List.of(
            this.banker(),
            this.kingHeartHolder(),
            this.kingHeartPlayer(),
            this.spadesAceHolder(),
            this.spadesAcePlayer(),
            this.whyDiscardTwoHeart()
        );
    }

    @Override
    public Joker getRandom() {
        return this.commonJokers.get(new Random().nextInt(this.commonJokers.size()));
    }

    @Override
    public List<Joker> getJokerList() {
        return List.copyOf(this.commonJokers);
    }

    private Joker banker() {
        return this.factory.addMoneyBoundToJoker(
            "The banker",
             "It adds 5 base points if the current money is 0",
             this.fiveBasePoints(),
             m -> m == 0,
             TIER);
    }

    private Joker kingHeartHolder() {
        return this.factory.addHoldingCardBoundToJoker(
            "The king heart holder",
             "It adds 5 base points if your're holding a king of hearts",
             this.fiveBasePoints(),
             checkContains(new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS))),
             TIER);
    }

    private Joker kingHeartPlayer() {
        return this.factory.addPlayedCardBoundToJoker(
            "The king heart player",
             "It adds 10 base points if you're playing a king of hearts",
             this.tenBasePoints(),
             checkContains(new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS))),
             TIER);
    }

    private Joker spadesAceHolder() {
        return this.factory.addHoldingCardBoundToJoker(
            "The spades ace holder",
             "It adds 2 multiplier if your're holding an ace of spades",
             this.twoMultiplier(),
             this.checkContains(new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES))),
             TIER);
    }

    private Joker spadesAcePlayer() {
        return this.factory.addPlayedCardBoundToJoker(
            "The spades ace holder",
             "It adds 4 multiplier if your're playing an ace of spades",
             this.fourMultiplier(),
             this.checkContains(new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES))),
             TIER);
    }

    private Joker whyDiscardTwoHeart() {
        return this.factory.addPlayedCardBoundToJoker(
            "Why they discard the two of hearts?",
             "It adds 10 basePoint if your're playing a two of hearts",
             this.tenBasePoints(),
             this.checkContains(new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.HEARTS))),
             TIER);
    }

    private Predicate<Set<PlayableCard>> checkContains(PlayableCard card) {
        return c -> c.contains(card);
    }

    /* BASE JOKERS */

    private Joker fiveBasePoints() {
        return this.factory.withModifierAndRandomPrice(
            "The fifth point",
         "It adds 5 base points",
         new ModifierBuilderImpl()
         .addBasePointsModifier(b -> b + 5)
         .build(),
         TIER);
    }

    private Joker tenBasePoints() {
        return this.factory.withModifierAndRandomPrice(
            "The tenth point",
         "It adds 10 base points",
         new ModifierBuilderImpl()
         .addBasePointsModifier(b -> b + 10)
         .build(),
         TIER);
    }

    private Joker twoMultiplier() {
        return this.factory.withModifierAndRandomPrice(
            "The second multiplier",
         "It adds 2 multiplier",
         new ModifierBuilderImpl()
         .addMultiplierModifier(m -> m + 2)
         .build(),
         TIER);
    }

    private Joker fourMultiplier() {
        return this.factory.withModifierAndRandomPrice(
            "The fourth multiplier",
         "It adds 4 multiplier",
         new ModifierBuilderImpl()
         .addMultiplierModifier(m -> m + 4)
         .build(),
         TIER);
    }
}
