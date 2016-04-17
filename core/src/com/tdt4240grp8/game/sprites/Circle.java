package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

public class Circle extends Fighter {

    public static final float PRODUCTION_TIME = 1.0f;
    public static final int ATTACK_DAMAGE = 1;
    public static final float ATTACK_COOLDOWN = 0.4f;
    public static final int GOLD_VALUE = 10;
    public static final int MAX_HEALTH = 10;
    public static final int MOVEMENT_SPEED = 200;

    public Circle(float x, float y, boolean isGoingLeft) {
        super(x, y, isGoingLeft);

        if(isGoingLeft){
            texture = TextureManager.getInstance().getTexture("circle-fighter-face-right.png");
        }else{
            texture = TextureManager.getInstance().getTexture("circle-fighter-face-left.png");
        }
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        productionTime = PRODUCTION_TIME;
        attackDamage = ATTACK_DAMAGE;
        attackCooldwn = ATTACK_COOLDOWN;
        goldValue = GOLD_VALUE;
        health = MAX_HEALTH;
        velocity = new Vector2(isGoingLeft ? MOVEMENT_SPEED : -MOVEMENT_SPEED, 0);
        textureRegion = new TextureRegion(TextureManager.getInstance().getTexture("circle-preview-face-right.png"));
        if(!isGoingLeft){
            textureRegion.flip(true,false);
        }
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }


}