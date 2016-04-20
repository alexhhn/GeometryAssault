package com.tdt4240grp8.game.managers;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * All textures are loaded through this class. If a texture has been loaded befure,
 * the texture will be reused instead of loading it again.
 */
public class TextureManager {

    private static TextureManager instance = new TextureManager();

    private HashMap<String, Texture> textures = new HashMap<String, Texture>();

    private TextureManager() {}

    public static TextureManager getInstance() {
        return instance;
    }

    // If a texture is loaded for the first time, put it in the HashMap, then return it
    // If it has been loaded before (if it is in the HashMap), return the existing texture
    public Texture getTexture(String path) {
        if (textures.get(path) == null) {
            textures.put(path, new Texture(path));
        }
        return textures.get(path);
    }

}
