package com.tdt4240grp8.game.sprites;

import com.tdt4240grp8.game.observable.PlayerListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    public static final float PRODUCTION_TIME = 2.0f;

    public enum Fighters {
        SQUARE, TRIANGLE, CIRCLE
    }

    private Core core;

    private ArrayList<Fighter> fighters;

    private ArrayList<PlayerListener> playerListeners = new ArrayList<PlayerListener>();

    private Fighter fighterInProduction;
    private float currentProductionTime;

    private int gold;
    private int health;

    private boolean isGoingLeft;

    public Player(boolean isGoingLeft) {
        fighters = new ArrayList<Fighter>();
        this.isGoingLeft = isGoingLeft;
        core = new Core(isGoingLeft ? 0 : 750, 50);
        health = 20;
    }

    public void update(float delta) {
        if (fighterInProduction == null) {
            return;
        }
        float oldProductionTime = currentProductionTime;
        currentProductionTime += delta;
        for (PlayerListener playerListener : playerListeners) {
            playerListener.currentProductionTimeChanged(oldProductionTime, currentProductionTime);
        }
        if (currentProductionTime > fighterInProduction.productionTime) {
            fighters.add(fighterInProduction);
            Fighter fighter = fighterInProduction;
            fighterInProduction = null;
            for (PlayerListener playerListener : playerListeners) {
                playerListener.fighterInProductionChanged(fighter, null);
            }
            currentProductionTime = 0;
        }
    }

    public void addFighter(Fighters fighterType) {
        if (fighterInProduction != null) {
            return;
        }
        switch (fighterType) {
            case SQUARE:
                fighterInProduction = new Square(core.getPosition().x, core.getPosition().y, isGoingLeft);
                break;
            case TRIANGLE:
                fighterInProduction = new Triangle(core.getPosition().x, core.getPosition().y, isGoingLeft);
                break;
            case CIRCLE:
                fighterInProduction = new Circle(core.getPosition().x, core.getPosition().y, isGoingLeft);
                break;
        }
        for (PlayerListener playerListener : playerListeners) {
            playerListener.fighterInProductionChanged(null, fighterInProduction);
        }
    }

    public void removeFighter(Fighter fighter) {
        fighters.remove(fighter);
    }

    public ArrayList<Fighter> getFighters() {
        return fighters;
    }

    public boolean isGoingLeft() {
        return isGoingLeft;
    }

    public ArrayList<GameObject> getPlayerGameObjects() {
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>(Arrays.asList(core));
        for (Fighter fighter : fighters) {
            gameObjects.add(fighter);
        }
        return gameObjects;
    }

    public Core getCore() {
        return core;
    }

    public void addGold(int amount) {
        int oldValue = gold;
        gold += amount;
        for (PlayerListener playerListener : playerListeners) {
            playerListener.goldChanged(oldValue, gold);
        }
    }

    public void removeHealth(int amount) {
        int oldValue = health;
        health -= amount;
        for (PlayerListener playerListener : playerListeners) {
            playerListener.healthChanged(oldValue, health);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void addPlayerListener(PlayerListener playerListener) {
        playerListeners.add(playerListener);
    }

    public void removePlayerListener(PlayerListener playerListener) {
        playerListeners.remove(playerListener);
    }

}
