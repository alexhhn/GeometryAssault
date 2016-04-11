package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.sprites.Fighter;
import com.tdt4240grp8.game.sprites.Player;
import com.tdt4240grp8.game.widgets.GoldWidget;
import com.tdt4240grp8.game.widgets.HealthWidget;

import java.util.ArrayList;

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

        player1 = new Player(true);
        player2 = new Player(false);

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);


        goldWidget1 = new GoldWidget(player1);
        goldWidget2 = new GoldWidget(player2);
        healthWidget1 = new HealthWidget();
        healthWidget2 = new HealthWidget();
        st = new Stage();

        st.addActor(healthWidget1);
        //st.addActor(healthWidget2);

        player1.addPlayerListener(healthWidget1);
        //player2.addPlayerListener(healthWidget2);

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

        // Create a progressbar
        Texture progress_bar = new Texture(Gdx.files.internal("ProgressBar.png"));
        Texture progress_bar_knob = new Texture(Gdx.files.internal("knob.png"));
        progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(progress_bar));
        progressBarStyle.knob = new TextureRegionDrawable(new TextureRegion(progress_bar_knob));
        progressBar1 = new ProgressBar(0f,100f,1f,false, progressBarStyle);
        progressBar1.setPosition(135,GeometryAssault.HEIGHT - 52);
        st.addActor(progressBar1);


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
        player1.update(delta);
        player2.update(delta);
        ArrayList<Fighter> waitingToMove = new ArrayList<Fighter>();
        for (Fighter fighter : player1.getFighters()) {
            fighter.update(delta);
            boolean collided = false;
            if (collides(fighter.getBounds(), player2.getCore().getBounds())) {
                collided = true;
                if (fighter.attackOffCooldown()) {
                    fighter.attack(player2);
                    fighter.resetAttackCooldown();
                }
            }
            for (Fighter fighter2 : player2.getFighters()) {
                if (collides(fighter.getBounds(), fighter2.getBounds())) {
                    collided = true;
                    if (fighter.attackOffCooldown()) {
                        fighter.attack(fighter2);
                        fighter.resetAttackCooldown();
                    }
                    break;
                }
            }
            if (!collided) {
                waitingToMove.add(fighter);
            }
        }
        for (Fighter fighter : player2.getFighters()) {
            fighter.update(delta);
            boolean collided = false;
            if (collides(fighter.getBounds(), player1.getCore().getBounds())) {
                collided = true;
                if (fighter.attackOffCooldown()) {
                    fighter.attack(player1);
                    fighter.resetAttackCooldown();
                }

            }
            for (Fighter fighter1 : player1.getFighters()) {
                if (collides(fighter.getBounds(), fighter1.getBounds())) {
                    collided = true;
                    if (fighter.attackOffCooldown()) {
                        fighter.attack(fighter1);
                        fighter.resetAttackCooldown();
                    }
                    break;
                }
            }
            if (!collided) {
                waitingToMove.add(fighter);
            }
        }
        ArrayList<Fighter> markedForDeath = new ArrayList<Fighter>();
        for (Fighter fighter : player1.getFighters()) {
            if (fighter.isDead()) {
                markedForDeath.add(fighter);
            }
        }
        for (Fighter fighter : markedForDeath) {
            player2.addGold(fighter.getGoldValue());
            player1.removeFighter(fighter);
        }

        markedForDeath = new ArrayList<Fighter>();
        for (Fighter fighter : player2.getFighters()) {
            if (fighter.isDead()) {
                markedForDeath.add(fighter);
            }
        }
        for (Fighter fighter : markedForDeath) {
            player1.addGold(fighter.getGoldValue());
            player2.removeFighter(fighter);
        }
        for (Fighter fighter : waitingToMove) {
            fighter.move(delta);
        }
        if (player1.isDead() || player2.isDead()) {
            game.setScreen(new VictoryScreen(game));
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
        healthWidget1.render(delta, game.batch);
        for (Fighter fighter : player1.getFighters()) {
            game.batch.draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
        }
        for (Fighter fighter : player2.getFighters()) {
            game.batch.draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
        }

        // Create goldwidget
//        goldFont.setColor(Color.WHITE);
//        goldFont.draw(game.batch, "$ " + goldWidget1.getGold(), 20, GeometryAssault.HEIGHT - 30);
//        goldFont.draw(game.batch, "$ " + goldWidget2.getGold(), GeometryAssault.WIDTH -140, GeometryAssault.HEIGHT - 30);
//        healthFont.draw(game.batch,  "" + healthWidget1.getHealth(), 70 , GeometryAssault.HEIGHT - 70);
//        healthFont.draw(game.batch, "" + healthWidget2.getHealth(), GeometryAssault.WIDTH - 100, GeometryAssault.HEIGHT - 70);

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
