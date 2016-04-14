package com.tdt4240grp8.game.sprites;

import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.observable.PlayerListener;

import java.util.ArrayList;

public class Player {

    public enum Fighters {
        SQUARE, TRIANGLE, CIRCLE
    }

    private Core core;

    private ArrayList<Fighter> fighters;

    private ArrayList<PlayerListener> playerListeners;

    private Fighter fighterInProduction;
    private float currentProductionTime;

    private int gold;
    private int health;

    private boolean isGoingLeft;

    public Player(boolean isGoingLeft) {
        fighters = new ArrayList<Fighter>();
        playerListeners = new ArrayList<PlayerListener>();
        this.isGoingLeft = isGoingLeft;
        core = new Core(isGoingLeft ? 0 : GeometryAssault.WIDTH - 150, 100);
        health = GeometryAssault.PLAYER_START_HEALTHPOINT;
        gold = GeometryAssault.PLAYER_START_GOLD;
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
        System.out.println(playerListeners.size() + " " + playerListeners);
        for (PlayerListener playerListener : playerListeners) {
            System.out.println("loop");
            playerListener.healthChanged(oldValue, health);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void addPlayerListener(PlayerListener playerListener) {
        playerListeners.add(playerListener);
        System.out.println(playerListeners.size() + " " + playerListeners);
    }

    public void removePlayerListener(PlayerListener playerListener) {
        playerListeners.remove(playerListener);
    }

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return health;
    }

}
