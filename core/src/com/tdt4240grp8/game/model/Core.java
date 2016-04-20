package com.tdt4240grp8.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

/**
 * The core (fortress at both ends of the screen)
 */
public class Core extends GameObject {

    public Core(int x, int y, boolean isGoingLeft) {
        position = new Vector2(x, y);

        if (isGoingLeft){
            texture = TextureManager.getInstance().getTexture("devil-face-right.png");
        } else{
            texture = TextureManager.getInstance().getTexture("devil-face-left.png");
        }

        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
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
