package com.tdt4240grp8.game.observable;

import com.tdt4240grp8.game.sprites.Fighter;

public interface PlayerListener {

    public void healthChanged(int oldValue, int newValue);
    public void goldChanged(int oldValue, int newValue);
    public void fighterInProductionChanged(Fighter oldValue, Fighter newValue);
    public void currentProductionTimeChanged(float oldValue, float newValue, float totalValue);
}
