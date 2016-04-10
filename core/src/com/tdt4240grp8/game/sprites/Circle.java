package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.tdt4240grp8.game.managers.TextureManager;

public class Circle extends Fighter {

    public static final float PRODUCTION_TIME = 2.0f;

    public Circle(float x, float y, boolean isGoingLeft) {
        super(x, y, isGoingLeft);
        texture = TextureManager.getInstance().getTexture("circle.png");
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

}