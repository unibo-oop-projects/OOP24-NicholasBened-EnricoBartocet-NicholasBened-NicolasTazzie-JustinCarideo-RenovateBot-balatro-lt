package it.unibo.balatrolt.controller.impl;

import java.util.Iterator;

import it.unibo.balatrolt.controller.api.HandController;
import it.unibo.balatrolt.model.api.PlayableCard;
import it.unibo.balatrolt.model.api.levels.Ante;
import it.unibo.balatrolt.model.api.levels.AnteFactory;
import it.unibo.balatrolt.model.impl.levels.AnteFactoryImpl;

public class HandControllerImpl implements HandController{
    private Iterator<Ante> antes;
    private int numHandSlot;

    public HandControllerImpl() {
        AnteFactory anteFactory = new AnteFactoryImpl(0, null, null);
        anteFactory.generateList(8);
        //this.numHandSlot = new BlindCards
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasNext'");
    }

    @Override
    public PlayableCard next() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'next'");
    }

    @Override
    public int numHandSlot() {
        return this.numHandSlot;
    }

}
