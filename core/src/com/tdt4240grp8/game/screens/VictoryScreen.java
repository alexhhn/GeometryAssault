package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.managers.SoundManager;

public class VictoryScreen implements Screen {

    private GeometryAssault game;
    private Stage st;
    private OrthographicCamera gamecam;
    private Viewport gamePort;


    public VictoryScreen(GeometryAssault game, boolean winner) {
        this.game = game;
        SoundManager.getInstance().stopMusic();

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        st = new Stage();
        st.setViewport(gamePort);
        st.addActor(createStaticTexture("bg.png", 0, 0, st));
        //st.addActor(createStaticTexture("press2return.png",GeometryAssault.WIDTH/2-175,GeometryAssault.HEIGHT / 2,st ));
        st.addActor(createStaticTexture("bottomBanner.png", 0, -50, st));
        st.addActor(createStaticTexture("return-menu.png", GeometryAssault.WIDTH/2-210, 400,st));

        if (winner){
            st.addActor(createStaticTexture("play2win.png", 175, 500, st));
            st.addActor(createStaticTexture("happy-triangle.png", 850, 226, st));
            st.addActor(createStaticTexture("happy-circle.png", 950, 225, st));
            st.addActor(createStaticTexture("happy-square.png", 750, 227, st));
            st.addActor(createStaticTexture("devil-face-right-dead.png", 25, 183, st));//done
            st.addActor(createStaticTexture("happy-devil-tail-right.png", GeometryAssault.WIDTH-225, 202, st));
        }else{
            st.addActor(createStaticTexture("play1win.png", 175, 500, st));
            st.addActor(createStaticTexture("happy-triangle.png", 300, 226, st));
            st.addActor(createStaticTexture("happy-circle.png", 200, 225, st));
            st.addActor(createStaticTexture("happy-square.png", 400, 227, st));
            st.addActor(createStaticTexture("devil-face-left-dead.png", GeometryAssault.WIDTH-275, 183, st));
            st.addActor(createStaticTexture("happy-devil-tail-left.png", 15, 202, st));
        }
    }

    private Image createStaticTexture(String texturePath, int x, int y, Stage st) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.setPosition(x, y);
        return img;
    }

    private void startGame() {
        game.setScreen(new PlayScreen(game));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(st);
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            game.setScreen(new StartScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        handleInput();
        st.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
