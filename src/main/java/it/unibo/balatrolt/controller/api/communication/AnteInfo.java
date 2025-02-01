package it.unibo.balatrolt.controller.api.communication;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

public class AnteInfo {

    private final int id;
    private final List<BlindInfo> blinds;
    private final int currentBlindId;

    public AnteInfo(int id, List<BlindInfo> blinds, int currentBlindId) {
        this.id = id;
        this.blinds = Collections.unmodifiableList(Preconditions.checkNotNull(blinds));
        this.currentBlindId = currentBlindId;
    }

    public int id() {
        return this.id;
    }

    public List<BlindInfo> blinds() {
        return this.blinds;
    }

    public int currentBlindId() {
        return this.currentBlindId;
    }

}
