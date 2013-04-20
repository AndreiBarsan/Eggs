package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class GameplayScreen extends GameScreen {

	static class Dude extends Actor {
		protected AnimatedSprite sprite;
		protected GameScreen screen;
		private boolean dead = false;
		public Vector2 speed = new Vector2();
		
		public Dude(AnimatedSprite sprite) {
			this.sprite = new AnimatedSprite(sprite);
		}
		
		public Dude(Actor other, AnimatedSprite sprite) {
			setX(other.getX());
			setY(other.getY());
			setOrigin(other.getOriginX(), other.getOriginY());
			setWidth(other.getWidth());
			setHeight(other.getHeight());
			this.sprite = new AnimatedSprite(sprite);
			
			System.out.println(other);
		}
		
		public void init(GameScreen screen) {
			this.screen = screen;
		}
		
		@Override
		public void act(float delta) {
			if(dead) {
				return;
			}
			
			super.act(delta);

			sprite.update(Gdx.graphics.getDeltaTime());
			
			setX(getX() + delta * speed.x);
			setY(getY() + delta * speed.y);
		}
		
		public void kill() {
			if(isDead()) {
				Log.E("Killed enemy twice. Look for bugs!");
			}
			dead = true;
			screen.signalDead(this);
		}
		
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			if(dead) {
				return;
			}
			
			super.draw(batch, parentAlpha);

			if(sprite != null) {
				sprite.setPosition(getX(), getY());
				sprite.setOrigin(getOriginX(), getOriginY());
				sprite.setRotation(getRotation());
				sprite.draw(batch);
			}
		}
		
		public boolean isDead() {
			return dead;
		}
		
		public AnimatedSprite getSprite() {
			return sprite;
		}

		public void setSprite(AnimatedSprite sprite) {
			this.sprite = sprite;
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
					System.out.println("Whoops");
				}
				keys[keycode] = true;
				return super.keyDown(keycode);
			}
			
			@Override
			public boolean keyUp(int keycode) {
				if(keycode > klimit) {
					System.out.println("Whoops");
				}
				
				if(keycode == Keys.SPACE) {
					int al = stage.getActors().size;
					Dude d = (Dude) stage.getActors().get(MathUtils.random(0, al - 1));
					d.kill();
				}
				
				keys[keycode] = false;
				return super.keyUp(keycode);
			}
			
			
			
		});
	}
	
	class Enemy extends Dude {
		public Enemy() {
			super(EggGame.R.animatedSprite("enemy"));
			sprite.play("wobble");
			sprite.setPlayMode(Animation.LOOP_PINGPONG);
			setName("Enemy");
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
			System.out.println("Enemy dead");
			AnimatedSprite spr = EggGame.R.animatedSprite("explosion");
			final Dude explosion = new Dude(this, spr);
			explosion.setName("explosion");
			explosion.getSprite().play("explode");
			explosion.getSprite().onLoop(new Runnable() {
				@Override
				public void run() {
					explosion.kill();
					System.out.println("Explosion being killed");
				}
			});
			
			screen.addDude(explosion);
		}
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
		bf.draw(sb, "Derp: " + stage.getActors().size, 20, 20);
		sb.end();
	}
}
