package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.managers.SoundManager;
import com.tdt4240grp8.game.model.Fighter;
import com.tdt4240grp8.game.model.Player;
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
    public static final int buttonXpos = 5, buttonYPos = 2 , buttonWidth = 163, buttonHeight = 250,
    coreYPos = buttonHeight + 5, fighterYPos = coreYPos + 18,hudXPos = 5, hudYPos = GeometryAssault.HEIGHT - 80, heartIconHeight = 65,
            goldXPos = hudXPos + 120, hudTextYPos = hudYPos - 4, previewImageSize = 50,
            progressBarYPos = hudYPos + 13, previewImageYPos = progressBarYPos - 8;

    private String deathSound = "punch.mp3";

    public Simulation simulation = Simulation.RUN;

    public enum Simulation
    {
        PAUSE,
        RUN,
    }

    public PlayScreen(GeometryAssault game)  {
        this.game = game;
        player1 = new Player(true);
        player2 = new Player(false);

        shapeRenderer = new ShapeRenderer();

        gamecam = new OrthographicCamera(GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        gamecam.position.set(GeometryAssault.WIDTH / 2f, GeometryAssault.HEIGHT / 2f, 0);
        gamePort = new FitViewport(GeometryAssault.WIDTH, GeometryAssault.HEIGHT, gamecam);

        st = new Stage();
        st.setViewport(gamePort);

        goldWidget1 = new GoldWidget(goldXPos, hudYPos - 5);
        goldWidget2 = new GoldWidget(GeometryAssault.WIDTH - goldXPos - heartIconHeight - heartIconHeight/2, hudYPos - 5);
        healthWidget1 = new HealthWidget(hudXPos,hudYPos);
        healthWidget2 = new HealthWidget(GeometryAssault.WIDTH - 95, hudYPos);
        productionPreview1 = new ProductionPreview(goldXPos + 130 + previewImageSize , progressBarYPos, -previewImageSize - 1);
        productionPreview2 = new ProductionPreview(GeometryAssault.WIDTH - 480, progressBarYPos, 151);

        st.addActor(createStaticTexture("bg.png", 0, 0));
        st.addActor(createStaticTexture("bottomBanner.png", 0, 0));

        st.addActor(goldWidget1.getImg());
        st.addActor(goldWidget2.getImg());
        st.addActor(healthWidget1.getImg());
        st.addActor(healthWidget2.getImg());
        st.addActor(productionPreview1);
        st.addActor(productionPreview1.getImg());
        st.addActor(productionPreview2);
        st.addActor(productionPreview2.getImg());

        st.getActors().get(6).setVisible(false);
        st.getActors().get(8).setVisible(false);

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
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Fighter createdFighter = player.addFighter(fighter);

                if (simulation == Simulation.RUN) {
                    if (createdFighter != null) {
                        HealthBar healthBar = new HealthBar();
                        createdFighter.addFighterListener(healthBar);
                        player.addGold(-createdFighter.getProductionCost());
                        st.addActor(healthBar);

                        if (player == player1){
                            st.getActors().get(6).setVisible(true);
                        } else{
                            st.getActors().get(8).setVisible(true);
                        }
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

    private Image createStaticTexture(String texturePath, int x, int y){
        Image img = new Image(TextureManager.getInstance().getTexture(texturePath));
        img.setPosition(x, y);
        return img;
    }


    public void createMenuButtons(){
        resumeBtn = new Image(TextureManager.getInstance().getTexture("resume.png"));
        pauseBtn = new Image(TextureManager.getInstance().getTexture("pause.png"));
        quitBtn = new Image(TextureManager.getInstance().getTexture("quit.png"));

        resumeBtn.setVisible(false);
        quitBtn.setVisible(false);

        resumeBtn.setSize(200,80);
        pauseBtn.setSize(200,80);
        quitBtn.setSize(200,80);

        pauseBtn.setPosition((GeometryAssault.WIDTH/2)-100,GeometryAssault.HEIGHT -100);
        resumeBtn.setPosition((GeometryAssault.WIDTH/2) - 100,GeometryAssault.HEIGHT -100);
        quitBtn.setPosition((GeometryAssault.WIDTH/2)-100 ,GeometryAssault.HEIGHT-200);

        resumeBtn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (simulation == Simulation.PAUSE){
                    setGameState(Simulation.RUN);
                    resumeBtn.setVisible(false);
                    pauseBtn.setVisible(true);
                    quitBtn.setVisible(false);
                    return true;
                }
                return false;

            }

        });

        pauseBtn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(simulation == Simulation.RUN){
                    setGameState(Simulation.PAUSE);
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
                if(simulation == Simulation.PAUSE){
                    game.setScreen(new VictoryScreen(game));
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(st);
        SoundManager.getInstance().startMusic();
    }

    public void update(float delta) {
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
                SoundManager.getInstance().playSound(deathSound);
                markedForDeath.add(fighter);
            }
        }
        for (Fighter fighter : markedForDeath) {
            player2.addGold(fighter.getGoldValue()*game.getGameModeState().getGoldMultiplier());
            player1.removeFighter(fighter);
        }

        markedForDeath = new ArrayList<Fighter>();
        for (Fighter fighter : player2.getFighters()) {
            if (fighter.isDead()) {
                SoundManager.getInstance().playSound(deathSound);
                markedForDeath.add(fighter);
            }
        }
        for (Fighter fighter : markedForDeath) {
            player1.addGold(fighter.getGoldValue()*game.getGameModeState().getGoldMultiplier());
            player2.removeFighter(fighter);
        }
        for (Fighter fighter : waitingToMove) {
            fighter.move(delta*game.getGameModeState().getSpeedMultiplier());

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
        shapeRenderer.rect(0, 0, GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    @Override
    public void render(float delta) {
        if (simulation == Simulation.RUN) {
            update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        st.draw();
        game.getSpriteBatch().setProjectionMatrix(gamecam.combined);
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(player1.getCore().getTexture(), player1.getCore().getPosition().x, player1.getCore().getPosition().y);
        game.getSpriteBatch().draw(player2.getCore().getTexture(), player2.getCore().getPosition().x, player2.getCore().getPosition().y);
        goldWidget1.render(game.getSpriteBatch());
        goldWidget2.render(game.getSpriteBatch());
        healthWidget1.render(game.getSpriteBatch());
        healthWidget2.render(game.getSpriteBatch());

        for (Fighter fighter : player1.getFighters()) {
            game.getSpriteBatch().draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
        }
        for (Fighter fighter : player2.getFighters()) {
            game.getSpriteBatch().draw(fighter.getTexture(), fighter.getPosition().x, fighter.getPosition().y);
        }
        game.getSpriteBatch().end();

        if (simulation == Simulation.PAUSE) {
            //Pause black screen with opacity 0.5
            updatePause(delta);
        }
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

    public void setGameState(Simulation s){
        this.simulation = s;
    }
}
