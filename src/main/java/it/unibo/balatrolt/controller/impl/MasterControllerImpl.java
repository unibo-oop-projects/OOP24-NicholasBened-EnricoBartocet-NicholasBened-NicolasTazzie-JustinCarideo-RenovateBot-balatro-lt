package it.unibo.balatrolt.controller.impl;

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
import it.unibo.balatrolt.controller.api.SortingHandController;
import it.unibo.balatrolt.controller.api.communication.CombinationInfo;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.model.api.cards.BuffedDeck;
import it.unibo.balatrolt.model.api.cards.PlayableCard;
import it.unibo.balatrolt.model.api.combination.Combination;
import it.unibo.balatrolt.model.impl.cards.deck.BuffedDeckFactory;
import it.unibo.balatrolt.model.impl.combination.CombinationTableImpl;
import it.unibo.balatrolt.model.impl.combination.PlayedHandImpl;
import it.unibo.balatrolt.view.api.View;

/**
 * Implementation of {@link MasterController}.
 */
public final class MasterControllerImpl implements MasterController {

    private static final int SHOP_SIZE = 3;
    private final Set<View> views = new HashSet<>();
    private final Map<DeckInfo, BuffedDeck> deckTranslator = new HashMap<>();
    private final List<CombinationInfo> avaiableCombinations;
    private Set<BalatroEvent> nextEvents = Set.of(BalatroEvent.MAIN_MENU);

    private LevelsController levels;
    private PlayerController player;
    private ShopController shop;
    private SortingHandController sortController;

    /**
     * Constructor of {@link MasterContorller}.
     */
    public MasterControllerImpl() {
        final var decks = BuffedDeckFactory.getList();
        decks.forEach(d -> deckTranslator.put(new DeckInfo(d.getName(), d.getDescription()), d));
        this.avaiableCombinations = new CombinationTableImpl().getCombinationTable()
            .entrySet()
            .stream()
            .map(entry -> new CombinationInfo(
                entry.getKey().toString(),
                entry.getValue().e1(),
                entry.getValue().e2()))
            .sorted((c1, c2) -> Integer.compare(c1.points(), c2.points()))
            .toList();
    }

    @Override
    public void handleEvent(final BalatroEvent e, final Optional<?> data) {
        Preconditions.checkState(this.nextEvents.contains(e), "Invalid event received: " + e.toString());
        switch (e) {
            case MAIN_MENU -> views.forEach(View::showMainMenu);
            case INIT_GAME -> {
                this.shop = new ShopControllerImpl(SHOP_SIZE);
                views.forEach(v -> v.showDecks(deckTranslator.keySet().stream().toList()));
            }
            case CHOOSE_DECK -> {
                setControllers(data);
                this.levels.updateAnte();
                this.views.forEach(v -> {
                    v.showSettings(
                            this.levels.getCurrentBlindInfo(),
                            this.levels.getCurrentBlindStats(),
                            this.player.getSpecialCards(),
                            this.player.getDeck(),
                            this.levels.getNumAnte(),
                            avaiableCombinations);
                    v.showAnte(this.levels.getCurrentAnte());
                    v.updateAnteInfo(this.levels.getCurrentAnte());
                });
            }
            case SHOW_BLINDS -> {
                this.views.forEach(v -> v.showRound(this.getSortedCards()));
            }
            case STAGE_CARDS -> this.views.forEach(v -> v.updateCombinationStatus(recognizeCombination(data)));
            case DISCARD_CARDS -> {
                this.levels.discardCards(checkPlayableCards(data));
                this.views.forEach(v -> {
                    v.updateGameTable(this.getSortedCards(), this.levels.getCurrentBlindStats());
                    v.updateScore(this.levels.getCurrentBlindStats()
                    );
                });
            }
            case SORT_BY_RANK -> {
                this.views.forEach(v -> {
                    v.updateGameTable(
                        this.sortController.sortByRank(checkPlayableCards(data)),
                        this.levels.getCurrentBlindStats()
                    );
                });
            }
            case SORT_BY_SUIT -> {
                this.views.forEach(v -> {
                    v.updateGameTable(
                        this.sortController.sortBySuit(checkPlayableCards(data)),
                        this.levels.getCurrentBlindStats());
                });
            }
            case PLAY_CARDS -> {
                this.levels.playCards(checkPlayableCards(data), this.player.getPlayerStatus());
                switch (this.levels.getRoundStatus()) {
                    case IN_GAME -> {
                        this.views.forEach(
                                v -> v.updateGameTable(this.getSortedCards(), this.levels.getCurrentBlindStats()));
                    }
                    case BLIND_DEFEATED -> {
                        this.player.addCurrency(this.levels.getCurrentBlindInfo().reward());
                        this.views.forEach(v -> v.updateCurrency(this.player.getCurrency()));
                        if (this.levels.isOver()) {
                            this.views.forEach(View::showYouWon);
                        } else {
                            this.views.forEach(v -> {
                                v.showBlindDefeated(this.levels.getCurrentBlindInfo());
                            });
                        }
                    }
                    case BLIND_WON -> this.views.forEach(View::showGameOver);
                    default -> throw new IllegalStateException("Round Status unknown");
                }
                this.views.forEach(v -> {
                    v.updateScore(this.levels.getCurrentBlindStats());
                    v.updateCombinationStatus(new CombinationInfo("", 0, 0));
                });
            }
            case OPEN_SHOP -> {
                this.shop.openShop();
                this.views.forEach(v -> {
                    v.showShop();
                    v.updateShopCards(this.shop.getCards());
                });
            }
            case BUY_CARD -> {
                if (buySpecialCard(data)) {
                    this.views.forEach(v -> {
                        v.updateShopCards(this.shop.getCards());
                        v.updateCurrency(this.player.getCurrency());
                        v.updateSpecialCards(this.player.getSpecialCards());
                    });
                }
            }
            case SELL_CARD -> {
                this.player.sellSpecialCard(checkSpecialCard(data));
                this.views.forEach(v -> {
                    v.updateSpecialCards(this.player.getSpecialCards());
                    v.updateCurrency(this.player.getCurrency());
                });
            }
            case CLOSE_SHOP -> {
                this.levels.updateAnte();
                this.views.forEach(v -> {
                    v.showSettings(
                            this.levels.getCurrentBlindInfo(),
                            this.levels.getCurrentBlindStats(),
                            this.player.getSpecialCards(),
                            this.player.getDeck(),
                            this.levels.getNumAnte(),
                            avaiableCombinations);
                    v.showAnte(this.levels.getCurrentAnte());
                    v.updateAnteInfo(this.levels.getCurrentAnte());
                    v.updateCurrency(this.player.getCurrency());
                });
            }
            default -> throw new IllegalStateException("Invalid Event received");
        }
        this.nextEvents = e.getNextPossibleEvents();
    }

    private List<PlayableCardInfo> getSortedCards() {
        return this.sortController.sortByLastCall(this.levels.getHand());
    }

    @Override
    public void attachView(final View v) {
        Preconditions.checkNotNull(v);
        views.add(v);
    }

    private boolean buySpecialCard(final Optional<?> data) {
        final var card = this.checkSpecialCard(data);
        if (this.player.getSpecialCards().size() >= this.player.getMaxSpecialCards()) {
            this.views.forEach(v -> v.notifyErrror("Shop", "The special card slot is full"));
            return false;
        }
        if (this.shop.buyCard(card, this.player.getCurrency())
                && this.shop.translateCard(card).isPresent()) {
            this.player.addSpecialCard(this.shop.translateCard(card).get());
            this.player.spendCurrency(card.price());
            return true;
        }
        this.views.forEach(v -> v.notifyErrror("Shop", "You don't have enough money"));
        return false;
    }

    private void setControllers(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No deck was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof DeckInfo,
                "The data received alongside the event isn't a DeckInfo");
        final var deck = deckTranslator.get((DeckInfo) data.get());
        this.levels = new LevelsControllerImpl(deck);
        this.player = new PlayerControllerImpl(deck);
        this.sortController = new SortingHandControllerImpl(deck);
    }

    private List<PlayableCardInfo> checkPlayableCards(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No cards were received alongside the event");
        Preconditions.checkArgument(data.get() instanceof List, "The data received alongside the event isn't a List");
        final var cards = (List<?>) data.get();
        return cards.stream().map(c -> (PlayableCardInfo) c).toList();
    }

    private CombinationInfo recognizeCombination(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No list was received alongside the event");
        Preconditions.checkArgument(data.get() instanceof List, "The data received alongside the event isn't a list");
        final var cards = (List<?>) data.get();
        final List<PlayableCard> list = this.levels
                .translatePlayableCard(cards.stream().map(c -> (PlayableCardInfo) c).toList());
        final Combination combination = new PlayedHandImpl(list).evaluateCombination();
        return new CombinationInfo(
                combination.getCombinationType().toString(),
                combination.getBasePoints().basePoints(),
                combination.getMultiplier().multiplier());
    }

    private SpecialCardInfo checkSpecialCard(final Optional<?> data) {
        Preconditions.checkArgument(data.isPresent(), "No card was received alongside the event");
        Preconditions.checkArgument(
                data.get() instanceof SpecialCardInfo,
                "The data received alongside the event isn't a SpecialCardInfo");
        return (SpecialCardInfo) data.get();
    }
}
