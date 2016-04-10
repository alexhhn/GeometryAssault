package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Fighter extends GameObject {

    private int health;
    private int attackDamage;
    private float attackCooldwn;
    private float currentAttackCooldown;

    public Fighter(float x, float y, boolean isGoingLeft) {
        position = new Vector2(x, y);
        velocity = new Vector2(isGoingLeft ? 50 : -50, 0);
        health = 10;
        attackDamage = 1;
        attackCooldwn = 0.4f;
        currentAttackCooldown = 0;
    }

    public void update(float delta) {
        currentAttackCooldown -= delta;
        if (currentAttackCooldown < 0) {
            currentAttackCooldown = 0;
        }
    }

    public float getCurrentAttackCooldown() {
        return currentAttackCooldown;
    }

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
