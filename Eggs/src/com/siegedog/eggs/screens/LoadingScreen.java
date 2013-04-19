package com.siegedog.eggs.screens;

import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;

public class LoadingScreen extends GameScreen {
		
	public LoadingScreen() {
		EggGame.R
			.loadTex("warmup_sheet.gif", "sheet")
			.onLoadFinish(new Runnable() {			
				public void run() {
					EggGame.R.createSprite("sheet", 48, 0, 48, 48, "redScarab");
					EggGame.R.createSprites("sheet", 96, 0, 32, 32, new String[] {
							"laser",
							"rocketLauncher",
							"rocket",
							"smoke",
							"shipExhaust"
					});
					// FIXME: maybe make a laarge wide sprite, and THEN divide it into frames
					// and not just make a small sprite than move right with the coords
					EggGame.R.createAnimatedSprite("sheet", 0, 48, 48, 48, "explosion");
					AnimatedSprite as = EggGame.R.animatedSprite("explosion");
					as.addAnimationByFrameCount("explode", 7, 0.07f);
					
					EggGame.R.createAnimatedSprite("sheet", 0, 96, 48, 48, "enemy");
					as = EggGame.R.animatedSprite("enemy");
					as.addAnimationByFrameCount("wobble", 9, 1.20f);
					
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
