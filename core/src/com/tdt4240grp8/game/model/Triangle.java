package com.tdt4240grp8.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

public class Triangle extends Fighter {

    public static final float PRODUCTION_TIME = 2.0f;
    public static final int ATTACK_DAMAGE = 4;
    public static final float ATTACK_COOLDOWN = 0.4f;
    public static final int GOLD_VALUE = 10;
    public static final int PRODUCTION_COST = 400;
    public static final int MAX_HEALTH = 10;
    public static final int MOVEMENT_SPEED = 50;

    public Triangle(float x, float y, boolean isGoingLeft) {
        super(x, y);
        if(isGoingLeft){
            texture = TextureManager.getInstance().getTexture("triangle-fighter-face-right.png");
        }else{
            texture = TextureManager.getInstance().getTexture("triangle-fighter-face-left.png");
        }
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        productionTime = PRODUCTION_TIME;
        attackDamage = ATTACK_DAMAGE;
        attackCooldwn = ATTACK_COOLDOWN;
        goldValue = GOLD_VALUE;
        productionCost = PRODUCTION_COST;
        health = MAX_HEALTH;
        velocity = new Vector2(isGoingLeft ? MOVEMENT_SPEED : -MOVEMENT_SPEED, 0);
        textureRegion = new TextureRegion(TextureManager.getInstance().getTexture("triangle-preview-face-right.png"));
        if(!isGoingLeft){
            textureRegion.flip(true,false);
        }
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }


}