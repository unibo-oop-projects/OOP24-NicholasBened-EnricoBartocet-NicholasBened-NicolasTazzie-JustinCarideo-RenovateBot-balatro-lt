package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerFactory;
import it.unibo.balatrolt.model.impl.Pair;
import it.unibo.balatrolt.model.impl.cards.PlayableCardImpl;

/**
 * Catalog of base {@link Joker} with {@link JokerTier} common.
 */
public final class JokerCatalogCommon implements JokerCatalog {
    private static final String THE_FOURTH_MULTIPLIER = "the fourth multiplier";
    private static final String THE_SECOND_MULTIPLIER = "the second multiplier";
    private static final String THE_TENTH_POINT = "the tenth point";
    private static final String THE_FIFTH_POINT = "the fifth point";
    private static final JokerTier TIER = JokerTier.COMMON;
    private final JokerCatalog base = new JokerCatalogBase();
    private final JokerFactory factory = new JokerFactoryImpl();
    private final Map<String, Joker> jokers;

    /**
     * Constuctor.
     */
    public JokerCatalogCommon() {
        this.jokers = Map.of(
            this.banker().getName(), this.banker(),
            this.kingHeartHolder().getName(), this.kingHeartHolder(),
            this.kingHeartPlayer().getName(), this.kingHeartPlayer(),
            this.spadesAceHolder().getName(), this.spadesAceHolder(),
            this.spadesAcePlayer().getName(), this.spadesAcePlayer(),
            this.whyDiscardTwoHeart().getName(), this.whyDiscardTwoHeart()
        );
    }

    @Override
    public List<Joker> getJokerList() {
        return this.jokers.values().stream().toList();
    }

    @Override
    public Optional<Joker> getJoker(String name) {
        return Optional.fromNullable(this.jokers.get(name.toLowerCase()));
    }

    private Joker banker() {
        return this.factory.addMoneyBoundToJoker(
            "the banker",
             "it adds 5 base points if the current money is 0",
             this.base.getJoker(THE_FIFTH_POINT).get(),
             m -> m == 0,
             TIER);
    }

    private Joker kingHeartHolder() {
        return this.factory.addHoldingCardBoundToJoker(
            "the king heart holder",
             "it adds 5 base points if your're holding a king of hearts",
             this.base.getJoker(THE_FIFTH_POINT).get(),
             checkContains(new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS))),
             TIER);
    }

    private Joker kingHeartPlayer() {
        return this.factory.addPlayedCardBoundToJoker(
            "the king heart player",
             "it adds 10 base points if you're playing a king of hearts",
             this.base.getJoker(THE_TENTH_POINT).get(),
             checkContains(new PlayableCardImpl(new Pair<>(Rank.KING, Suit.HEARTS))),
             TIER);
    }

    private Joker spadesAceHolder() {
        return this.factory.addHoldingCardBoundToJoker(
            "the spades ace holder",
             "it adds 2 multiplier if your're holding an ace of spades",
             this.base.getJoker(THE_SECOND_MULTIPLIER).get(),
             this.checkContains(new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES))),
             TIER);
    }

    private Joker spadesAcePlayer() {
        return this.factory.addPlayedCardBoundToJoker(
            "the spades ace holder",
             "it adds 4 multiplier if your're playing an ace of spades",
             this.base.getJoker(THE_FOURTH_MULTIPLIER).get(),
             this.checkContains(new PlayableCardImpl(new Pair<>(Rank.ACE, Suit.SPADES))),
             TIER);
    }

    private Joker whyDiscardTwoHeart() {
        return this.factory.addPlayedCardBoundToJoker(
            "why they discard the two of hearts?",
             "it adds 10 basePoint if your're playing a two of hearts",
             this.base.getJoker(THE_TENTH_POINT).get(),
             this.checkContains(new PlayableCardImpl(new Pair<>(Rank.TWO, Suit.HEARTS))),
             TIER);
    }

    private Predicate<Set<PlayableCard>> checkContains(PlayableCard card) {
        return c -> c.contains(card);
    }
}
