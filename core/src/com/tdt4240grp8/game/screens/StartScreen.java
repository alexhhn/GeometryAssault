package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.managers.SoundManager;
import com.tdt4240grp8.game.states.GameModeState;
import com.tdt4240grp8.game.states.HypersonicState;
import com.tdt4240grp8.game.states.NormalState;
import com.tdt4240grp8.game.states.WealthyState;

/**
 * The start menu screen
 */
public class StartScreen implements Screen {

    private GeometryAssault game;
    private Stage st;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Image muteButton;

    public StartScreen(GeometryAssault game) {
        this.game = game;

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        st = new Stage();
        st.setViewport(gamePort);

        st.addActor(createStaticTexture("bg.png", 0, 0));
        st.addActor(createStaticTexture("GA-title.png", 400, 500));
        st.addActor(createStaticTexture("modeselect.png", 500, 400));

        addButton("normalModeButton.png", 200, 250, new NormalState());
        addButton("wealthyModeButton.png", 500, 250, new WealthyState());
        addButton("hypersonicModeButton.png", 800, 250, new HypersonicState());

        muteButton = new Image(TextureManager.getInstance().getTexture("muteSoundwaves.png"));
        muteButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleSound();
                return true;
            }
        });
        muteButton.setPosition(1150, 600);
        st.addActor(muteButton);
    }

    private void toggleSound() {
        if (SoundManager.soundEnabled) {
            muteButton.setDrawable(new SpriteDrawable(new Sprite(TextureManager.getInstance().getTexture("muteX.png"))));
            SoundManager.soundEnabled = false;
        } else {
            muteButton.setDrawable(new SpriteDrawable(new Sprite(TextureManager.getInstance().getTexture("muteSoundwaves.png"))));
            SoundManager.soundEnabled = true;
        }
    }

    private void addButton(String texturePath, int x, int y, final GameModeState gameModeState) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                setGameMode(gameModeState);
                startGame();
                return true;
            }
        });
        img.setPosition(x, y);
        st.addActor(img);
    }

    private Image createStaticTexture(String texturePath, int x, int y) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.setPosition(x, y);
        return img;
    }

    private void setGameMode(GameModeState gameModeState) {
        game.setGameModeState(gameModeState);
    }

    private void startGame() {
        game.setScreen(new PlayScreen(game));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(st);
    }

    @Override
    public void render(float delta) {
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
