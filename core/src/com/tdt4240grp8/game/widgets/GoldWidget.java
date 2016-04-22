package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.screens.PlayScreen;
import com.tdt4240grp8.game.model.Fighter;

/**
 * Displays the player gold
 */
public class GoldWidget extends Actor implements PlayerListener {

    private int gold;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter goldFontParameter;

    private BitmapFont goldFont;
    private Image img;

    public BitmapFont getGoldFont() {
        return goldFont;
    }

    public void setGoldFont(BitmapFont goldFont) {
        this.goldFont = goldFont;
    }

    /**
     * Constructor of the gold widget
     * @param xPos x position of the widget
     * @param yPos y position of the widget
     */
    public GoldWidget(int xPos, int yPos) {
        gold = GeometryAssault.PLAYER_START_GOLD;

        img = new Image(TextureManager.getInstance().getTexture("gold-bar-icon.png"));
        img.setPosition(xPos, yPos);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arvo-Bold.ttf"));
        goldFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        goldFontParameter.size = 45;
        goldFont = generator.generateFont(goldFontParameter);
        goldFont.setColor(Color.valueOf("f85900"));
    }


    /**
     * Called on every render frame
     * Draws the gold text to the screen
     */
    public void render(SpriteBatch batch){
        goldFont.draw(batch,  "$" + gold, img.getX() - 30 , PlayScreen.hudTextYPos);
    }


    public Image getImg(){
        return img;
    }

    public void setImg(Image img){
        this.img = img;
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
