package com.siegedog.eggs;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.siegedog.eggs.util.Resources;

public class DesktopGame {
        public static void main (String[] args) {
        	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        	config.fullscreen = false;
        	config.resizable = false;
        	config.width = 800;
        	config.height = 480;
        	config.samples = 4;
        	config.title = "1951";
        	config.useGL20 = false;
        	Resources.ASS_FOLDER = "";
        	Resources.refreshPaths();
            new LwjglApplication(new EggGame(), config);
        }
}
