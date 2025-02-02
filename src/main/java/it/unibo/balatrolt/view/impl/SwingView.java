package it.unibo.balatrolt.view.impl;

import java.util.Set;

import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopView;
import it.unibo.balatrolt.view.api.View;

public class SwingView implements View {
    private ShopView shop = new ShopViewImpl(null, null);

    @Override
    public void showDecks(Set<DeckInfo> setMap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showDecks'");
    }

    @Override
    public void showAnte() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showAnte'");
    }

    @Override
    public void showRound() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showRound'");
    }

    @Override
    public void updateHand() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateHand'");
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
    public void updateSpecialCards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSpecialCards'");
    }

    @Override
    public void showShop(Set<SpecialCardInfo> toSell) {
        this.shop.updateCards(toSell);
    }

    @Override
    public void notifyErrror(String name, String desc) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notifyErrror'");
    }

    @Override
    public void updateShopCards(Set<SpecialCardInfo> toSell) {
        this.shop.updateCards(toSell);
    }
}
