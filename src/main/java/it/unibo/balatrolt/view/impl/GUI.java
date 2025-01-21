package it.unibo.balatrolt.view.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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

        //Image img = Toolkit.getDefaultToolkit().createImage("C:/Users/nicholas.benedetti/Desktop/sfondo_balatro.jpg");
        
        //creating left panel
        JPanel leftPanel = createLeftPanel();

        //creating slots panel
        JPanel slotPanel = createSlotPanel();

        //mixing everything togheter
        this.add(leftPanel, BorderLayout.WEST);
        this.add(slotPanel);
        this.setVisible(true);
    }

    private JPanel createLeftPanel () {
        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        //leftPanel.setBackground(Color.darkGray.darker());
        
        //first block
        JPanel blind = new JPanel();
        blind.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        blind.setBackground(Color.orange.darker());
        JLabel str = new JLabel("Big Blind");
        str.setFont(new Font("Bauhaus 93", Font.PLAIN, 36));
        blind.add(str, BorderLayout.CENTER);
        leftPanel.add(blind);
        
        //second block
        JPanel blindInfo = new JPanel();
        blindInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        blindInfo.setBackground(Color.orange.darker().darker());
        JLabel str1 = new JLabel("Score at least: ---based on the round---");
        blindInfo.add(str1);
        leftPanel.add(blindInfo);
        
        //third block  
        JLabel score = new JLabel("round score: xxxxx", SwingConstants.CENTER);
        score.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(score);

        //fourth block
        JLabel multiplicators = new JLabel("--somma carte-- X --moltiplicatori--", SwingConstants.CENTER);
        multiplicators.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(multiplicators);

        //fifth block
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel leftInfoPanel = new JPanel(new GridLayout(2, 1));

        ActionListener al = e -> {
            JOptionPane.showMessageDialog(leftPanel, "ciaooooo");
        };

        JButton infoButton = new JButton("Run info");
        infoButton.setBackground(Color.ORANGE);
        JButton optionButton = new JButton("Options");
        optionButton.setBackground(Color.ORANGE);
        infoButton.addActionListener(al);
        optionButton.addActionListener(al);

        leftInfoPanel.add(infoButton, SwingConstants.CENTER);
        leftInfoPanel.add(optionButton, SwingConstants.CENTER);

        infoPanel.add(leftInfoPanel, BorderLayout.WEST);

        JPanel rightInfoPanel = new JPanel(new GridLayout(3, 2));
        rightInfoPanel.setBackground(Color.red.darker());
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

        return leftPanel;
    }

    private JPanel createSlotPanel() {
        JPanel panel = new JPanel(new GridLayout());
        panel.setBackground(Color.green.darker().darker().darker());
        
        //creating special cards slots

        return panel;
    }
    
}
