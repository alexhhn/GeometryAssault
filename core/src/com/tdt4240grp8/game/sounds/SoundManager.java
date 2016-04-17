package com.tdt4240grp8.game.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mikaila on 14/04/16.
 */
public class SoundManager {
    public static SoundManager sharedInstance = new SoundManager();
    public static float VOLUME = 1.0f;
    public HashMap<Long, Sound> ids = new HashMap<Long, Sound>();

    private SoundManager(){
    }

    public void put(long id, Sound s) {
        ids.put(id, s);
    }

    public void mute(){
        for (Map.Entry<Long, Sound> entry : ids.entrySet()) {
            long id = entry.getKey();
            Sound sound = entry.getValue();
            sound.stop(id);
            ids.remove(id,sound);
        }
    }

    public static void muteFX(){
        VOLUME = 0.0f;
    }

    public static void normalizeFX(){
        VOLUME = 1.0f;
    }




}
