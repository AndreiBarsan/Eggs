package com.siegedog.eggs.screens;

import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;

public class LoadingScreen extends GameScreen {

	@Override
	public void init(EggGame game) {
		super.init(game);
		
		EggGame.R
			.loadTex("warmup_sheet.gif", "sheet")
			.loadFont("motorwerk16")
			.loadFont("motorwerk32")
			.loadFont("motorwerk128")
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
			game.setScreen(new Title1953());
		}
		super.render(delta);
	}
}
