package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class LoadingScreen extends GameScreen {

	BitmapFont bf = new BitmapFont();
	
	@Override
	public void init(EggGame game) {
		super.init(game);
		
		bf.setScale(2.0f);
		
		EggGame.R
			.loadFont("motorwerk20")
			.loadFont("motorwerk24")
			.loadFont("motorwerk32")
			.loadFont("motorwerk128")
			.loadAtlas("img/atlas/pack.atlas");
	}
	
	@Override
	public void render(float delta) {
		EggGame.R.update(delta);
		if(EggGame.R.done()) {
			game.setScreen(new Title1951());
		}
		
		float loaded = EggGame.R.getInternal().getProgress();
		SpriteBatch sb = stage.getSpriteBatch();
		sb.begin();
		bf.drawWrapped(sb, String.format("1951 is loading\nsiegedog.com\nLoaded: %.0f %%", loaded * 100.0f) , 0, Gdx.graphics.getHeight() / 2.0f - 24,
				Gdx.graphics.getWidth(), HAlignment.CENTER);
		sb.end();
		
		super.render(delta);
	}
}
