package it.unibo.balatrolt.model.impl.cards.specialcard;

import java.util.Map;
import java.util.Set;

import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Rank;
import it.unibo.balatrolt.model.api.cards.PlayableCard.Suit;
import it.unibo.balatrolt.model.api.cards.specialcard.Joker;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerCatalog;
import it.unibo.balatrolt.model.api.cards.specialcard.JokerTier;

/**
 * Catalog of not common {@link Joker}.
 */
public final class JokerCatalogNotCommon extends AbstractJokerCatalog {
    private static final String THE_FOURTH_MULTIPLIER = "the fourth multiplier";
    private static final String THE_TENTH_POINT = "the tenth point";
    private static final String THE_EIGHT_MULTIPLIER = "the eighth multiplier";
    private static final JokerTier TIER = JokerTier.NOT_COMMON;
    private final JokerCatalog base = new JokerCatalogBase();

    @Override
    protected Map<String, Joker> getJokersMap() {
        return Map.of(
            this.hearther().getName(), this.hearther(),
            this.spader().getName(), this.spader(),
            this.aceGiver().getName(), this.aceGiver()
        );
    }

    private Joker hearther() {
        return super.getFactory()
            .addPlayedCardBoundToJoker(
                "the hearther",
                "adds 4 multiplier if the played card contains a heart",
                this.base.getJoker(THE_FOURTH_MULTIPLIER).get(),
                c -> checkContainsSuit(c, Suit.HEARTS),
                TIER);
    }

    private Joker spader() {
        return super.getFactory()
            .addPlayedCardBoundToJoker(
                "the spader",
                "adds 10 basepoints if the played card contains a spades",
                this.base.getJoker(THE_TENTH_POINT).get(),
                c -> checkContainsSuit(c, Suit.SPADES),
                TIER);
    }

    private Joker aceGiver() {
        return super.getFactory()
            .addPlayedCardBoundToJoker(
                "the ace giver",
                "adds 8 multiplier if you're holding an ace",
                this.base.getJoker(THE_EIGHT_MULTIPLIER).get(),
                c -> checkContainsRank(c, Rank.ACE),
                TIER);
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
}
