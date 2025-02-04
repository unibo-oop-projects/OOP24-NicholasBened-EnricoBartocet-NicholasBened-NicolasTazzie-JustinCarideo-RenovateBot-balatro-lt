package it.unibo.balatrolt.controller.api.communication;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Immutable class used to comunicate the informations of an Ante.
 */
public final class AnteInfo {

    private final int id;
    private final List<BlindInfo> blinds;
    private final int currentBlindId;

    /**
     * Create an AnteInfo object.
     * @param id the id of the ante
     * @param blinds the list of the Blinds that the Ante has
     * @param currentBlindId the id of the current Blind
     */
    public AnteInfo(final int id, final List<BlindInfo> blinds, final int currentBlindId) {
        this.id = id;
        this.blinds = Collections.unmodifiableList(Preconditions.checkNotNull(blinds));
        this.currentBlindId = currentBlindId;
    }

    /**
     * @return the id of the Ante
     */
    public int id() {
        return this.id;
    }

    /**
     * @return the list of the Blinds belonging to this Ante
     */
    public List<BlindInfo> blinds() {
        return this.blinds;
    }

    /**
     * @return the id of the current Blind
     */
    public int currentBlindId() {
        return this.currentBlindId;
    }

}
