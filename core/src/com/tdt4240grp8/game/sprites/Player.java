package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.utils.Array;

public class Player {

    public enum Fighters {
        SQUARE, TRIANGLE, CIRCLE
    }

    private Core core;

    private Array<Fighter> fighters;

    private Fighter fighterInProduction;
    private int currentProductionTime;

    private int gold;
    private int health;

    private boolean isGoingLeft;

    public Player(boolean isGoingLeft) {
        fighters = new Array<Fighter>();
        this.isGoingLeft = isGoingLeft;
        core = new Core(isGoingLeft ? 0 : 750, 50);
    }

    public void addFighter(Fighters fighterType) {
        switch (fighterType) {
            case SQUARE:
                Fighter fighter = new Fighter(core.getPosition().x, core.getPosition().y, isGoingLeft);
                fighters.add(fighter);
                break;
            case TRIANGLE:
                break;
            case CIRCLE:
                break;
        }
    }

    public void removeFighter(Fighter fighter) {
        fighters.removeValue(fighter, true);
    }

    public Array<Fighter> getFighters() {
        return fighters;
    }

    public boolean isGoingLeft() {
        return isGoingLeft;
    }

    public Core getCore() {
        return core;
    }

}
