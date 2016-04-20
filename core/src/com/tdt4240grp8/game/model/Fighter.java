package com.tdt4240grp8.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.observable.FighterListener;

import java.util.ArrayList;

/**
 * The base class for all fighters
 */
public abstract class Fighter extends GameObject {

    // how long until this fighter can attack again
    private float currentAttackCooldown;

    // how far to update the fighter's position each frame
    protected Vector2 velocity;
    // current health
    protected int health;
    // how much damage each attack does
    protected int attackDamage;
    // how long the fighter has to wait between attacks
    protected float attackCooldwn;
    // how long it takes to produce this fighter
    protected float productionTime;
    // how much gold the other player is rewarded with for defeating this fighter
    protected int goldValue;
    // how much it costs to put this fighter into production
    protected int productionCost;
    // the preview image for when this fighter is in production
    protected TextureRegion textureRegion;

    protected ArrayList<FighterListener> fighterListeners;

    public Fighter(float x, float y) {
        fighterListeners = new ArrayList<FighterListener>();
        position = new Vector2(x, y);
        currentAttackCooldown = 0;
    }

    /**
     * Called every frame to update the attack cooldown
     */
    public void update(float delta) {
        currentAttackCooldown -= delta;
        if (currentAttackCooldown < 0) {
            currentAttackCooldown = 0;
        }
    }
    public TextureRegion getProductionImage(){
        return textureRegion;
    }

    public abstract int getMaxHealth();

    /**
     * Called whenever the fighter is supposed to move
     * (i.e. whenever there is no enemy in front of it)
     */
    public void move(float delta) {
        Vector2 oldValue = position;
        position.x += velocity.x * delta;;
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

    public int getGoldValue() { return goldValue; }

    public int getProductionCost() {
        return productionCost;
    }

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
