package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.FighterListener;

/**
 * Displays a health bar over a fighter
 */
public class HealthBar extends ProgressBar implements FighterListener {

    /**
     * Creates a LibGdx ProgressBar and makes it look like a health bar
     */
    public HealthBar() {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("HealthBar.png")));
        style.knobAfter = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("HealthBarKnob.png")));
        setVisible(false);
        setStyle(style);
        setValue(1);
        setSize(100, 5);
    }

    @Override
    public void healthChanged(int oldValue, int newValue, int maxValue) {
        setValue((float) newValue / (float) maxValue);
        toFront(); // if several fighters are stacked, make sure to display the health bar of the one taking damage
        if (getValue() == 0f) {
            remove();
        }
    }

    @Override
    public void positionChanged(Vector2 oldValue, Vector2 newValue) {
        setVisible(true);
        setPosition(newValue.x, newValue.y + 120);
    }
}
