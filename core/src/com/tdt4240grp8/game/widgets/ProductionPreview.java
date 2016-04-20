package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.screens.PlayScreen;
import com.tdt4240grp8.game.model.Fighter;

/**
 * Displays the fighter currently in production and a bar showing its progress
 */
public class ProductionPreview extends ProgressBar implements PlayerListener {

    private Image img;

    /**
     * Constructor for the production preview class
     * @param xPos x position of the widget
     * @param yPos y position of the widget
     * @param xOffset x offset for the image-part of the widget
     */
    public ProductionPreview(int xPos, int yPos, int xOffset) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("ProgressBar.png")));
        style.knobAfter = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("knob.png")));
        setStyle(style);
        setValue(1);
        setSize(150, 30);
        setPosition(xPos, yPos);
        img = new Image();
        img.setPosition(xPos + xOffset, PlayScreen.previewImageYPos);
        img.setSize(50,50);
        img.setVisible(false);
    }

    @Override
    public void healthChanged(int oldValue, int newValue) {}

    @Override
    public void goldChanged(int oldValue, int newValue) {}

    @Override
    public void fighterInProductionChanged(Fighter oldValue, Fighter newValue) {
        if (newValue == null) { // if a new fighter has left production, hide the widget
            setVisible(false);
            img.setVisible(false);
        } else{     // if a new fighter has entered production, show the widget
            setVisible(true);
            img.setVisible(true);
            img.setDrawable(new TextureRegionDrawable(newValue.getProductionImage()));
        }
    }

    @Override
    public void currentProductionTimeChanged(float oldValue, float newValue, float totalValue) {
        setValue(newValue / totalValue);
    }

    public Image getImg() {
        return img;
    }
}
