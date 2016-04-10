package com.tdt4240grp8.game.widgets;

import com.tdt4240grp8.game.sprites.Player;

/**
 * Created by Shark on 08/04/16.
 */
public class GoldWidget extends Observer {
    private int gold;

    private void increaseAmount(int amount){

    }

    private void decreaseAmount(int amount){

    }

    public GoldWidget(Player player) {
        this.player = player;
        this.player.attach(this);
    }


    public int getGold() {
        return gold;
    }

    @Override
    public void update() {
        this.gold = player.getGold();
    }
}
