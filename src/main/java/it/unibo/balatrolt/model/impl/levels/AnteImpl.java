package it.unibo.balatrolt.model.impl.levels;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.Blind;
import it.unibo.balatrolt.model.api.levels.BlindModifier;

/**
 * An implementation of the {@link Ante} interface.
 * @author Bartocetti Enrico
 */
public final class AnteImpl implements Ante {
    private final AnteConfiguration configuration;
    private final List<Blind> blinds;
    private int currentBlind;

    /**
     * Initialize an Ante from his number.
     * @param config the configuration of the Ante
     * @param blindModifier the modifier that tells how to change the statistics of the Blind
     */
    public AnteImpl(final AnteConfiguration config, final BlindModifier blindModifier) {
        this.configuration = Preconditions.checkNotNull(config);
        this.blinds = new BlindFactoryImpl(
            config.baseChipCalc(),
            config.rewardCalc(),
            Preconditions.checkNotNull(blindModifier)
        ).createList(config.numBlinds(), config.id());
    }

    @Override
    public int getAnteNumber() {
        return this.configuration.id();
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
        return this.currentBlind >= this.configuration.numBlinds();
    }

}
