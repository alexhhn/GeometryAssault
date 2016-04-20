package com.tdt4240grp8.game.states;

/**
 * This is a game mode where the players get lots of gold
 */
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