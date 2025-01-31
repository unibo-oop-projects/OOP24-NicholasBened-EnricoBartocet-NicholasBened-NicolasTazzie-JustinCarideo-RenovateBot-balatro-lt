package it.unibo.balatrolt.controller.impl;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.Player;
import it.unibo.balatrolt.model.impl.BuffedDeckFactory;
import it.unibo.balatrolt.model.impl.PlayerImpl;
import it.unibo.balatrolt.view.api.View;

public class MasterControllerImpl implements MasterController {
    private final Set<View> views = new HashSet<>();
    private final Map<DeckInfo, BuffedDeck> deckTranslator = new HashMap<>();
    private Set<BalatroEvent> nextEvents = Set.of(BalatroEvent.CHOOSE_DECK);
    private Player player;

    public MasterControllerImpl() {
        final var decks = BuffedDeckFactory.getList();
        decks.forEach(d -> deckTranslator.put(new DeckInfo(d.getName(), d.getDescription()), d));
        views.forEach(v -> v.showDecks(deckTranslator.keySet()));
    }

    @Override
    public void handleEvent(final BalatroEvent e, final Optional<?> data) {
        checkState(this.nextEvents.contains(e));
        switch (e) {
            case CHOOSE_DECK -> {
                var deck = deckTranslator.get((DeckInfo) data.get());
                setDeck(deck);
            }
            case BUY_CARD -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case CHOOSE_BLIND -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case CLOSE_SHOP -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case DISCARD_CARDS -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case OPEN_SHOP -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case PLAY_CARDS -> throw new UnsupportedOperationException("Unimplemented case: " + e);
        }
        this.nextEvents = e.getNextPossibleEvents();
    }

    @Override
    public void attachView(final View v) {
        views.add(v);
    }

    private void setDeck(final BuffedDeck deck) {
        this.player = new PlayerImpl(deck);
        // this.playerController.setDeck(); // se esistesse un eventuale playercontroller
        views.forEach(View::showRound);
    }
}
