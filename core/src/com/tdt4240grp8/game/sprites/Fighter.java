package com.tdt4240grp8.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240grp8.game.managers.TextureManager;

public class Fighter {

    private Vector2 position;
    private Vector2 velocity;

    private Rectangle bounds;

    private int health;
    private int attackDamage;

    private Texture texture;

    public Fighter(float x, float y, boolean isGoingLeft) {
        position = new Vector2(x, y);
        velocity = new Vector2(isGoingLeft ? 50 : -50, 0);
        texture = TextureManager.getInstance().getTexture("bird.png");
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        position.x += velocity.x * delta;
        bounds.x += velocity.x * delta;
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
