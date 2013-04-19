package com.siegedog.eggs.screens;

import com.siegedog.eggs.EggGame;

public class LoadingScreen extends GameScreen {
		
	public LoadingScreen() {
		EggGame.R
			.loadTex("warmup_sheet.gif", "sheet")
			.onLoadFinish(new Runnable() {			
				public void run() {
					EggGame.R.createSprite("sheet", 0, 0, 48, 48, "enemy");
					EggGame.R.createSprite("sheet", 48, 0, 48, 48, "redScarab");
					EggGame.R.createSprites("sheet", 96, 0, 32, 32, new String[] {
							"laser",
							"rocketLauncher",
							"rocket",
							"smoke",
							"shipExhaust"
					});
					EggGame.R.createSprite("sheet", 0, 48, 48, 48 * 7, "explosion");
					EggGame.R.animatedSprite("explosion").addAnimationByFrameCount("default", 7, 0.15f);
					
					game.setScreen(new GameplayScreen());
				}
			});
		
	}
	
	@Override
	public void render(float delta) {
		EggGame.R.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
