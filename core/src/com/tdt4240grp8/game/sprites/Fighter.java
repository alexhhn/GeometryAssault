package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Fighter extends GameObject {

    private float currentAttackCooldown;

    protected int health;
    protected int attackDamage;
    protected float attackCooldwn;
    protected float productionTime;
    protected int goldValue;

    public Fighter(float x, float y, boolean isGoingLeft) {
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
        position.x += velocity.x * delta;
        bounds.x += velocity.x * delta;
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
        health -= amount;
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
}
