package com.tdt4240grp8.game.widgets;

import com.tdt4240grp8.game.sprites.Player;

/**
 * Created by Shark on 08/04/16.
 */
public class HealthWidget extends Observer{

    private int health;
    public HealthWidget(Player player) {
        this.player = player;
        this.player.attach(this);
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void update() {
        this.health = player.getHealth();
    }
}
