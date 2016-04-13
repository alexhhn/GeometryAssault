package com.tdt4240grp8.game.states;

public class NormalState implements GameModeState {

    @Override
    public int getGoldMultiplier() {
        return 1;
    }

    @Override
    public float getSpeedMultiplier() {
        return 1;
    }
}
