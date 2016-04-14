package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

public class Square extends Fighter {

    public static final float PRODUCTION_TIME = 2.0f;
    public static final int ATTACK_DAMAGE = 1;
    public static final float ATTACK_COOLDOWN = 0.4f;
    public static final int GOLD_VALUE = 10;
    public static final int MAX_HEALTH = 40;
    public static final int MOVEMENT_SPEED = 50;

    public Square(float x, float y, boolean isGoingLeft) {
        super(x, y, isGoingLeft);
        textureRegion = new TextureRegion(TextureManager.getInstance().getTexture("sprite-animation2.png"));
        animation = new Animation(textureRegion, 6, 3);
        bounds = new Rectangle(x, y, animation.getFrame().getRegionWidth(), animation.getFrame().getRegionHeight());
        productionTime = PRODUCTION_TIME;
        attackDamage = ATTACK_DAMAGE;
        attackCooldwn = ATTACK_COOLDOWN;
        goldValue = GOLD_VALUE;
        health = MAX_HEALTH;
        velocity = new Vector2(isGoingLeft ? MOVEMENT_SPEED : -MOVEMENT_SPEED, 0);
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

}
