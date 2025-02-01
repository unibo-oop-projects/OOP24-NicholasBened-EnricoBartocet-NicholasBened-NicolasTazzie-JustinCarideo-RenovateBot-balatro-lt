package it.unibo.balatrolt.controller.api.communication;

import java.util.List;

public record AnteInfo(int id, List<BlindInfo> blinds, int blindId) {

}
