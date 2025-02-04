package it.unibo.balatrolt.controller.impl;

import static com.google.common.base.Preconditions.checkNotNull;
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
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.BuffedDeck;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.impl.BuffedDeckFactory;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;
import it.unibo.balatrolt.view.api.View;

/**
 * Implementation of {@link MasterController}.
 */
public class MasterControllerImpl implements MasterController {

    private static final int SHOP_SIZE = 3;
    private final Set<View> views = new HashSet<>();
    private final Map<DeckInfo, BuffedDeck> deckTranslator = new HashMap<>();
    private Set<BalatroEvent> nextEvents = Set.of(BalatroEvent.MAIN_MENU);

    private LevelsController levels;
    private PlayerController player;
    private ShopController shop;

    /**
     * Constructor of {@link MasterContorller}.
     */
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
                views.forEach(v -> v.showDecks(deckTranslator.keySet().stream().toList()));
            }
            case CHOOSE_DECK -> {
                setControllers(data);
                this.levels.updateAnte();
                views.forEach(v -> v.showSettings(this.levels.getCurrentBlindInfo(), this.levels.getCurrentBlindStats(), this.player.getSpecialCards(), this.player.getDeck(), this.levels.getNumAnte()));
                views.forEach(v -> v.showAnte(this.levels.getCurrentAnte()));
                views.forEach(v -> v.updateAnteInfo(this.levels.getCurrentAnte()));
            }
            case CHOOSE_BLIND -> {
                views.forEach(v -> v.showRound(this.levels.getHand()));
            }
            case STAGE_CARDS -> {
                recognizeCombination(data);
            }
            case DISCARD_CARDS -> {
                this.levels.discardCards(checkPlayableCards(data));
                views.forEach(v -> v.updateGameTable(this.levels.getHand(), this.levels.getCurrentBlindStats()));
                views.forEach(v -> v.updateScore(this.levels.getCurrentBlindStats()));
            }
            case PLAY_CARDS -> {
                this.levels.playCards(checkPlayableCards(data), this.player.getPlayerStatus());
                switch (this.levels.getRoundStatus()) {
                    case IN_GAME -> {
                        views.forEach(v -> v.updateGameTable(this.levels.getHand(), this.levels.getCurrentBlindStats()));
                    }
                    case BLIND_DEFEATED -> {
                        this.player.addCurrency(this.levels.getCurrentBlindInfo().reward());
                        views.forEach(v -> v.updateCurrency(this.player.getCurrency()));
                        if (this.levels.isOver()) {
                            views.forEach(v -> v.showYouWon());
                        } else {
                            views.forEach(v -> v.showBlindDefeated(this.levels.getCurrentBlindInfo(), this.levels.getCurrentBlindStats()));
                        }
                    }
                    case BLIND_WON -> {
                        views.forEach(v -> v.showGameOver());
                    }
                }
                views.forEach(v -> v.updateScore(this.levels.getCurrentBlindStats()));
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
                    views.forEach(v -> v.notifyErrror("Shop", "You don't have enough money"));
                } else {
                    views.forEach(v -> {
                        v.updateShopCards(this.shop.getCards());
                        v.updateCurrency(this.player.getCurrency());
                    });
                }
            }
            case CLOSE_SHOP -> {
                this.levels.updateAnte();
                views.forEach(v -> {
                    v.showSettings(this.levels.getCurrentBlindInfo(), this.levels.getCurrentBlindStats(), this.player.getSpecialCards(), this.player.getDeck(), this.levels.getNumAnte());
                    v.showAnte(this.levels.getCurrentAnte());
                    v.updateAnteInfo(this.levels.getCurrentAnte());
                    v.updateCurrency(this.player.getCurrency());
                });
                System.out.println("currency: " + this.player.getCurrency());
            }
            default -> throw new IllegalStateException("Invalid Event received");
        }
        this.nextEvents = e.getNextPossibleEvents();
    }

    @Override
    public void attachView(final View v) {
        checkNotNull(v);
        views.add(v);
    }

    private boolean buySpecialCard(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No card was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof SpecialCardInfo,
                "The data received alongside the event isn't a SpecialCardInfo");
        final var card = (SpecialCardInfo) data.get();
        if (this.shop.buyCard(card, this.player.getCurrency())
                && this.shop.translateCard(card).isPresent()
                && this.player.getSpecialCards().size() < this.player.getMaxSpecialCards()) {
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

    private void recognizeCombination(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No list was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof List,
                "The data received alongside the event isn't a list");
        final var cards = (List<?>) data.get();
        final List<PlayableCard> list = this.levels.translatePlayableCard(cards.stream().map(c -> (PlayableCardInfo) c).toList());
        final Combination combination = new PlayedHandImpl(list).evaluateCombination();
        this.views.forEach(v -> v.updateCombinationStatus(new CombinationInfo(combination.getCombinationType().toString(), combination.getBasePoints().basePoints(), combination.getMultiplier().multiplier())));
        System.out.println(new CombinationInfo(combination.getCombinationType().toString(), combination.getBasePoints().basePoints(), combination.getMultiplier().multiplier()));
    }

}
