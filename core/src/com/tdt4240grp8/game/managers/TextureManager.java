package com.tdt4240grp8.game.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureManager {

    private static TextureManager instance = new TextureManager();

    private HashMap<String, Texture> textures = new HashMap<String, Texture>();

    private TextureManager() {}

    public static TextureManager getInstance() {
        return instance;
    }

    public Texture getTexture(String path) {
        if (textures.get(path) == null) {
            textures.put(path, new Texture(path));
        } else {
        }
        return textures.get(path);
    }

}
