package com.tdt4240grp8.game.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240grp8.game.screens.PlayScreen;
import com.tdt4240grp8.game.screens.StartScreen;
import com.tdt4240grp8.game.states.GameModeState;
import com.tdt4240grp8.game.states.HypersonicState;

public class GeometryAssault extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int PLAYER_START_GOLD = 2000;
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
