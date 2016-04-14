package com.tdt4240grp8.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.tdt4240grp8.game.GeometryAssault;
import com.tdt4240grp8.game.managers.TextureManager;
import com.tdt4240grp8.game.sprites.Bird;
import com.tdt4240grp8.game.sprites.Core;
import com.tdt4240grp8.game.sprites.Tube;

public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = 50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Core core1, core2;

    private Button button;

    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        button = new Button();
        core1 = new Core(50, 200);
        core2 = new Core(GeometryAssault.WIDTH - 100, 200);
        bird = new Bird(50, 300);

        bg = TextureManager.getInstance().getTexture("bg.png");
        ground = TextureManager.getInstance().getTexture("ground.png");

        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        cam.setToOrtho(false, GeometryAssault.WIDTH, GeometryAssault.HEIGHT);
        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);

        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);
            if (tube.collides(bird.getBounds())) {
                gsm.set(new PlayState(gsm));
            }
        }
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(core1.getTexture(), 100, 100);
        sb.draw(core2.getTexture(), GeometryAssault.WIDTH - 100, 100);
        sb.end();
    }

    @Override
    public void dispose() {
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
