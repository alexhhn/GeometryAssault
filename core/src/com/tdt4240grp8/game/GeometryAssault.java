package com.tdt4240grp8.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240grp8.game.screens.StartScreen;
import com.tdt4240grp8.game.states.GameModeState;
import com.tdt4240grp8.game.states.HypersonicState;
import com.tdt4240grp8.game.states.NormalState;
import com.tdt4240grp8.game.states.WealthyState;

public class GeometryAssault extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
//	public static final int WIDTH = 640;
//	public static final int HEIGHT = 320;
	public static final int PLAYER_START_GOLD = 2000;
	public static final int PLAYER_START_HEALTHPOINT = 99;


	public static final String TITLE = "GeometryAssault";
	public SpriteBatch batch;
	public GameModeState gameModeState;


	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
		gameModeState = new HypersonicState();
	}

	@Override
	public void render () {
		super.render();
	}

}
