package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.sprites.Fighter;
import com.tdt4240grp8.game.sprites.Square;

public class ProductionPreview extends ProgressBar implements PlayerListener {

    private Image img;
    private Stage st;
    private static final String noneInProduction = "noneInProduction.png";


    public ProductionPreview(int xPos, int yPos, int xOffset, float min, float max, float stepSize, boolean vertical, Stage st) {
        super(min, max, stepSize, vertical, new ProgressBarStyle());
        this.st = st;
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("ProgressBar.png")));
        style.knobAfter = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("knob.png")));
        setStyle(style);
        setValue(1);
        setSize(100, 20);
        setPosition(xPos, yPos);
        img = new Image(TextureManager.getInstance().getTexture(noneInProduction));
        img.setPosition(xPos + xOffset, yPos);
    }

    @Override
    public void healthChanged(int oldValue, int newValue) {}

    @Override
    public void goldChanged(int oldValue, int newValue) {}

    @Override
    public void fighterInProductionChanged(Fighter oldValue, Fighter newValue) {
        if (newValue == null) {
            img.setDrawable(new SpriteDrawable(new Sprite(TextureManager.getInstance().getTexture(noneInProduction))));
            return;
        }
        img.setDrawable(new SpriteDrawable(new Sprite(newValue.getTexture())));
    }

    @Override
    public void currentProductionTimeChanged(float oldValue, float newValue, float totalValue) {
        setValue(newValue / totalValue);
    }

    public Image getImg() {
        return img;
    }
}
