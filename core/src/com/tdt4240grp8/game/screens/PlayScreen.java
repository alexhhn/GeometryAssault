package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.sprites.Fighter;
import com.tdt4240grp8.game.sprites.Player;
import com.tdt4240grp8.game.widgets.GoldWidget;
import com.tdt4240grp8.game.widgets.HealthWidget;

public class PlayScreen implements Screen {

    private GeometryAssault game;

    private Player player1, player2;
    private Stage st;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private GoldWidget goldWidget1, goldWidget2;
    private HealthWidget healthWidget1, healthWidget2;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter goldFontParameter;
    private FreeTypeFontGenerator.FreeTypeFontParameter healthFontParameter;

    private BitmapFont goldFont;
    private BitmapFont healthFont;

    private ProgressBar progressBar1;
    private ProgressBar.ProgressBarStyle progressBarStyle;

    public PlayScreen(GeometryAssault game) {

        this.game = game;

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        player1 = new Player(true, GeometryAssault.PLAYER_START_GOLD, GeometryAssault.PLAYER_START_HEALTHPOINT);
        player2 = new Player(false, GeometryAssault.PLAYER_START_GOLD, GeometryAssault.PLAYER_START_HEALTHPOINT);

        goldWidget1 = new GoldWidget(player1);
        goldWidget2 = new GoldWidget(player2);
        healthWidget1 = new HealthWidget(player1);
        healthWidget2 = new HealthWidget(player2);

        progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBar1 = new ProgressBar(10,100,10,false, progressBarStyle);

        player1.notifyAllObservers();
        player2.notifyAllObservers();


        st = new Stage();
        st.setViewport(gamePort);
        Gdx.input.setInputProcessor(st);
        createButton(player1, "playbtn.png", 50, 50, st, Player.Fighters.SQUARE);
        createButton(player1, "playbtn.png", 150, 50, st, Player.Fighters.TRIANGLE);
        createButton(player1, "playbtn.png", 250, 50, st, Player.Fighters.CIRCLE);

        createButton(player2, "playbtn.png", 450, 50, st, Player.Fighters.CIRCLE);
        createButton(player2, "playbtn.png", 550, 50, st, Player.Fighters.TRIANGLE);
        createButton(player2, "playbtn.png", 650, 50, st, Player.Fighters.SQUARE);

        createIcon("heart-icon.png", 20 , GeometryAssault.HEIGHT - 100, st);
        createIcon("heart-icon.png",GeometryAssault.WIDTH - 65, GeometryAssault.HEIGHT - 100, st);


        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        goldFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        goldFontParameter.size = 35;
        goldFont = generator.generateFont(goldFontParameter); // goldFont size 12 pixels

        healthFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        healthFontParameter.size = 25;
        healthFont = generator.generateFont(healthFontParameter);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!

    }

    private Image createButton(final Player player, String texturePath, int x, int y, Stage st, final Player.Fighters fighter) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.addFighter(fighter);
                return true;
            }
        });
        img.setPosition(x, y);
        st.addActor(img);
        return img;
    }

    private Image createIcon(String texturePath, int x, int y, Stage st){
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.setSize(40,34);
        img.setPosition(x, y);
        st.addActor(img);
        return img;
    }

    @Override
    public void show() {

    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
        }
    }

    public void update(float delta) {
        handleInput();
        for (Fighter fighter : player1.getFighters()) {
            fighter.update(delta);
            if (collides(fighter.getBounds(), player2.getCore().getBounds())) {
                // losing hp
                player2.decreaseHealth(10);
                player1.removeFighter(fighter);
//                game.setScreen(new VictoryScreen(game));
            }
            for (Fighter fighter2 : player2.getFighters()) {
                if (collides(fighter.getBounds(), fighter2.getBounds())) {
                    player2.removeFighter(fighter2);
                    player1.removeFighter(fighter);
                }
            }
        }
        for (Fighter fighter : player2.getFighters()) {
            fighter.update(delta);
            if (collides(fighter.getBounds(), player1.getCore().getBounds())) {
                player1.decreaseHealth(10);
                player2.removeFighter(fighter);
//                game.setScreen(new VictoryScreen(game));
            }
        }

    }

    private boolean collides(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        st.draw();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(player1.getCore().getTexture(), player1.getCore().getPosition().x, player1.getCore().getPosition().y);
        game.batch.draw(player2.getCore().getTexture(), player2.getCore().getPosition().x, player2.getCore().getPosition().y);
        for (Fighter fighter : player1.getFighters()) {
            game.batch.draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
        }
        for (Fighter fighter : player2.getFighters()) {
            game.batch.draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
        }

        // Create goldwidget
        goldFont.setColor(Color.WHITE);
        goldFont.draw(game.batch, "$ " + goldWidget1.getGold(), 20, GeometryAssault.HEIGHT - 30);
        goldFont.draw(game.batch, "$ " + goldWidget2.getGold(), GeometryAssault.WIDTH -140, GeometryAssault.HEIGHT - 30);
        healthFont.draw(game.batch,  "" + healthWidget1.getHealth(), 70 , GeometryAssault.HEIGHT - 70);
        healthFont.draw(game.batch, "" + healthWidget2.getHealth(), GeometryAssault.WIDTH - 100, GeometryAssault.HEIGHT - 70);
//        progressBar1.draw(game.batch,1);
//        st.addActor(progressBar1);

        game.batch.end();
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
