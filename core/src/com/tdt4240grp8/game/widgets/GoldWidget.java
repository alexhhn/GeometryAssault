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

/**
 * Created by Shark on 08/04/16.
 */
public class GoldWidget extends Actor implements PlayerListener {
    private int gold;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter goldFontParameter;

    private BitmapFont goldFont;
    private Image img;
    private int xOffset;

    public GoldWidget(int xPos, int yPos, int xOffset, float scaleValue) {
        this.xOffset = xOffset;
        gold = GeometryAssault.PLAYER_START_GOLD;

        img = new Image(TextureManager.getInstance().getTexture("dollar-icon.png"));
        img.setScale(scaleValue);
        img.setPosition(xPos, yPos);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));

        goldFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        goldFontParameter.size = 35;
        goldFont = generator.generateFont(goldFontParameter);
        goldFont.setColor(Color.WHITE);
    }

    public void render(float delta, SpriteBatch batch){
        goldFont.draw(batch,  "" + gold, img.getX() + xOffset , GeometryAssault.HEIGHT - 50);
    }


    public Image getImg(){
        return img;
    }


    @Override
    public void healthChanged(int oldValue, int newValue) {

    }

    @Override
    public void goldChanged(int oldValue, int newValue) {
        gold = newValue;
    }

    @Override
    public void fighterInProductionChanged(Fighter oldValue, Fighter newValue) {

    }

    @Override
    public void currentProductionTimeChanged(float oldValue, float newValue, float totalValue) {

    }
}
