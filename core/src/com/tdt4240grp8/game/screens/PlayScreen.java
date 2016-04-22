package com.tdt4240grp8.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240grp8.game.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.managers.SoundManager;
import com.tdt4240grp8.game.model.Circle;
import com.tdt4240grp8.game.model.Fighter;
import com.tdt4240grp8.game.model.GameObject;
import com.tdt4240grp8.game.model.Player;
import com.tdt4240grp8.game.model.Square;
import com.tdt4240grp8.game.model.Triangle;
import com.tdt4240grp8.game.widgets.GoldWidget;
import com.tdt4240grp8.game.widgets.HealthBar;
import com.tdt4240grp8.game.widgets.HealthWidget;
import com.tdt4240grp8.game.widgets.ProductionPreview;

import java.util.ArrayList;

/**
 * The main game screen
 */
public class PlayScreen implements Screen{

    private GeometryAssault game;
    // a model class for each player
    private Player player1, player2;
    // the stage that ui elements are drawn on
    private Stage st;
    // the game camera
    private OrthographicCamera gamecam;
    // viewport defines what area the camera should show
    private Viewport gamePort;
    // widgets that display player gold
    private GoldWidget goldWidget1, goldWidget2;
    // widgets that display player health
    private HealthWidget healthWidget1, healthWidget2;
    // widgets that display the fighter currently in production
    private ProductionPreview productionPreview1, productionPreview2;
    // renders the semi transparent black box in the pause screen
    private ShapeRenderer shapeRenderer;
    // buttons
    private Image resumeBtn;
    private Image pauseBtn;
    private Image quitBtn;

    // All of these are just for placing images, texture
    public static final int buttonXpos = 5, buttonYPos = 2 , buttonWidth = 163, buttonHeight = 250,
    coreYPos = buttonHeight + 5, fighterYPos = coreYPos + 18,hudXPos = 5, hudYPos = GeometryAssault.HEIGHT - 80, heartIconHeight = 65,
            goldXPos = hudXPos + 120, hudTextYPos = hudYPos - 4, previewImageSize = 50,
            progressBarYPos = hudYPos + 13, previewImageYPos = progressBarYPos - 8;

    // filename for the death sound
    private String deathSound = "punch.mp3";
    private String noGoldSound = "gold.mp3";

    // defines whether the game simulation should run or not (if the game is paused or not)
    public Simulation simulation = Simulation.RUN;

    public enum Simulation
    {
        PAUSE,
        RUN,
    }

    /**
     * Contructor. Initializes everything, adds ui elements to the stage, creates buttons
     */
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
        st.addActor(createStaticTexture("no-gold.png",goldXPos, hudYPos - 5));
        st.addActor(createStaticTexture("no-gold.png",GeometryAssault.WIDTH - goldXPos - heartIconHeight - heartIconHeight/2, hudYPos - 5));
        st.getActors().get(4).setVisible(false);
        st.getActors().get(5).setVisible(false);

        st.addActor(healthWidget1.getImg());
        st.addActor(healthWidget2.getImg());
        st.addActor(productionPreview1);
        st.addActor(productionPreview1.getImg());
        st.addActor(productionPreview2);
        st.addActor(productionPreview2.getImg());

        productionPreview1.setVisible(false);
        productionPreview2.setVisible(false);

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

        createButton(player1, "square-button.png","square-button-tapped.png", buttonXpos, buttonYPos, Player.Fighters.SQUARE);
        createButton(player1, "triangle-button.png","triangle-button-tapped.png", buttonXpos + buttonWidth + 5, buttonYPos, Player.Fighters.TRIANGLE);
        createButton(player1, "circle-button.png", "circle-button-tapped.png",buttonXpos + buttonWidth * 2 + 10, buttonYPos, Player.Fighters.CIRCLE);

        createButton(player2, "circle-button-face-left.png","circle-button-face-left-tapped.png", GeometryAssault.WIDTH - buttonWidth * 3 - 15, buttonYPos, Player.Fighters.CIRCLE);
        createButton(player2, "triangle-button-face-left.png","triangle-button-face-left-tapped.png", GeometryAssault.WIDTH - buttonWidth * 2 - 10, buttonYPos, Player.Fighters.TRIANGLE);
        createButton(player2, "square-button-face-left.png","square-button-face-left-tapped.png", GeometryAssault.WIDTH - buttonWidth - 5, buttonYPos, Player.Fighters.SQUARE);
    }

    /**
     * Method for creating the buttons that spawn fighters
     * @param player which player they belong to
     * @param texturePath the path to the button texture
     * @param x x position
     * @param y y position
     * @param fighter which type of fighter to create
     * @return
     */
    private ImageButton createButton(final Player player, String texturePath, String texturePath2, int x, int y, final Player.Fighters fighter) {
        ImageButton imageButton = new ImageButton(new Image(TextureManager.getInstance().getTexture(texturePath)).getDrawable(),
                new Image(TextureManager.getInstance().getTexture(texturePath2)).getDrawable()
                );

        imageButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (simulation == Simulation.RUN) {
                    int productionCost = 0;
                    switch (fighter) {
                        case SQUARE:
                            productionCost = Square.PRODUCTION_COST;
                            break;
                        case TRIANGLE:
                            productionCost = Triangle.PRODUCTION_COST;
                            break;
                        case CIRCLE:
                            productionCost = Circle.PRODUCTION_COST;
                            break;
                    }
                    if (player.getGold() < productionCost){
                        if (player == player1){
                            goldWidget1.getImg().setColor(255,0,0,.4f);
                            goldWidget1.getGoldFont().setColor(Color.RED);
                            st.getActors().get(4).setVisible(true);

                        } else {
                            goldWidget2.getImg().setColor(255,0,0,.4f);
                            goldWidget2.getGoldFont().setColor(Color.RED);
                            st.getActors().get(5).setVisible(true);

                        }
                        SoundManager.getInstance().playSound(noGoldSound);
                    }

                    Fighter createdFighter = player.addFighter(fighter); // will return null if a fighter is already in production, or if player has insufficient gold
                    if (createdFighter != null) {
                        HealthBar healthBar = new HealthBar();
                        createdFighter.addFighterListener(healthBar);
                        player.addGold(-createdFighter.getProductionCost());
                        st.addActor(healthBar);

                        if (player == player1){
                            productionPreview1.setVisible(true);
                            System.out.println(player.getGold());
//                            System.out.println(createdFighter.getProductionCost());
                        } else{
                            productionPreview2.setVisible(true);
                        }
                        return true;
                    }
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                float delay = .4f; // seconds
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        if (simulation == Simulation.RUN) {
                                if (player == player1){
                                    goldWidget1.getImg().setColor(Color.WHITE);
                                    goldWidget1.getGoldFont().setColor(Color.valueOf("f85900"));
                                } else {
                                    goldWidget2.getImg().setColor(Color.WHITE);
                                    goldWidget2.getGoldFont().setColor(Color.valueOf("f85900"));
                                }
//                        SoundManager.getInstance().playSound(deathSound);
                            st.getActors().get(2).setColor(Color.WHITE);
                            goldWidget1.getGoldFont().setColor(Color.valueOf("f85900"));
                            st.getActors().get(4).setVisible(false);
                            st.getActors().get(5).setVisible(false);

                        }
                    }
                }, delay);

            }
        });



        imageButton.setPosition(x, y);
        st.addActor(imageButton);
        return imageButton;
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
                    game.setScreen(new StartScreen(game));
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

    /**
     * Called at the start of every render frame, to update the game state
     * @param delta time since the last call
     */
    public void update(float delta) {
        // updates the fighter currently in production for both players
        player1.update(delta);
        player2.update(delta);



        // creates a new list of all fighters waiting to move
        // any fighter that should move this frame is added to the list
        // The actual movement happens when this has been determined for all fighters
        ArrayList<Fighter> waitingToMove = new ArrayList<Fighter>();
        for (Fighter fighter : player1.getFighters()) {
            // updates the fighter's attack cooldown
            fighter.update(delta);
            // keeps track of whether the fighter has collided with anything
            boolean collided = false;
            // if fighter collides with the enemy core
            if (collides(fighter, player2.getCore())) {
                collided = true;
                // if fighter is able to attack
                if (fighter.attackOffCooldown()) {
                    fighter.attack(player2);
                    fighter.resetAttackCooldown();
                }
            }
            // checks if fighter collides with an enemy fighter
            for (Fighter fighter2 : player2.getFighters()) {
                if (collides(fighter, fighter2)) {
                    collided = true;

                    if (fighter.attackOffCooldown()) {
                        fighter.attack(fighter2);
                        fighter.resetAttackCooldown();
                    }
                    break;
                }
            }
            // if the fighetr has not collided with anything yet, add it to the move-list
            if (!collided) {
                waitingToMove.add(fighter);
            }
        }
        // same as above, now for player 2
        for (Fighter fighter : player2.getFighters()) {
            fighter.update(delta);
            boolean collided = false;
            if (collides(fighter, player1.getCore())) {
                collided = true;
                if (fighter.attackOffCooldown()) {
                    fighter.attack(player1);
                    fighter.resetAttackCooldown();
                }

            }
            for (Fighter fighter1 : player1.getFighters()) {
                if (collides(fighter, fighter1)) {
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
        // creates a new list of all fighters that are going to die
        // any fighter that should die this frame is added to the list
        // The actual dying happens when this has been determined for all fighters
        ArrayList<Fighter> markedForDeath = new ArrayList<Fighter>();
        for (Fighter fighter : player1.getFighters()) {
            // if a fighter dies, play a sound and add it to the list
            if (fighter.isDead()) {
                SoundManager.getInstance().playSound(deathSound);
                markedForDeath.add(fighter);
            }
        }
        // goes through the markedForDeath list, adding gold to the other player and removing the fighter
        for (Fighter fighter : markedForDeath) {
            player2.addGold(fighter.getGoldValue()*game.getGameModeState().getGoldMultiplier());
            player1.removeFighter(fighter);
        }
        // same as above, now for player 2
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
        // move the fighters waiting to move
        for (Fighter fighter : waitingToMove) {
            fighter.move(delta*game.getGameModeState().getSpeedMultiplier());

        }
        // check if one of the players are dead
        if (player1.isDead() ) {
           game.setScreen(new VictoryScreen(game,true));
        }
        if (player2.isDead() ) {
            game.setScreen(new VictoryScreen(game,false));
        }

    }

    /**
     * Returns whether the bounds of one gameobject overlaps with the bounds of another
     */
    private boolean collides(GameObject r1, GameObject r2) {
        return r1.getBounds().overlaps(r2.getBounds());
    }

    /**
     * Extra render method for when the game is paused
     */
    public void renderPause(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(gamecam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
        shapeRenderer.rect(0, 0, GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.getSpriteBatch().begin();
        resumeBtn.draw(game.getSpriteBatch(), 1);
        quitBtn.draw(game.getSpriteBatch(), 1);
        game.getSpriteBatch().end();
    }

    /**
     * Called every frame. Renders the screen
     * @param delta time since the last call
     */
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
            renderPause();
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
