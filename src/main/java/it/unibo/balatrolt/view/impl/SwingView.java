package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
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

/**
 * Implementation of the View interface.
 */
public class SwingView implements View {
    private static final int RIDIM = 2;
    private final MasterController controller;
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    /**
     * Sets the frame and it's size.
     * @param controller the MasterController to use.
     */
    public SwingView(final MasterController controller) {
        this.controller = Preconditions.checkNotNull(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() / RIDIM), (int) (screenSize.getHeight() / RIDIM));
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
        panel = new DeckSelector(this.controller, setMap);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showAnte(final AnteInfo anteInfo) {
        frame.remove(panel);
        panel = new AnteView(this.controller, anteInfo);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showRound(BlindInfo info, BlindStats stats, List<SpecialCardInfo> specialCards, List<PlayableCardInfo> playableCards) {
        frame.remove(panel);
        panel = new JPanel(new BorderLayout());
        leftPanel = new LeftGUI().build();
        panel.add(leftPanel, BorderLayout.WEST);
        try {
            centerPanel = new SlotGUI(this.controller, playableCards, specialCards);
            panel.add(centerPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void updateHand(List<PlayableCardInfo> hand) {
        ((SlotGUI) centerPanel).updateHand(hand.stream()
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
