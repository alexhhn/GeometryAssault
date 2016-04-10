package com.tdt4240grp8.game.widgets;

import com.tdt4240grp8.game.sprites.Player;

/**
 * Created by Shark on 10/04/16.
 */
public abstract class Observer {
    protected Player player;
    public abstract void update();

}
