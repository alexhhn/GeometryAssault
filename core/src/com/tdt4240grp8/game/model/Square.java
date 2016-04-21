package com.tdt4240grp8.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

public class Square extends Fighter {

    public static final float PRODUCTION_TIME = 1.5f;
    public static final int ATTACK_DAMAGE = 2;
    public static final float ATTACK_COOLDOWN = 0.4f;
    public static final int GOLD_VALUE = 200;
    public static final int PRODUCTION_COST = 200;
    public static final int MAX_HEALTH = 40;
    public static final int MOVEMENT_SPEED = 125;

    public Square(float x, float y, boolean isGoingLeft) {
        super(x, y);
        if(isGoingLeft){
            texture = TextureManager.getInstance().getTexture("square-fighter-face-right.png");
        }else{
            texture = TextureManager.getInstance().getTexture("square-fighter-face-left.png");
        }
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        productionTime = PRODUCTION_TIME;
        attackDamage = ATTACK_DAMAGE;
        attackCooldwn = ATTACK_COOLDOWN;
        goldValue = GOLD_VALUE;
        productionCost = PRODUCTION_COST;
        health = MAX_HEALTH;
        velocity = new Vector2(isGoingLeft ? MOVEMENT_SPEED : -MOVEMENT_SPEED, 0);
        textureRegion = new TextureRegion(TextureManager.getInstance().getTexture("square-preview-face-right.png"));
        if(!isGoingLeft){
            textureRegion.flip(true,false);
        }
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

}
