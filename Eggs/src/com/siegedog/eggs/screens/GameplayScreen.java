package com.siegedog.eggs.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class GameplayScreen extends GameScreen {

	static class Dude extends Actor {
		protected Sprite sprite;
		
		public Dude(Sprite sprite) {
			this.sprite = sprite;
		}
		
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			
			sprite.draw(batch);
		}
		
		public Sprite getSprite() {
			return sprite;
		}

		public void setSprite(Sprite sprite) {
			this.sprite = sprite;
		}
	}
	
	@Override
	public void show() {
		super.show();
		
		Log.D("Entered Gameplay with the resources loaded well!");
		
		Dude boom = new Dude(EggGame.R.sprite("explosion"));
		((AnimatedSprite)boom.getSprite()).play("default");
		stage.addActor(boom);
	}
}
