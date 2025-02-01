package it.unibo.balatrolt.view.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.View;

public class SwingView implements View {
    private JFrame frame = new JFrame();
    private JPanel panel;
    private SwingMainRound mainRoundView;
    private final MasterController controller;

    public SwingView(final MasterController controller) {
        this.controller = Preconditions.checkNotNull(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() * 0.5), (int) (screenSize.getHeight() * 0.5));
        frame.setLocationByPlatform(true);
    }

    @Override
    public void showMainMenu() {
        panel = new MainMenu(controller, "Play");
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showDecks(final Set<DeckInfo> setMap) {
        frame.remove(panel);
        panel = new DeckSelector(controller, setMap);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showAnte(final AnteInfo anteInfo) {
        frame.remove(panel);
        panel = new AnteView(controller, anteInfo);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showRound(BlindInfo info, BlindStats stats, List<SpecialCardInfo> specialCards, List<PlayableCardInfo> playableCards) {
        try {
            this.mainRoundView = new SwingMainRound();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        updateHand(playableCards);
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

    @Override
    public void updateBlindStatistics(BlindStats stats) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBlindStatistics'");
    }

    @Override
    public void showBlindDefeated() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showBlindDefeated'");
    }

    @Override
    public void showGameOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showGameOver'");
    }

    @Override
    public void showYouWon() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showYouWon'");
    }
}
