package com.siegedog.eggs.dev;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Resources;

public class DevQuickLaunch {
        public static void main (String[] args) throws Exception {
        	
        	BakeTileMaps.main(args);
        	
        	TexturePacker2.main(new String[] {
        		Resources.texRoot,
        		Resources.texRoot + "atlas/"
        	});
        	
        	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        	config.fullscreen = false;
        	config.resizable = false;
        	config.width = 800;
        	config.height = 480;
        	config.samples = 4;
        	config.title = "Eggs";
        	config.useGL20 = false;
            new LwjglApplication(new EggGame(), config);
        }
}
