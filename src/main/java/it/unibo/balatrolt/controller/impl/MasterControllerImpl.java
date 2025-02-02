package it.unibo.balatrolt.controller.impl;

import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.BalatroEvent;
import it.unibo.balatrolt.controller.api.LevelsController;
import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.PlayerController;
import it.unibo.balatrolt.controller.api.ShopController;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.impl.BuffedDeckFactory;
import it.unibo.balatrolt.view.api.View;

public class MasterControllerImpl implements MasterController {

    private static final int SHOP_SIZE = 3;
    private final Set<View> views = new HashSet<>();
    private final Map<DeckInfo, BuffedDeck> deckTranslator = new HashMap<>();
    private Set<BalatroEvent> nextEvents = Set.of(BalatroEvent.MAIN_MENU);

    private LevelsController levels;
    private PlayerController player;
    private ShopController shop;

    public MasterControllerImpl() {
        final var decks = BuffedDeckFactory.getList();
        decks.forEach(d -> deckTranslator.put(new DeckInfo(d.getName(), d.getDescription()), d));
    }

    @Override
    public void handleEvent(final BalatroEvent e, final Optional<?> data) {
        checkState(this.nextEvents.contains(e), "Invalid event received: " + e.toString());
        switch (e) {
            case MAIN_MENU -> views.forEach(View::showMainMenu);
            case INIT_GAME -> {
                this.shop = new ShopControllerImpl(SHOP_SIZE);
                views.forEach(v -> v.showDecks(deckTranslator.keySet()));
            }
            case CHOOSE_DECK -> {
                setControllers(data);
                this.levels.updateAnte();
                views.forEach(v -> v.showAnte(this.levels.getCurrentAnte()));
            }
            case CHOOSE_BLIND -> {
                views.forEach(v -> v.showRound(this.levels.getCurrentBlindInfo(), this.levels.getCurrentBlindStats(),
                        this.player.getSpecialCards(), this.levels.getHand()));
                        System.out.println(this.player.getSpecialCards());
            }
            case DISCARD_CARDS -> {
                this.levels.discardCards(checkPlayableCards(data));
                views.forEach(v -> v.updateHand(this.levels.getHand()));
                views.forEach(v -> v.updateBlindStatistics(this.levels.getCurrentBlindStats()));
            }
            case PLAY_CARDS -> {
                this.levels.playCards(checkPlayableCards(data), this.player.getPlayerStatus());
                switch (this.levels.getRoundStatus()) {
                    case IN_GAME -> {
                        views.forEach(v -> v.updateHand(this.levels.getHand()));
                        views.forEach(v -> v.updateBlindStatistics(this.levels.getCurrentBlindStats()));
                    }
                    case BLIND_DEFEATED -> {
                        this.player.addCurrency(this.levels.getCurrentBlindInfo().reward());
                        if (this.levels.isOver()) {
                            views.forEach(v -> v.showYouWon(this.levels.getCurrentBlindInfo(),
                                    this.levels.getCurrentBlindStats()));
                        } else {
                            views.forEach(v -> v.showBlindDefeated(this.levels.getCurrentBlindInfo(),
                                    this.levels.getCurrentBlindStats()));
                        }
                        System.out.println("currency: " + this.player.getPlayerStatus().currency());
                    }
                    case BLIND_WON -> {
                        views.forEach(v -> v.showGameOver(this.levels.getCurrentBlindInfo(),
                        this.levels.getCurrentBlindStats()));
                        System.out.println("currency: " + this.player.getPlayerStatus().currency());
                    }
                }
                System.out.println(this.levels.getCurrentBlindStats());
            }
            case OPEN_SHOP -> {
                this.shop.openShop();
                views.forEach(v -> {
                    v.showShop();
                    v.updateShopCards(this.shop.getCards());
                });
            }
            case BUY_CARD -> {
                if (!buySpecialCard(data)) {
                    views.forEach(v -> v.notifyErrror("Shop", "Currency is not enought"));
                } else {
                    views.forEach(v -> {
                        v.updateShopCards(this.shop.getCards());
                    });
                }
            }
            case CLOSE_SHOP -> {
                this.levels.updateAnte();
                views.forEach(v -> v.showAnte(this.levels.getCurrentAnte()));
                System.out.println("currency: " + this.player.getPlayerStatus().currency());
            }
            default -> throw new IllegalStateException("Invalid Event received");
        }
        this.nextEvents = e.getNextPossibleEvents();
    }

    @Override
    public void attachView(final View v) {
        views.add(v);
    }

    private boolean buySpecialCard(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No card was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof SpecialCardInfo,
                "The data received alongside the event isn't a SpecialCardInfo");
        final var card = (SpecialCardInfo) data.get();
        if (this.shop.buyCard(card, this.player.getPlayerStatus().currency())
                && this.shop.translateCard(card).isPresent()) {
            this.player.addSpecialCard(this.shop.translateCard(card).get());
            this.player.spendCurrency(card.price());
            return true;
        } else {
            return false;
        }
    }

    private void setControllers(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No deck was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof DeckInfo,
                "The data received alongside the event isn't a DeckInfo");
        final var deck = deckTranslator.get((DeckInfo) data.get());
        this.levels = new LevelsControllerImpl(deck);
        this.player = new PlayerControllerImpl(deck);
    }

    private List<PlayableCardInfo> checkPlayableCards(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No cards were received alongside the event");
        Preconditions.checkArgument(data.get() instanceof List, "The data received alongside the event isn't a List");
        final var cards = (List<?>) data.get();
        return cards.stream().map(c -> (PlayableCardInfo) c).toList();
    }
}
