package com.tdt4240grp8.game.sprites;

import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.screens.PlayScreen;

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
        core = new Core(isGoingLeft ?  0 : GeometryAssault.WIDTH - 200, PlayScreen.coreYPos, isGoingLeft);
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
            playerListener.currentProductionTimeChanged(oldProductionTime, currentProductionTime, fighterInProduction.productionTime);
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

    public Fighter addFighter(Fighters fighterType) {
        if (fighterInProduction != null) {
            return null;
        }
        switch (fighterType) {
            case SQUARE:
                if (gold < Square.PRODUCTION_COST) {
                    return null;
                }
                fighterInProduction = new Square(core.getPosition().x, PlayScreen.fighterYPos, isGoingLeft);
                break;
            case TRIANGLE:
                if (gold < Triangle.PRODUCTION_COST) {
                    return null;
                }
                fighterInProduction = new Triangle(core.getPosition().x, PlayScreen.fighterYPos, isGoingLeft);
                break;
            case CIRCLE:
                if (gold < Circle.PRODUCTION_COST) {
                    return null;
                }
                fighterInProduction = new Circle(core.getPosition().x, PlayScreen.fighterYPos, isGoingLeft);
                break;
        }
        for (PlayerListener playerListener : playerListeners) {
            playerListener.fighterInProductionChanged(null, fighterInProduction);
        }
        return fighterInProduction;
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

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return health;
    }

}
