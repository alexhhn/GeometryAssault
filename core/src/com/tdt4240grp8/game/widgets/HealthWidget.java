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
    private  Image img;

    private int xOffset;
    private int xPos, yPos;

    public HealthWidget(int xPos, int yPos, int xOffset, float scaleValue){
        this.xOffset = xOffset;
        health = GeometryAssault.PLAYER_START_HEALTHPOINT;

        img = new Image(TextureManager.getInstance().getTexture("heart-icon.png"));
        img.setScale(scaleValue);
        img.setPosition(xPos, yPos);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));

        healthFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        healthFontParameter.size = 30;
        healthFont = generator.generateFont(healthFontParameter);
        healthFont.setColor(Color.WHITE);

    }

    public Image getImg() {
        return img;
    }

    public void render(float delta, SpriteBatch batch) {
        healthFont.draw(batch,  "" + health, img.getX() + xOffset , GeometryAssault.HEIGHT - 135);
//        System.out.println(img.getX() + " "+ img.getWidth()/2 +" "+  xOffset);
    }

    @Override
    public void healthChanged(int oldValue, int newValue) {
        health = newValue;
    }

    @Override
    public void goldChanged(int oldValue, int newValue) {}

    @Override
    public void fighterInProductionChanged(Fighter oldValue, Fighter newValue) {}

    @Override
    public void currentProductionTimeChanged(float oldValue, float newValue, float totalValue) {}
}
