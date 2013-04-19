package com.siegedog.eggs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.siegedog.eggs.util.Log;

public class EggGame extends Game {
	
        public void create () {
        	Gdx.app.setLogLevel(Logger.DEBUG);
        	Log.D("Starting game!");
        }

        public void render () {
        }

        public void resize (int width, int height) {
        }

        public void pause () {
        }

        public void resume () {
        }

        public void dispose () {
        	Log.D("Ending Eggs.");
        }
}
