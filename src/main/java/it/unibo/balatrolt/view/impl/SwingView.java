package it.unibo.balatrolt.view.impl;

import java.util.List;
import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.view.api.View;

public class SwingView implements View {
    private SwingMainRound mainRoundView;

    public SwingView(SwingMainRound mainRoundView) {
        try {
            this.mainRoundView = new SwingMainRound();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public void showDecks(final Set<DeckInfo> setMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showDecks'");
    }

    @Override
    public void showAnte(final AnteInfo anteInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showAnte'");
    }

    @Override
    public void showRound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showRound'");
    }

    @Override
    public void updateHand(List<PlayableCardInfo> hand) {
        mainRoundView.updateHand(hand.stream()
            .map(card -> card.rank() + card.suit())
            .toList());
    }

    @Override
    public void updateCombinationStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCombinationStatus'");
    }

    @Override
    public void updateScore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateScore'");
    }

    @Override
    public void updatePlayedCards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePlayedCards'");
    }

    @Override
    public void showShop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showShop'");
    }

    @Override
    public void updateSpecialCards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSpecialCards'");
    }
}
