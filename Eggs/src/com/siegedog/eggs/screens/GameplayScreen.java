package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class GameplayScreen extends GameScreen {

	class Enemy extends Dude {
		public Enemy() {
			super(EggGame.R.animatedSprite("enemy"));
			sprite.play("wobble");
			sprite.setPlayMode(Animation.LOOP_PINGPONG);
			setName("enemy");
		}
		
		@Override
		public void act(float delta) {
			super.act(delta);
			
			setWidth(sprite.getWidth());
			setHeight(sprite.getHeight());
			setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			
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
		public void kill() {
			super.kill();
			final Dude explosion = new Dude(this, EggGame.R.animatedSprite("explosion"));
			explosion.setName("explosion");
			explosion.getSprite().play("explode");
			explosion.getSprite().onLoop(new Runnable() {
				@Override
				public void run() {
					explosion.kill();
				}
			});
			
			Dude deadBody = new Dude(this, EggGame.R.animatedSprite("enemy"));
			deadBody.getSprite().play("dead");
			screen.addDude(deadBody);
			screen.addDude(explosion);			
		}
	}
	
	@Override
	public void init(EggGame game) {
		super.init(game);
		stage.setViewport(400, 240, true);
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			final int klimit = 512;
			boolean keys[] = new boolean[klimit];
			
			@Override
			public boolean keyDown(int keycode) {
				if(keycode > klimit) {
					Log.E("Invalid keycode. Plz fix me.");
				}
				keys[keycode] = true;
				return super.keyDown(keycode);
			}
			
			@Override
			public boolean keyUp(int keycode) {
				if(keycode > klimit) {
					Log.E("Invalid keycode. Plz fix me.");
				}
				
				if(keycode == Keys.SPACE) {
					int al = stage.getActors().size;
					String name = ""; 
					Dude d = null;
					while(al > 0 && name != "enemy") {
						d = (Dude) stage.getActors().get(MathUtils.random(0, al - 1));
						name = d.getName();
						al--;
					}
					if(al != 0) {
						d.kill();
					}
				}
				
				keys[keycode] = false;
				return super.keyUp(keycode);
			}
		});
	}
	
	@Override
	public void show() {
		super.show();
		
		for(int i = 0; i < 25; ++i) {

			Enemy e = new Enemy();
			e.setX(MathUtils.random(0, 800));
			e.setY(MathUtils.random(0, 480));
			e.speed = new Vector2(MathUtils.random(-50.0f, 50.0f), MathUtils.random(-50.0f, 50.0f));
			addDude(e);
		}
		
		Log.D("Entered Gameplay with the resources loaded well!");
	}
	
	BitmapFont bf = new BitmapFont();
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		SpriteBatch sb = stage.getSpriteBatch();
		sb.begin();
		bf.draw(sb, "Entities: " + stage.getActors().size, 16, 16);
		sb.end();
	}
}
