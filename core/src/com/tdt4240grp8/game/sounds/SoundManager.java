package com.tdt4240grp8.game.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by mikaila on 14/04/16.
 */
public class SoundManager {
    public static SoundManager sharedInstance = new SoundManager();
    public static float VOLUME = 1.0f;
    public Sound music = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
    public Sound punchSound = Gdx.audio.newSound(Gdx.files.internal("punch.mp3"));
    public Sound death = Gdx.audio.newSound(Gdx.files.internal("punch-death.mp3"));


    private SoundManager(){

    }

    public void playMusic(){
        music.play(VOLUME);
    }



    public void play(Sound sound){
        sound.play(VOLUME);
    }

    public static void muteFX(){
        VOLUME = 0.0f;
    }

    public static void normalizeFX(){
        VOLUME = 1.0f;
    }




}
