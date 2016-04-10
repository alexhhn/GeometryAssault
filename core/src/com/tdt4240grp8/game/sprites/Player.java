package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.utils.Array;
import com.tdt4240grp8.game.widgets.Observer;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Observer> observers =  new ArrayList<Observer>();

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

    public Player(boolean isGoingLeft, int gold, int health) {
        this.gold = gold;
        this.health = health;
        fighters = new Array<Fighter>();
        this.isGoingLeft = isGoingLeft;
        core = new Core(isGoingLeft ? 0 : 750, 50);
        notifyAllObservers();
    }

    public void addFighter(Fighters fighterType) {
        switch (fighterType) {
            case SQUARE:
                Fighter fighter = new Fighter(core.getPosition().x, core.getPosition().y, isGoingLeft);
                fighters.add(fighter);
                decreaseGold(100);
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

    // All observer patterns function

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer: observers){
            observer.update();
        }
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
        notifyAllObservers();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        notifyAllObservers();
    }

    public void increaseGold(int gold){
        this.gold += gold;
        notifyAllObservers();
    }

    public void decreaseGold(int gold){
        this.gold -= gold;
        notifyAllObservers();
    }

    public void decreaseHealth(int hp){
        this.health -= hp;
        notifyAllObservers();
    }
}
