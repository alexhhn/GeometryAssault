package com.tdt4240grp8.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tdt4240grp8.game.GeometryAssault;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GeometryAssault.WIDTH;
		config.height = GeometryAssault.HEIGHT;
		config.title = GeometryAssault.TITLE;
		new LwjglApplication(new GeometryAssault(), config);
	}
}
