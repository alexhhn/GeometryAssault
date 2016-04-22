package com.tdt4240grp8.game.model;

import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.screens.PlayScreen;

import java.util.ArrayList;

/**
 * The main model class. Contains all game state information for a player.
 */
public class Player {

    public enum Fighters {
        SQUARE, TRIANGLE, CIRCLE
    }

    // this player's core
    private Core core;

    // list of all fighters this player currently has
    private ArrayList<Fighter> fighters;

    // all widgets listening to this player
    private ArrayList<PlayerListener> playerListeners;

    // the current fighter in production
    private Fighter fighterInProduction;

    // how long the fighter has been in production
    private float currentProductionTime;

    private int gold;
    private int health;

    // indicates whether this player is on the left or right side of the screen
    private boolean isGoingLeft;

    public Player(boolean isGoingLeft) {
        fighters = new ArrayList<Fighter>();
        playerListeners = new ArrayList<PlayerListener>();
        this.isGoingLeft = isGoingLeft;
        core = new Core(isGoingLeft ?  0 : GeometryAssault.WIDTH - 200, PlayScreen.coreYPos, isGoingLeft);
        health = GeometryAssault.PLAYER_START_HEALTHPOINTS;
        gold = GeometryAssault.PLAYER_START_GOLD;
    }

    /**
     * Called once every frame to update production time and check if the fighter in production is ready to spawn
     */
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

    /**
     * Called whenever a player clicks on a button to create a fighter.
     * If no figher is in production and the player has enough gold, send a new fighter to production.
     * @param fighterType the type of fighter to create
     * @return null if a fighter is already in production or the player doesn't have enough gold, otherwise return the fighter
     */
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
}
