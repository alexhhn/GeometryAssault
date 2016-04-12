package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.sprites.Fighter;

public class HealthBar extends ProgressBar {

    private Fighter fighter;

    public HealthBar(float min, float max, float stepSize, boolean vertical, Fighter fighter) {
        super(min, max, stepSize, vertical, new ProgressBarStyle());
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("healthBarGreen.png")));
        style.knobAfter = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("healthBarRed.png")));
        setStyle(style);
        setSize(25, 5);
        this.fighter = fighter;
    }

    public Fighter getFighter() {
        return fighter;
    }
}
