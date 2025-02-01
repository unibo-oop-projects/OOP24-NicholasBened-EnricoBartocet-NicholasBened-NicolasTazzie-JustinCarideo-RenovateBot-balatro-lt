package it.unibo.balatrolt.controller.impl;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.LevelsController;
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
    private Set<BalatroEvent> nextEvents = Set.of(BalatroEvent.INIT_GAME);

    private Player player;
    private LevelsController levelsController;

    public MasterControllerImpl() {
        final var decks = BuffedDeckFactory.getList();
        decks.forEach(d -> deckTranslator.put(new DeckInfo(d.getName(), d.getDescription()), d));
    }

    @Override
    public void handleEvent(final BalatroEvent e, final Optional<?> data) {
        checkState(this.nextEvents.contains(e));
        switch (e) {
            case INIT_GAME -> views.forEach(v -> v.showDecks(deckTranslator.keySet()));
            case CHOOSE_DECK -> {
                setDeck(data);
                views.forEach(v -> v.showAnte(this.levelsController.getCurrentAnteInfo()));
            }
            case CHOOSE_BLIND -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case DISCARD_CARDS -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case PLAY_CARDS -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case OPEN_SHOP -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case BUY_CARD -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            case CLOSE_SHOP -> throw new UnsupportedOperationException("Unimplemented case: " + e);
            default -> throw new IllegalStateException("Invalid Event received");
        }
        this.nextEvents = e.getNextPossibleEvents();
    }

    @Override
    public void attachView(final View v) {
        views.add(v);
    }

    private void setDeck(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No deck was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof DeckInfo, "The data received alongside the event isn't a DeckInfo");
        final var deck = deckTranslator.get((DeckInfo) data.get());
        this.player = new PlayerImpl(deck);
        this.levelsController = new LevelsControllerImpl(deck);
    }
}
