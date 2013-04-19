package com.siegedog.eggs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Logger;
import com.siegedog.eggs.screens.GameScreen;
import com.siegedog.eggs.screens.LoadingScreen;
import com.siegedog.eggs.util.Log;
import com.siegedog.eggs.util.Resources;

public class EggGame implements ApplicationListener {
	
		/* Public static utilities */
		public static Resources R;
		
		/* Fields */
		protected GameScreen activeScreen; 
		
        public void create () {
        	Gdx.app.setLogLevel(Logger.DEBUG);
        	Log.D("Starting game!");
        	
        	R = new Resources();
        	setScreen(new LoadingScreen());
        }
        
        public void setScreen(GameScreen screen) {
        	assert screen != null;
        	
        	if(activeScreen != null) {
        		activeScreen.hide();
        	}
        	
        	activeScreen = screen;
        	activeScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        	activeScreen.init(this);
        	activeScreen.show();
        }
        
        @Override
    	public void render () {
        	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    		if (activeScreen != null) {
    			activeScreen.render(Gdx.graphics.getDeltaTime());
    		}
    	}

    	@Override
    	public void resize (int width, int height) {
    		if (activeScreen != null) {
    			activeScreen.resize(width, height);
    		}
    	}
    	
        public void pause () {
        	activeScreen.pause();
        }

        public void resume () {
        	activeScreen.resume();
        }

        public void dispose () {
        	activeScreen.dispose();
        	Log.D("Ending Eggs.");
        }
}
