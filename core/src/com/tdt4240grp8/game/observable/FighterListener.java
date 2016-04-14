package com.tdt4240grp8.game.observable;

import com.badlogic.gdx.math.Vector2;

public interface FighterListener {

    public void healthChanged(int oldValue, int newValue, int maxValue);
    public void positionChanged(Vector2 oldValue, Vector2 newValue);
}
