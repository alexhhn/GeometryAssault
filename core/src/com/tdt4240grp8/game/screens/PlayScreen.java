package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.sounds.SoundManager;
import com.tdt4240grp8.game.sprites.Fighter;
import com.tdt4240grp8.game.sprites.Player;
import com.tdt4240grp8.game.sprites.Square;
import com.tdt4240grp8.game.widgets.GoldWidget;
import com.tdt4240grp8.game.widgets.HealthBar;
import com.tdt4240grp8.game.widgets.HealthWidget;
import com.tdt4240grp8.game.widgets.ProductionPreview;

import java.util.ArrayList;

public class PlayScreen implements Screen{

    private GeometryAssault game;

    private Player player1, player2;
    private Stage st;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private GoldWidget goldWidget1, goldWidget2;
    private HealthWidget healthWidget1, healthWidget2;
    private ProductionPreview productionPreview1, productionPreview2;

    private ShapeRenderer shapeRenderer;
    private Image resumeBtn;
    private Image pauseBtn;
    private Image quitBtn;

    // All of these are just for placing images, texture
    public static final int buttonXpos = 5, buttonYPos = 2 , buttonWidth = 163, buttonHeight = 250;
    public static final int coreYPos = buttonHeight + 5, fighterYPos = coreYPos + 18;
    public static final int hudXPos = 5, hudYPos = GeometryAssault.HEIGHT - 80, heartIconHeight = 65,
            goldXPos = hudXPos + 120, hudTextYPos = hudYPos - 4;

    private Sound music;
    private Sound punch;
    private Sound death;

    public State state = State.RUN;

    public enum State
    {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }


    public void createMenuButtons(){
        resumeBtn = new Image(TextureManager.getInstance().getTexture("play.jpg"));
        pauseBtn = new Image(TextureManager.getInstance().getTexture("pause.jpg"));
        quitBtn = new Image(TextureManager.getInstance().getTexture("quit.png"));

        resumeBtn.setVisible(false);
        quitBtn.setVisible(false);

        resumeBtn.setSize(50,50);
        pauseBtn.setSize(50,50);
        quitBtn.setSize(50,50);

        pauseBtn.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() -100);
        resumeBtn.setPosition(Gdx.graphics.getWidth()/2 - 50,Gdx.graphics.getHeight() -100);
        quitBtn.setPosition(Gdx.graphics.getWidth()/2 + 50,Gdx.graphics.getHeight()-100);

        resumeBtn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (state == State.PAUSE){
                    setGameState(State.RUN);
                    resumeBtn.setVisible(false);
                    quitBtn.setVisible(false);
                    pauseBtn.setVisible(true);
                    return true;
                }
                return false;

            }

        });

        pauseBtn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(state == State.RUN){
                    setGameState(State.PAUSE);
                    pauseBtn.setVisible(false);
                    resumeBtn.setVisible(true);
                    quitBtn.setVisible(true);
                    return true;
                }
                return false;
            }
        });

        quitBtn.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(state == State.PAUSE){
                    game.setScreen(new VictoryScreen(game));
                    return true;
                }
                return false;
            }
        });
    }


    public PlayScreen(GeometryAssault game) {
        this.game = game;
        player1 = new Player(true);
        player2 = new Player(false);

        //Get rid of old music
        SoundManager.sharedInstance.muteMusic();

        shapeRenderer = new ShapeRenderer();



        if(GeometryAssault.soundEnabled) {
            music = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
            punch = Gdx.audio.newSound(Gdx.files.internal("punch.mp3"));
            death = Gdx.audio.newSound(Gdx.files.internal("pacman-death.mp3"));
            long id = music.play();
            SoundManager.sharedInstance.put(id, music);
        }

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        st = new Stage();
        st.setViewport(gamePort);
        Gdx.input.setInputProcessor(st);

        productionPreview1 = new ProductionPreview(250, GeometryAssault.HEIGHT - 75, 150, 0, 1, 0.01f, false, st);
        productionPreview2 = new ProductionPreview(GeometryAssault.WIDTH - 350, GeometryAssault.HEIGHT - 75, -80, 0, 1, 0.01f, false, st);

        goldWidget1 = new GoldWidget(goldXPos, hudYPos - 5, -30, 1);
        goldWidget2 = new GoldWidget(GeometryAssault.WIDTH - goldXPos - heartIconHeight - heartIconHeight/2, hudYPos - 5, -30, 1);
        healthWidget1 = new HealthWidget(hudXPos,hudYPos, 6, 1);
        healthWidget2 = new HealthWidget(GeometryAssault.WIDTH - 95, hudYPos, 3, 1);
        
        st.addActor(createStaticTexture("bg.png",0,0,st));
        st.addActor(createStaticTexture("bottomBanner.png",0,0,st));

        st.addActor(goldWidget1.getImg());
        st.addActor(goldWidget2.getImg());
        st.addActor(healthWidget1.getImg());
        st.addActor(healthWidget2.getImg());
        st.addActor(productionPreview1);
        st.addActor(productionPreview1.getImg());
        st.addActor(productionPreview2);
        st.addActor(productionPreview2.getImg());



        player1.addPlayerListener(goldWidget1);
        player2.addPlayerListener(goldWidget2);
        player1.addPlayerListener(healthWidget1);
        player2.addPlayerListener(healthWidget2);
        player1.addPlayerListener(productionPreview1);
        player2.addPlayerListener(productionPreview2);

        createMenuButtons();

        st.addActor(resumeBtn);
        st.addActor(pauseBtn);
        st.addActor(quitBtn);

        createButton(player1, "square-button.png", buttonXpos, buttonYPos, Player.Fighters.SQUARE);
        createButton(player1, "triangle-button.png", buttonXpos + buttonWidth + 5, buttonYPos, Player.Fighters.TRIANGLE);
        createButton(player1, "circle-button.png", buttonXpos + buttonWidth * 2 + 10, buttonYPos, Player.Fighters.CIRCLE);

        createButton(player2, "circle-button-face-left.png", GeometryAssault.WIDTH - buttonWidth * 3 - 15, buttonYPos, Player.Fighters.CIRCLE);
        createButton(player2, "triangle-button-face-left.png", GeometryAssault.WIDTH - buttonWidth * 2 - 10, buttonYPos, Player.Fighters.TRIANGLE);
        createButton(player2, "square-button-face-left.png", GeometryAssault.WIDTH - buttonWidth - 5, buttonYPos, Player.Fighters.SQUARE);
    }


    private Image createButton(final Player player, String texturePath, int x, int y, final Player.Fighters fighter) {
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Fighter createdFighter = player.addFighter(fighter);

                if (state == State.RUN) {
                    if (createdFighter != null) {
                        HealthBar healthBar = new HealthBar(0f, 1f, 0.01f, false);
                        createdFighter.addFighterListener(healthBar);
                        player.addGold(-100);
                        st.addActor(healthBar);
                        return true;
                    }

                }
                return false;
            }
        });
        img.setPosition(x, y);
        st.addActor(img);
        return img;
    }

    private Image createStaticTexture(String texturePath, int x, int y, Stage st){
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
//        img.setSize(GeometryAssault.WIDTH,GeometryAssault.HEIGHT);
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
                if(GeometryAssault.soundEnabled){
                    long id = punch.play();

                }

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
                if(GeometryAssault.soundEnabled){
                    long id = punch.play();
                }
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
        if (player1.isDead() || player2.isDead()) {
            game.setScreen(new VictoryScreen(game));
        }
    }

    private boolean collides(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }


    public void updatePause(float delta){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(gamecam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    @Override
    public void render(float delta) {
        //pause
        //mute music
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            GeometryAssault.soundEnabled = false;
            SoundManager.sharedInstance.muteMusic();
        }

        //enable music
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            //Get rid of old music before playing new music.
            SoundManager.sharedInstance.muteMusic();

            GeometryAssault.soundEnabled = true;
            long id = music.play();
            SoundManager.sharedInstance.put(id,music);
        }

        switch (state){
            case RUN:
                update(delta);
                break;
            case PAUSE:

                break;
            case RESUME:
                break;
            default:
                break;

        }

//
//        Gdx.gl.glClearColor(0, 0, 0, 0);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
            game.batch.draw(fighter.getTexture(),fighter.getPosition().x,fighter.getPosition().y);
        }
        for (Fighter fighter : player2.getFighters()) {
            game.batch.draw(fighter.getTexture(),fighter.getPosition().x,fighter.getPosition().y);
        }



        game.batch.end();


        //Pause black screen with opacity 0.5
        switch (state){
            case RUN:
                break;
            case PAUSE:
                updatePause(delta);
                break;
            case RESUME:
                break;
            default:
                break;

        }


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        music.dispose();
        punch.dispose();
        death.dispose();
    }

    public void setGameState(State s){
        this.state = s;
    }
}
