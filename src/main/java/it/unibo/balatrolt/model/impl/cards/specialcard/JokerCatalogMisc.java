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
 * A catalog containing Miscelleous {@link Joker}
 */
public final class JokerCatalogMisc extends AbstractJokerCatalog {
    private static final String DOUBLER = "the doubler";
    private static final String DONOUR = "the donour";
    private JokerCatalog base = new JokerCatalogBase();

    @Override
    protected Map<String, Joker> getJokersMap() {
       this.base = new JokerCatalogBase();
       return Map.of(
            this.kingDonour().getName(), this.kingDonour(),
            this.seventhDonour().getName(), this.seventhDonour(),
            this.heartDoubler().getName(), this.heartDoubler(),
            this.diamondDoubler().getName(), this.diamondDoubler()
       );
    }

    private Joker kingDonour() {
        return super.getFactory().addPlayedCardBoundToJoker(
            "the king donour",
            "It adds 50 base points if the played cards contains a king",
            this.base.getJoker(DONOUR).get(),
            cards -> checkContainsRank(cards, Rank.KING),
            JokerTier.EPIC
        );
    }

    private Joker seventhDonour() {
        return super.getFactory().addPlayedCardBoundToJoker(
            "the seventh donour",
            "It adds 50 base points if the played cards contains a seven",
            this.base.getJoker(DONOUR).get(),
            cards -> checkContainsRank(cards, Rank.SEVEN),
            JokerTier.EPIC
        );
    }

    private Joker diamondDoubler() {
        return super.getFactory().addPlayedCardBoundToJoker(
            "The diamond doubler",
            "It doubles the current value of multipler if one of "
            + "the played cards has suit diamond",
            this.base.getJoker(DOUBLER).get(),
            cards -> checkContainsSuit(cards, Suit.DIAMONDS),
            JokerTier.EPIC
        );
    }

    private Joker heartDoubler() {
        return super.getFactory().addPlayedCardBoundToJoker(
            "The heart doubler",
            "It doubles the current value of multipler if one of "
            + "the played cards has suit heart",
            this.base.getJoker(DOUBLER).get(),
            cards -> checkContainsSuit(cards, Suit.HEARTS),
            JokerTier.EPIC
        );
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
