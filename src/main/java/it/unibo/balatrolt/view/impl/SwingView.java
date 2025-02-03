package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.common.base.Preconditions;

import it.unibo.balatrolt.controller.api.MasterController;
import it.unibo.balatrolt.controller.api.communication.AnteInfo;
import it.unibo.balatrolt.controller.api.communication.BlindInfo;
import it.unibo.balatrolt.controller.api.communication.BlindStats;
import it.unibo.balatrolt.controller.api.communication.DeckInfo;
import it.unibo.balatrolt.controller.api.communication.SpecialCardInfo;
import it.unibo.balatrolt.view.api.ShopView;
import it.unibo.balatrolt.controller.api.communication.PlayableCardInfo;
import it.unibo.balatrolt.view.api.View;

/**
 * Implementation of the View interface.
 */
public class SwingView implements View {
    private static final float RIDIM = 1.5f;
    private final MasterController controller;
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel centerPanel;

    /**
     * Sets the frame and it's size.
     *
     * @param controller the MasterController to use.
     */
    public SwingView(final MasterController controller) {
        this.controller = Preconditions.checkNotNull(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() / RIDIM), (int) (screenSize.getHeight() / RIDIM));
        frame.setLocationByPlatform(true);
        frame.setMinimumSize(new Dimension(1000, 600));
    }

    @Override
    public void showMainMenu() {
        if (panel != null) frame.remove(panel);
        panel = new MainMenu(controller, "Play");
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showDecks(final List<DeckInfo> setMap) {
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
    public void showRound(BlindInfo info, BlindStats stats, List<SpecialCardInfo> specialCards,
            List<PlayableCardInfo> playableCards) {
        frame.remove(panel);
        panel = new JPanel(new BorderLayout());
        leftPanel = new LeftGUI().build();
        panel.add(leftPanel, BorderLayout.WEST);
        try {
            centerPanel = new GameTable(this.controller, playableCards, specialCards);
            panel.add(centerPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
        frame.add(panel);
        frame.setVisible(true);
        updateBlindStatistics(stats);
    }

    @Override
    public void updateHand(List<PlayableCardInfo> hand) {
        ((GameTable) this.centerPanel).updateHand(hand);
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
    public void updateSpecialCards() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSpecialCards'");
    }

    @Override
    public void updateBlindStatistics(BlindStats stats) {
        ((GameTable) this.centerPanel).setDiscardEnabled(stats.discards() > 0);
    }

    @Override
    public void showBlindDefeated(BlindInfo blindInfo, BlindStats blindStats) {
        this.panel.remove(this.centerPanel);
        this.centerPanel = new BlindOver(this.controller, "BLIND DEFEATED", blindInfo, blindStats);
        this.panel.add(this.centerPanel, BorderLayout.CENTER);
        this.centerPanel.setVisible(true);
    }

    @Override
    public void showGameOver(BlindInfo blindInfo, BlindStats blindStats) {
        frame.remove(panel);
        panel = new GameOver(controller);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void showYouWon(BlindInfo blindInfo, BlindStats blindStats) {
        this.panel.remove(this.centerPanel);
        this.centerPanel = new BlindOver(this.controller, "YOU WON THE ENTIRE GAME", blindInfo, blindStats);
        this.panel.add(this.centerPanel, BorderLayout.CENTER);
        this.centerPanel.setVisible(true);
    }

    @Override
    public void showShop() {
        this.panel.remove(centerPanel);
        this.centerPanel = new ShopViewImpl(controller, null);
        this.panel.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void notifyErrror(String title, String desc) {
        JOptionPane.showMessageDialog(this.panel, desc, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void updateShopCards(Set<SpecialCardInfo> toSell) {
        ((ShopView) this.centerPanel).updateCards(toSell);
    }
}
