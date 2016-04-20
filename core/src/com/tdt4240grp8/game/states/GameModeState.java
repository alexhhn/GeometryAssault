package com.tdt4240grp8.game.states;

/**
 * State pattern interface that allows for different game modes
 */
public interface GameModeState {

    /**
     * Returns the gold-multiplier for this game mode
     */
    int getGoldMultiplier();

    /**
     * Returns the speed-multiplier for this game mode
     */
    float getSpeedMultiplier();
}
