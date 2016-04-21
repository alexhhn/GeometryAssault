package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.managers.SoundManager;

/**
 * The victory screen
 */
public class VictoryScreen implements Screen {

    private GeometryAssault game;
    private Stage st;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Image backToMenuButton;


    public VictoryScreen(GeometryAssault game, boolean winner) {
        this.game = game;
        SoundManager.getInstance().stopMusic();
        // configures the camera and stage
        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        st = new Stage();
        st.setViewport(gamePort);
        // creates textures
        st.addActor(createStaticTexture("bg.png", 0, 0));
        st.addActor(createStaticTexture("bottomBanner.png", 0, -50));
        // show happy fighters on the winning side
        if (winner){
            st.addActor(createStaticTexture("play2win.png", 175, 500));
            st.addActor(createStaticTexture("happy-triangle.png", 850, 226));
            st.addActor(createStaticTexture("happy-circle.png", 950, 225));
            st.addActor(createStaticTexture("happy-square.png", 750, 227));
            st.addActor(createStaticTexture("devil-face-right-dead.png", 25, 183));
            st.addActor(createStaticTexture("happy-devil-tail-right.png", GeometryAssault.WIDTH-225, 202));
        }else{
            st.addActor(createStaticTexture("play1win.png", 175, 500));
            st.addActor(createStaticTexture("happy-triangle.png", 300, 226));
            st.addActor(createStaticTexture("happy-circle.png", 200, 225));
            st.addActor(createStaticTexture("happy-square.png", 400, 227));
            st.addActor(createStaticTexture("devil-face-left-dead.png", GeometryAssault.WIDTH-275, 183));
            st.addActor(createStaticTexture("happy-devil-tail-left.png", 15, 202));
        }
        // the button for going back to the main menu
        backToMenuButton = new Image(TextureManager.getInstance().getTexture("return-menu.png"));
        backToMenuButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backToMenu();
                return true;
            }
        });
        backToMenuButton.setPosition( GeometryAssault.WIDTH/2-210, 400);
        st.addActor(backToMenuButton);
    }

    private Image createStaticTexture(String texturePath, int x, int y) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.setPosition(x, y);
        return img;
    }

    /**
     * Called when the backToMenuButton is pressed, goes back to the main menu
     */
    private void backToMenu() {
        game.setScreen(new StartScreen(game));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(st);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
