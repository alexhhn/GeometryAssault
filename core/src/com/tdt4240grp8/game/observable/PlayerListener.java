package com.tdt4240grp8.game.observable;

import com.tdt4240grp8.game.model.Fighter;

/**
 * Listener interface implemented by classes that want to observe the Player-class
 */
public interface PlayerListener {

    /**
     * Called whenever the player (core) health changes
     */
    void healthChanged(int oldValue, int newValue);

    /**
     * Called whenever the player gold changes
     */
    void goldChanged(int oldValue, int newValue);

    /**
     * Called whenever the fighter in production changes
     * (when a new fighter is put into production, or when one spawns)
     */
    void fighterInProductionChanged(Fighter oldValue, Fighter newValue);

    /**
     * Called every frame when a fighter is in production
     */
    void currentProductionTimeChanged(float oldValue, float newValue, float totalValue);
}
