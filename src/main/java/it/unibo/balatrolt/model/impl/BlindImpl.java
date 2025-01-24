package it.unibo.balatrolt.model.impl;

import it.unibo.balatrolt.model.api.Blind;

/**
 * An implementation for the {@link Blind} interface.
 * @author Enrico Bartocetti
 */
public final class BlindImpl implements Blind {
    private final BlindConfiguration config;
    private int currentChips;

    /**
     * Instance a new BlindImpl starting from the configuration.
     * @param config the configuration for the Blind
     */
    public BlindImpl(final BlindConfiguration config) {
        this.config = config;
    }

    @Override
    public int getBlindNumber() {
        return this.config.id();
    }

    @Override
    public int getMinimumChips() {
        return this.config.baseChip();
    }

    @Override
    public int getCurrentChips() {
        return this.currentChips;
    }

    @Override
    public void incrementChips(final int handChips) {
        if (handChips <= 0) {
            throw new IllegalArgumentException("Negative chips aren't allowed");
        }
        this.currentChips += handChips;
    }

    @Override
    public boolean isOver() {
        return this.currentChips >= this.getMinimumChips();
    }

    @Override
    public int getReward() {
        return this.config.reward();
    }
}
