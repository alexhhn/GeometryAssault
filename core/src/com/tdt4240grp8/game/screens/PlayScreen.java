package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.tdt4240grp8.game.sprites.Square;
import com.tdt4240grp8.game.widgets.GoldWidget;
import com.tdt4240grp8.game.widgets.HealthBar;
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

    private BitmapFont goldFont;

    private ProgressBar progressBar1;
    private ProgressBar.ProgressBarStyle progressBarStyle;

    private ArrayList<HealthBar> healthBars = new ArrayList<HealthBar>();

    // All of these are just for placing images, texture
    public static final int buttonXpos = 5, buttonYPos = 10, buttonWidth = 195, buttonHeight = 250;
    public static final int coreYPos = buttonHeight + 50;

    public PlayScreen(GeometryAssault game) {

        this.game = game;

        player1 = new Player(true);
        player2 = new Player(false);

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        goldWidget1 = new GoldWidget(20, GeometryAssault.HEIGHT - 100, 90, .6f);
        goldWidget2 = new GoldWidget(GeometryAssault.WIDTH - 100, GeometryAssault.HEIGHT - 100, -90, .6f);
        healthWidget1 = new HealthWidget(23,GeometryAssault.HEIGHT - 175, 80, .7f);
        healthWidget2 = new HealthWidget(GeometryAssault.WIDTH - 95, GeometryAssault.HEIGHT - 175, -40, .7f);
        

        st = new Stage();
        st.addActor(goldWidget1.getImg());
        st.addActor(goldWidget2.getImg());
        st.addActor(healthWidget1.getImg());
        st.addActor(healthWidget2.getImg());

        player1.addPlayerListener(goldWidget1);
        player2.addPlayerListener(goldWidget2);
        player1.addPlayerListener(healthWidget1);
        player2.addPlayerListener(healthWidget2);

        st.setViewport(gamePort);
        Gdx.input.setInputProcessor(st);

        createButton(player1, "circle-button.png", buttonXpos, buttonYPos, Player.Fighters.SQUARE);
        createButton(player1, "circle-button.png", buttonXpos + buttonWidth, buttonYPos, Player.Fighters.TRIANGLE);
        createButton(player1, "circle-button.png", buttonXpos + buttonWidth * 2, buttonYPos, Player.Fighters.CIRCLE);

        createButton(player2, "circle-button.png", GeometryAssault.WIDTH - buttonWidth * 3, buttonYPos, Player.Fighters.CIRCLE);
        createButton(player2, "circle-button.png", GeometryAssault.WIDTH - buttonWidth * 2, buttonYPos, Player.Fighters.TRIANGLE);
        createButton(player2, "circle-button.png", GeometryAssault.WIDTH - buttonWidth, buttonYPos, Player.Fighters.SQUARE);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Bold.ttf"));
        goldFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        goldFontParameter.size = 35;
        goldFont = generator.generateFont(goldFontParameter); // goldFont size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        // Create a progressbar
//        Texture progress_bar = new Texture(Gdx.files.internal("ProgressBar.png"));
//        Texture progress_bar_knob = new Texture(Gdx.files.internal("knob.png"));
//        progressBarStyle = new ProgressBar.ProgressBarStyle();
//        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(progress_bar));
//        progressBarStyle.knob = new TextureRegionDrawable(new TextureRegion(progress_bar_knob));
//        progressBar1 = new ProgressBar(0f,100f,1f,false, progressBarStyle);
//        progressBar1.setPosition(135,GeometryAssault.HEIGHT - 52);
//        st.addActor(progressBar1);

        // Create bg

    }

    private Image createButton(final Player player, String texturePath, int x, int y, final Player.Fighters fighter) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Fighter createdFighter = player.addFighter(fighter);
                if (createdFighter != null) {
                    HealthBar healthBar = new HealthBar(0f, 1f, 0.1f, false, createdFighter);
                    healthBars.add(healthBar);
                    player.addGold(-100);
                    st.addActor(healthBar);
                    return true;
                }
                return false;
            }
        });
        img.setPosition(x, y);
        st.addActor(img);
        return img;
    }

    private Image createBg(String texturePath, int x, int y, Stage st){
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.setSize(GeometryAssault.WIDTH,GeometryAssault.HEIGHT);
        img.setPosition(x, y);
        return img;
    }

    @Override
    public void show() {

    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
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
            player2.addGold(fighter.getGoldValue()*game.gameModeState.getGoldMultiplier());
            player1.removeFighter(fighter);
        }

        markedForDeath = new ArrayList<Fighter>();
        for (Fighter fighter : player2.getFighters()) {
            if (fighter.isDead()) {
                markedForDeath.add(fighter);
            }
        }
        for (Fighter fighter : markedForDeath) {
            player1.addGold(fighter.getGoldValue()*game.gameModeState.getGoldMultiplier());
            player2.removeFighter(fighter);
        }
        for (Fighter fighter : waitingToMove) {
            fighter.move(delta*game.gameModeState.getSpeedMultiplier());
        }
        for (int i = healthBars.size() - 1; i >= 0; i--) {
            HealthBar healthBar = healthBars.get(i);
            healthBar.setPosition(healthBar.getFighter().getPosition().x, healthBar.getFighter().getPosition().y + 100);
            healthBar.setValue(healthBar.getFighter().getHeath() / 10.0f);
            if (healthBar.getValue() == 0f) {
                healthBars.remove(healthBar);
                healthBar.remove();
            }
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

        goldWidget1.render(delta,game.batch);
        goldWidget2.render(delta,game.batch);
        healthWidget1.render(delta, game.batch);
        healthWidget2.render(delta, game.batch);

        for (Fighter fighter : player1.getFighters()) {
            if (fighter instanceof Square){
                ((Square) fighter).getAnimation().update(delta);
                game.batch.draw(((Square) fighter).getAnimation().getCurrentFrame(),fighter.getPosition().x,fighter.getPosition().y);

            }
            else{
                game.batch.draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
            }

        }
        for (Fighter fighter : player2.getFighters()) {
            if (fighter instanceof Square){
                ((Square) fighter).getAnimation().update(delta);
                game.batch.draw(((Square) fighter).getAnimation().getCurrentFrame(),fighter.getPosition().x,fighter.getPosition().y);

            }
            else{
                game.batch.draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
            }
        }

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
