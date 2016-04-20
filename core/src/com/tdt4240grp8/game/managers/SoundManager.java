package com.tdt4240grp8.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Class that takes care of all things related to sound/music.
 * Makes sure to only play them when the sound is enabled.
 */
public class SoundManager {

    private static SoundManager instance = new SoundManager();
    public static boolean soundEnabled = true;

    // Can easily be extended to HashMap<String, Music> to support multiple music tracks
    private Music music;

    private HashMap<String, Sound> sounds = new HashMap<String, Sound>();

    private SoundManager(){
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
    }

    public static SoundManager getInstance() {
        return instance;
    }

    public void startMusic() {
        if (soundEnabled) {
            music.play();
        }
    }

    public void stopMusic() {
        music.stop();
    }

    // If a sound is loaded for the first time, put it in the HashMap, then return it
    // If it has been loaded before (if it is in the HashMap), return the existing sound
    public void playSound(String path) {
        if (sounds.get(path) == null) {
            sounds.put(path, Gdx.audio.newSound(Gdx.files.internal(path)));
        }
        sounds.get(path).play();
    }

}
