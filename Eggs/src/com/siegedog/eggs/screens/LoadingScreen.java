package com.siegedog.eggs.screens;

import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class LoadingScreen extends GameScreen {

	@Override
	public void init(EggGame game) {
		super.init(game);
		
		EggGame.R
			.loadTex("warmup_sheet.gif", "sheet")
			.loadFont("motorwerk16")
			.loadFont("motorwerk24")
			.loadFont("motorwerk32")
			.loadFont("motorwerk128")
			.loadAtlas("img/atlas/pack.atlas")
			.onLoadFinish(new Runnable() {			
				public void run() {
					EggGame.R.createSprites("sheet", 96, 0, 32, 32, new String[] {
							"laser",
							"rocketLauncher",
							"rocket",
							"smoke",
							"shipExhaust",
							"crate",
							"circle"
					});
				}
			});
	}
	
	@Override
	public void render(float delta) {
		EggGame.R.update(delta);
		if(EggGame.R.done()) {
			game.setScreen(new Title1951());
		} else {
			Log.D("Still loading....");
		}
		super.render(delta);
	}
}
