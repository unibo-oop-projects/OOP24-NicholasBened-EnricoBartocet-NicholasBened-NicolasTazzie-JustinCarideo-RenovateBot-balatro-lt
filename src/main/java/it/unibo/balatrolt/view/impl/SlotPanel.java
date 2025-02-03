package it.unibo.balatrolt.view.impl;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.google.common.base.Preconditions;

/**
 * Represent a Generic panel in which we can store any card.
 * we can also set the action to perform when the buttons are pressed.
 */
public class SlotPanel<X> extends JPanel {
    private final int slotSize;
    private Map<String, X> slots;
    private Consumer<X> consumer;
    private Supplier<Boolean> canClick;
    private Supplier<Boolean> canRemove;

    /**
     * It holds the full obj to eventually give it back to the controller
     * and holds the name of the card to set the image.
     */
    record SlotObject<X>(X obj, String cardName, String cardPath) {}

    /**
     * Sets the parameters of the panel.
     * @param slotSize max size of the slot.
     * @param canClick possibility to click
     * @param consumer action to perform with the pressed card. THE CARD GETS DELETED BY DEFAULT.
     */
    public SlotPanel(final int slotSize, Supplier<Boolean> canClick, Supplier<Boolean> canRemove, Consumer<X> consumer) {
        super(new GridLayout(1, slotSize));
        super.setBackground(Color.DARK_GRAY);
        this.consumer = Preconditions.checkNotNull(consumer);
        this.canClick = Preconditions.checkNotNull(canClick);
        this.canRemove = Preconditions.checkNotNull(canRemove);
        this.slotSize = Preconditions.checkNotNull(slotSize);
        this.slots = new HashMap<>();
    }

    /**
     * Adds the given card to the panel by giving it the already
     * defined Consumer(action to perform) and Supplier (possibility to click).
     * @param card to add.
     */
    public void addObject(SlotObject<X> card) {
        Preconditions.checkState(this.slots.size() < slotSize);
        this.slots.put(card.cardName(), card.obj());
        final JButton button = new JButton();
        button.setBackground(getBackground());
        button.setBorderPainted(false);
        button.setActionCommand(card.cardName());
        button.addActionListener(e -> {
            if (this.canClick.get()) {
                this.consumer.accept(this.slots.get(e.getActionCommand()));
                if (this.canRemove.get()) {
                    this.slots.remove(e.getActionCommand());
                    this.remove((JButton)e.getSource());
                }
            }
        });
        try {
            final Image img = ImageIO.read(getClass().getResource("/img/" + card.cardPath() + ".png"));
            button.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        this.add(button);
    }

    @Override
    public void removeAll() {
        this.slots.clear();
        super.removeAll();
        this.revalidate();
        this.repaint();
    }
}
