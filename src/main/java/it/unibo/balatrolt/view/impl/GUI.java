package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
;

public class GUI extends JFrame {
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*size, 50*size);

        JPanel mainPanel = new JPanel(new BorderLayout());

        //creating left panel
        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        
        JLabel blind = new JLabel("Big Blind", SwingConstants.CENTER);
        blind.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(blind);

        JLabel blindInfo = new JLabel("Score at least: ---based on the round---");
        blindInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(blindInfo);

        JLabel score = new JLabel("round score: xxxxx", SwingConstants.CENTER);
        score.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(score);

        JLabel multiplicators = new JLabel("--somma carte-- X --moltiplicatori--", SwingConstants.CENTER);
        multiplicators.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(multiplicators);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel leftInfoPanel = new JPanel(new GridLayout(2, 1));
        ActionListener al = e -> {
            JOptionPane.showMessageDialog(leftPanel, "ciaooooo");
        };
        JButton infoButton = new JButton("Run info");
        JButton optionButton = new JButton("Options");
        infoButton.addActionListener(al);
        optionButton.addActionListener(al);

        leftInfoPanel.add(infoButton, SwingConstants.CENTER);
        leftInfoPanel.add(optionButton, SwingConstants.CENTER);

        infoPanel.add(leftInfoPanel, BorderLayout.WEST);

        JPanel rightInfoPanel = new JPanel(new GridLayout(3, 2));
        JLabel hands = new JLabel("Hands : xxx");
        hands.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel money = new JLabel("money : xxx");
        money.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel discards = new JLabel("Discards : xxx");
        discards.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel ante = new JLabel("Ante : xxx");
        ante.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel round = new JLabel("Round : xxx");
        round.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightInfoPanel.add(hands);
        rightInfoPanel.add(discards);
        rightInfoPanel.add(money);
        rightInfoPanel.add(ante);
        rightInfoPanel.add(round);

        infoPanel.add(rightInfoPanel);
        leftPanel.add(infoPanel);

        //creating card panel
        JPanel cardPanel = new JPanel();

        cardPanel.setLayout(new GridLayout(2, 5));
        for (int i = 0; i < 10; i++) {
            JLabel cardSlot = new JLabel("Card Slot " + (i + 1), SwingConstants.CENTER);
            cardSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cardPanel.add(cardSlot);
        }

        //mixing everything togheter
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.SOUTH);
        this.getContentPane().add(mainPanel);

        this.setVisible(true);
    }    
}
