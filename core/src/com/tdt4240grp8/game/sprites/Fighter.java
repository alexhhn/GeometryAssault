package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.observable.FighterListener;
import com.tdt4240grp8.game.observable.PlayerListener;

import java.util.ArrayList;

public abstract class Fighter extends GameObject {

    private float currentAttackCooldown;

    protected int health;
    protected int attackDamage;
    protected float attackCooldwn;
    protected float productionTime;
    protected int goldValue;

    protected ArrayList<FighterListener> fighterListeners;

    public Fighter(float x, float y, boolean isGoingLeft) {
        fighterListeners = new ArrayList<FighterListener>();
        position = new Vector2(x, y);
        currentAttackCooldown = 0;
    }

    public void update(float delta) {
        currentAttackCooldown -= delta;
        if (currentAttackCooldown < 0) {
            currentAttackCooldown = 0;
        }
    }

    public abstract int getMaxHealth();

    public void move(float delta) {
        Vector2 oldValue = position;
        position.x += velocity.x * delta;
        bounds.x += velocity.x * delta;
        for (FighterListener fighterListener : fighterListeners) {
            fighterListener.positionChanged(oldValue, position);
        }
    }

    public boolean attackOffCooldown() {
        return currentAttackCooldown <= 0;
    }

    public void resetAttackCooldown() {
        currentAttackCooldown = attackCooldwn;
    }

    public void attack(Fighter fighter) {
        fighter.removeHealth(attackDamage);
    }

    public void attack(Player player) {
        player.removeHealth(attackDamage);
    }

    public void removeHealth(int amount) {
        int oldValue = health;
        health -= amount;
        for (FighterListener fighterListener : fighterListeners) {
            fighterListener.healthChanged(oldValue, health, getMaxHealth());
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getHeath() {
        return health;
    }

    public int getGoldValue() { return goldValue; }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void addFighterListener(FighterListener fighterListener) {
        fighterListeners.add(fighterListener);
    }

    public void removeFighterListener(FighterListener fighterListener) {
        fighterListeners.remove(fighterListener);
    }
}
