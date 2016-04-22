package com.tdt4240grp8.game.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240grp8.game.screens.StartScreen;
import com.tdt4240grp8.game.states.GameModeState;

public class GeometryAssault extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int PLAYER_START_GOLD = 300;
	public static final int PLAYER_START_HEALTHPOINTS = 100;
	public static final String TITLE = "Geometry Assault";

	private SpriteBatch batch;
	private GameModeState gameModeState;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public GameModeState getGameModeState() {
		return gameModeState;
	}

	public void setGameModeState(GameModeState gameModeState) {
		this.gameModeState = gameModeState;
	}
}
