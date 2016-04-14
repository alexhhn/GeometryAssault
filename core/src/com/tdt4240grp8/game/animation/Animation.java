package com.tdt4240grp8.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tdt4240grp8.game.managers.TextureManager;

/**
 * Created by mikaila on 11/04/16.
 */
public class Animation{

    private static int        FRAME_COLS;         // #1
    private static int        FRAME_ROWS;      // #2

    com.badlogic.gdx.graphics.g2d.Animation walkAnimation;          // #3
    Texture walkSheet;              // #4
    TextureRegion[]                 walkFrames;             // #5
    TextureRegion                   currentFrame;           // #7
    float stateTime;                                        // #8

    public Animation(Texture texture, int cols, int rows) {
        this.FRAME_COLS = cols;
        this.FRAME_ROWS = rows;
        walkSheet = texture;
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new com.badlogic.gdx.graphics.g2d.Animation(0.025f, walkFrames);      // #11
//        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13
    }

    public void animate() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
//        spriteBatch.begin();
//        spriteBatch.draw(currentFrame, 50, 50);             // #17
//        spriteBatch.end();
    }

    public TextureRegion getCurrentFrame(){
        return currentFrame;
    }

    public void update(float dt){
        stateTime += dt;
        currentFrame = walkAnimation.getKeyFrame(stateTime,true);
    }
    
    public int getWidth(){
        return walkSheet.getWidth()/FRAME_COLS;
    }

    public int getHeight(){
        return walkSheet.getHeight()/FRAME_ROWS;
    }

}