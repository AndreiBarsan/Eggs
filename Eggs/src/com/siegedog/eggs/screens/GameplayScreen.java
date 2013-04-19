package com.siegedog.eggs.screens;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class GameplayScreen extends GameScreen {

	static class Dude extends Actor {
		protected Sprite sprite;
		public Vector2 speed = new Vector2();
		
		public Dude(Sprite sprite) {
			this.sprite = sprite;
		}
		
		@Override
		public void act(float delta) {
			super.act(delta);
			
			setX(getX() + delta * speed.x);
			setY(getY() + delta * speed.y);
			
			setWidth(sprite.getWidth());
			setHeight(sprite.getHeight());
			setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			//setRotation((float) (getRotation() + (30) * delta));
			
			if(getX() - getOriginX() < 0) {
				setX(getOriginX());
				speed.x = Math.abs(speed.x);
			}
			
			if(getX() + getOriginX() > getStage().getWidth()) {
				setX(getStage().getWidth() - getOriginX());
				speed.x = -Math.abs(speed.x);
			}
			
			if(getY() < 0) {
				setY(0);
				speed.y = -speed.y;
			}
			
			if(getY() + getOriginY() > getStage().getHeight()) {
				setY(getStage().getHeight() - getOriginY());
				speed.y = -speed.y;
			}
		}
		
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);

			if(sprite != null) {
				sprite.setScale(2.0f);
				sprite.setPosition(getX(), getY());
				sprite.setOrigin(getOriginX(), getOriginY());
				sprite.setRotation(getRotation());
				sprite.draw(batch);
			}
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
		
		for(int i = 0; i < 25; ++i) {
			AnimatedSprite spr = EggGame.R.animatedSprite("enemy");
			spr.play("wobble");
			spr.setPlayMode(Animation.LOOP_PINGPONG);
			Dude dude = new Dude(spr);
			dude.setX(MathUtils.random(0, 800));
			dude.setY(MathUtils.random(0, 480));
			dude.speed = new Vector2(MathUtils.random(-50.0f, 50.0f), MathUtils.random(-50.0f, 50.0f));
			stage.addActor(dude);
		}
		
		Log.D("Entered Gameplay with the resources loaded well!");
		
		Dude boom = new Dude(EggGame.R.animatedSprite("explosion"));
		((AnimatedSprite)boom.getSprite()).play("explode");
		((AnimatedSprite)boom.getSprite()).setPlayMode(Animation.LOOP);
		((AnimatedSprite)boom.getSprite()).onLoop(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("LOOP");
			}
		});
		stage.addActor(boom);
	}
}
