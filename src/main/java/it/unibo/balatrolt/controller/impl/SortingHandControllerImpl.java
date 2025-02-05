package it.unibo.balatrolt.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.balatrolt.controller.api.SortingHandController;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.model.api.cards.Deck;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.impl.cards.SortingPlayableHelpers;

/**
 * Implementation of {@link SortingHandController}.
 */
public class SortingHandControllerImpl implements SortingHandController {
    private final Map<PlayableCardInfo, PlayableCard> cardsTranslator = new HashMap<>();

    /**
     * Constructor.
     * @param deck current deck of cards
     */
    public SortingHandControllerImpl(final Deck deck) {
        deck.getCards().forEach(c -> cardsTranslator.put(new PlayableCardInfo(c.getRank().name(), c.getSuit().name()), c));
    }

    @Override
    public List<PlayableCardInfo> sortByRank(final List<PlayableCardInfo> cards) {
        return SortingPlayableHelpers.sortingByRank(this.translateCards(cards))
            .stream()
            .map(c -> new PlayableCardInfo(c.getRank().name(), c.getSuit().name()))
            .toList();
    }

    @Override
    public List<PlayableCardInfo> sortBySuit(final List<PlayableCardInfo> cards) {
        return SortingPlayableHelpers.sortingBySuit(this.translateCards(cards))
            .stream()
            .map(c -> new PlayableCardInfo(c.getRank().name(), c.getSuit().name()))
            .toList();
    }

    private List<PlayableCard> translateCards(final List<PlayableCardInfo> cards) {
        return cards.stream()
            .map(cardsTranslator::get)
            .toList();
    }
}
