package it.unibo.balatrolt.model.impl.levels;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;

import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.Blind;

/**
 * An implementation of the {@link Ante} interface.
 * @author Bartocetti Enrico
 */
public class AnteImpl implements Ante {
    private static int NUM_BLINDS = 3;
    private final int id;
    private final List<Blind> blinds;
    private int currentBlind;

    /**
     * Initialize an Ante from his number.
     * @param id its ID
     */
    public AnteImpl(final int id) {
        this.id = id;
        this.blinds = new BlindFactoryImpl(
            (a, b) -> (int)(Math.pow(a, 2) * 10 + Math.pow(b, 4) * 10),
            n -> 4 + n
        ).createList(NUM_BLINDS, id);
    }

    @Override
    public int getAnteNumber() {
        return this.id;
    }

    @Override
    public List<Blind> getBlinds() {
        return Collections.unmodifiableList(this.blinds);
    }

    @Override
    public Optional<Blind> getCurrentBlind() {
        return Optional.fromJavaUtil(
            this.blinds.stream()
            .map(blinds::indexOf)
            .filter(n -> n == this.currentBlind)
            .map(blinds::get)
            .findAny()
        );
    }

    @Override
    public void nextBlind() {
        if (!this.isOver()) {
            this.currentBlind++;
        }
    }

    @Override
    public boolean isOver() {
        return this.currentBlind >= NUM_BLINDS;
    }

}
