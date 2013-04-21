package com.siegedog.eggs.dev;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.siegedog.eggs.EggGame;

public class DevQuickLaunch {
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