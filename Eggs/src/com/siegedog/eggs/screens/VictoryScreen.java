package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.siegedog.eggs.util.TextHelper;

public class VictoryScreen extends GameScreen {

	BitmapFont bf = new BitmapFont();
	BitmapFont smallf = new BitmapFont();
	
	public VictoryScreen() {
		bf.setScale(1.5f);
		smallf.setScale(1.5f);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				game.setScreen(new GameplayScreen());
				return super.touchUp(screenX, screenY, pointer, button);
			}
		});
		
		SpriteBatch sb = getStage().getSpriteBatch();
		sb.begin();
		TextHelper.draw(sb, "You, like, won or something.", bf, 
				Color.WHITE, 0, Gdx.graphics.getHeight() / scale - 20,
				Gdx.graphics.getWidth() / scale, HAlignment.CENTER, 1.0f				
				);
		
		TextHelper.draw(sb, "Tap to restart.", smallf, 
				Color.WHITE, 0, Gdx.graphics.getHeight() / (2 * scale) + 10,
				Gdx.graphics.getWidth() / scale, HAlignment.CENTER, 1.0f				
				);
		sb.end();
	}
}
