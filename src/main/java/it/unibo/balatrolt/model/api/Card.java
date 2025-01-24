package it.unibo.balatrolt.model.api;

import com.google.common.base.Optional;

public interface Card {

    Optional<Modifier> getModifier();
}
