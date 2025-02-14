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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Represent a Generic panel in which we can store any card.
 * we can also set the action to perform when the buttons are pressed.
 * @param <X> object the panel has to hold.
 * @author Benedetti Nicholas
 */
@SuppressFBWarnings(
    justification = """
        Since we extend JPanel (which is Serializable), it's required to make the class Serializable,
        otherwhise an exception will be thrown when serializing this class.
        Anyway we are sure that we will never serialize this class, because if we want to save the game
        we will only save the informations stored in the model, creating a new View when needed.
        """,
    value =  "SE_BAD_FIELD"
)
final class SlotPanel<X> extends JPanel {
    static final long serialVersionUID = 1L;

    private final int slotSize;
    private final int buttonWidth;
    private final int buttonHeight;
    private final Map<String, X> slots;
    private final Consumer<X> consumer;
    private final Supplier<Boolean> clickable;
    private final Supplier<Boolean> removable;
    /*
     * It holds the full obj to eventually give it back to the controller
     * and holds the name of the card to set the image.
     */
    record SlotObject<X>(X obj, String cardName, String cardPath) { }

    /**
     * Sets the parameters of the panel.
     * @param slotSize max size of the slot.
     * @param buttonWidth width of the button.
     * @param buttonHeigth height of the button.
     * @param clickable possibility to click.
     * @param removable possibility to remove.
     * @param consumer action to perform with the pressed card. THE CARD GETS DELETED BY DEFAULT.
     */
    SlotPanel(
            final int slotSize,
            final int buttonWidth,
            final int buttonHeigth,
            final Supplier<Boolean> clickable,
            final Supplier<Boolean> removable,
            final Consumer<X> consumer
        ) {
        super(new GridLayout(1, slotSize));
        super.setBackground(Color.DARK_GRAY);
        Preconditions.checkArgument(buttonWidth >= 0);
        Preconditions.checkArgument(buttonHeigth >= 0);
        this.consumer = Preconditions.checkNotNull(consumer);
        this.clickable = Preconditions.checkNotNull(clickable);
        this.removable = Preconditions.checkNotNull(removable);
        this.slotSize = Preconditions.checkNotNull(slotSize);
        this.slots = new HashMap<>();
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeigth;
    }

    /**
     * Adds the given card to the panel by giving it the already
     * defined Consumer(action to perform) and Supplier (possibility to click).
     * @param card to add.
     */
    void addObject(final SlotObject<X> card) {
        Preconditions.checkState(this.slots.size() < slotSize);
        this.slots.put(card.cardName(), card.obj());
        final JButton button = new JButton();
        button.setBackground(getBackground());
        button.setBorderPainted(false);
        button.setActionCommand(card.cardName());
        button.addActionListener(e -> {
            if (this.clickable.get()) {
                this.consumer.accept(this.slots.get(e.getActionCommand()));
                if (this.removable.get()) {
                    this.slots.remove(e.getActionCommand());
                    this.remove((JButton) e.getSource());
                }
            }
        });
        try {
            final Image img = ImageIO.read(getClass().getResource("/img/" + card.cardPath() + ".png"));
            button.setIcon(new ImageIcon(img.getScaledInstance(this.buttonWidth, this.buttonHeight, Image.SCALE_DEFAULT)));
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
