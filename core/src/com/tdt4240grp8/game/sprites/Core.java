package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

public class Core {

    private Texture texture;

    private Rectangle bounds;

    private Vector2 position;

    public Core(int x, int y) {
        position = new Vector2(x, y);
        texture = TextureManager.getInstance().getTexture("bottomtube.png");
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
