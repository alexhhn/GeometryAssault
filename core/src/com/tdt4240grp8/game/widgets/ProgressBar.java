package com.tdt4240grp8.game.widgets;

import com.tdt4240grp8.game.sprites.Player;

/**
 * Created by Shark on 08/04/16.
 */
public class ProgressBar extends Observer {

    public ProgressBar(Player player) {
        this.player = player;
        this.player.attach(this);
    }
    @Override
    public void update() {

    }
}
