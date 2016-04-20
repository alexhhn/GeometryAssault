package com.tdt4240grp8.game.observable;

import com.badlogic.gdx.math.Vector2;

/**
 * Listener interface implemented by classes that want to observe the Fighter-class
 */
public interface FighterListener {

    /**
     * Called whenever the fighter health changes
     */
    void healthChanged(int oldValue, int newValue, int maxValue);

    /**
     * Called whenever the fighter position changes
     */
    void positionChanged(Vector2 oldValue, Vector2 newValue);
}
