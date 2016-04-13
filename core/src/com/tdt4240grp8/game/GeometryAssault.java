package com.tdt4240grp8.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240grp8.game.screens.StartScreen;
import com.tdt4240grp8.game.states.GameStateManager;

public class GeometryAssault extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int PLAYER_START_GOLD = 2000;
	public static final int PLAYER_START_HEALTHPOINT = 20;


	public static final String TITLE = "GeometryAssault";
	private GameStateManager gsm;
	public SpriteBatch batch;


	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
