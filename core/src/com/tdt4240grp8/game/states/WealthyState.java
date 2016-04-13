package com.tdt4240grp8.game.states;

public class WealthyState implements GameModeState {

    @Override
    public int getGoldMultiplier() {
        return 2;
    }

    @Override
    public float getSpeedMultiplier() {
        return 1;
    }
}