package com.siegedog.eggs;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopGame {
        public static void main (String[] args) {
        	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        	config.fullscreen = false;
        	config.width = 800;
        	config.height = 480;
        	config.samples = 4;
        	config.title = "Eggs";
        	config.useGL20 = false;
            new LwjglApplication(new EggGame(), config);
        }
}
