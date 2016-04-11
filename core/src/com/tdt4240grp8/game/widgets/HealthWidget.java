package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.sprites.Fighter;
import com.tdt4240grp8.game.sprites.Player;


/**
 * Created by Shark on 08/04/16.
 */
public class HealthWidget extends Actor implements PlayerListener {

    private int health;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter healthFontParameter;

    private BitmapFont healthFont;

    public HealthWidget() {
        health = 20;
        Image img = new Image(TextureManager.getInstance().getTexture("heart-icon.png"));
        img.setSize(40,34);
        img.setPosition(0, 0);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));

        healthFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        healthFontParameter.size = 25;
        healthFont = generator.generateFont(healthFontParameter);
        healthFont.setColor(Color.WHITE);

    }

    public void render(float delta, SpriteBatch batch) {
        healthFont.draw(batch,  "" + health, 70 , GeometryAssault.HEIGHT - 70);
    }

    @Override
    public void healthChanged(int oldValue, int newValue) {
        System.out.println("hei");
        health = newValue;
    }

    @Override
    public void goldChanged(int oldValue, int newValue) {}

    @Override
    public void fighterInProductionChanged(Fighter oldValue, Fighter newValue) {}

    @Override
    public void currentProductionTimeChanged(float oldValue, float newValue) {}
}
