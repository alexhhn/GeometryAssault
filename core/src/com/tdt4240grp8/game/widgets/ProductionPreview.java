package com.tdt4240grp8.game.widgets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.observable.PlayerListener;
import com.tdt4240grp8.game.screens.PlayScreen;
import com.tdt4240grp8.game.sprites.Fighter;
import com.tdt4240grp8.game.sprites.Player;
import com.tdt4240grp8.game.sprites.Square;

public class ProductionPreview extends ProgressBar implements PlayerListener {

    private Image img;
    private Stage st;
    private Player player;
    private static final String noneInProduction = "noneInProduction.png";

    public ProductionPreview(int xPos, int yPos, int xOffset, float min, float max, float stepSize, boolean vertical, Stage st, Player player) {
        super(min, max, stepSize, vertical, new ProgressBarStyle());
        this.st = st;
        this.player = player;
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("ProgressBar.png")));
        style.knobAfter = new TextureRegionDrawable(new TextureRegion(TextureManager.getInstance().getTexture("knob.png")));
        setStyle(style);
        setValue(1);
        setSize(150, 30);
        setPosition(xPos, yPos);
        img = new Image(TextureManager.getInstance().getTexture(noneInProduction));
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
        if (newValue == null) {
            img.setVisible(false);
        } else{
            img.setVisible(true);
            img.setDrawable(new TextureRegionDrawable(newValue.getProductionImage()));
        }

    }

    @Override
    public void currentProductionTimeChanged(float oldValue, float newValue, float totalValue) {
        setValue(newValue / totalValue);
        System.out.println("totalValue" + newValue);
        if (newValue >= totalValue){
            if (player.isGoingLeft()){
                st.getActors().get(6).setVisible(false);
            }else {
                st.getActors().get(8).setVisible(false);
            }
        }
    }

    public Image getImg() {
        return img;
    }
}
