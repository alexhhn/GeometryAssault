package com.tdt4240grp8.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240grp8.game.screens.StartScreen;
import com.tdt4240grp8.game.sounds.SoundManager;
import com.tdt4240grp8.game.states.GameModeState;
import com.tdt4240grp8.game.states.HypersonicState;

public class GeometryAssault extends Game {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final int PLAYER_START_GOLD = 2000;
	public static final int PLAYER_START_HEALTHPOINT = 20;


	public static final String TITLE = "GeometryAssault";
	public SpriteBatch batch;
	public GameModeState gameModeState;


	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
		gameModeState = new HypersonicState();
		SoundManager.sharedInstance.playMusic();
	}

	@Override
	public void render () {
		super.render();
	}

}
