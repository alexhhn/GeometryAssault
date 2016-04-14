package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.FighterListener;
import com.tdt4240grp8.game.sprites.Fighter;

public class HealthBar extends ProgressBar implements FighterListener {

    public HealthBar(float min, float max, float stepSize, boolean vertical) {
        super(min, max, stepSize, vertical, new ProgressBarStyle());
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("healthBarGreen.png")));
        style.knobAfter = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("healthBarRed.png")));
        setStyle(style);
        setValue(1);
        setSize(25, 5);
    }

    @Override
    public void healthChanged(int oldValue, int newValue, int maxValue) {
        setValue((float) newValue / (float) maxValue);
        if (getValue() == 0f) {
            remove();
        }
    }

    @Override
    public void positionChanged(Vector2 oldValue, Vector2 newValue) {
        setPosition(newValue.x, newValue.y + 50);
    }
}
