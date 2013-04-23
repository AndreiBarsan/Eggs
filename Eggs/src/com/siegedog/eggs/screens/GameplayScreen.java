package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.DemoInput;
import com.siegedog.eggs.Dude;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class GameplayScreen extends GameScreen {

	private int left;
	BitmapFont bf = new BitmapFont();
	public int x0 = -200;
	public int y0 = -200;
	public int x1 =  250;
	public int y1 =  250;
	
	class Blob extends Dude {
		public Blob() {
			super(EggGame.R.animatedSprite("enemy"));
			sprite.play("wobble");
			sprite.setPlayMode(Animation.LOOP_PINGPONG);
			setName("enemy");
			
			setTouchable(Touchable.enabled);
		}
		
		@Override
		public void act(float delta) {
			super.act(delta);
			
			setWidth(sprite.getWidth());
			setHeight(sprite.getHeight());
			setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			
			if(getX() < x0) {
				setX(x0);
				speed.x = -speed.x;
			}
			
			if(getRight() > x1) {
				setX(x1 - getWidth());
				speed.x = -speed.x;
			}
			
			if(getY() < y0) {
				setY(y0);
				speed.y = -speed.y;
			}
			
			if(getTop() > y1) {
				setY(y1 - getHeight());
				speed.y = -speed.y;
			}
			
			// System.out.println(getX() + ", " + getY() + " --" + getWidth() + " " + getHeight());
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
			EggGame.R.play("enemySound");
		}
	}
	
	@Override
	public void init(EggGame game) {
		super.init(game);
		Gdx.input.setInputProcessor(new DemoInput(this));
	}
	
	@Override
	public void show() {
		super.show();
		
		left = 25;
		
		for(int i = 0; i < left; ++i) {

			Blob e = new Blob();
			e.setX(MathUtils.random(x0, x1));
			e.setY(MathUtils.random(y0, y1));
			e.speed = new Vector2(MathUtils.random(-50.0f, 50.0f), MathUtils.random(-50.0f, 50.0f));
			e.onDeath = new Runnable() {
				
				@Override
				public void run() {
					left --;
				}
			};
			addDude(e);
		}
		
		Log.D("Entered Gameplay with the resources loaded well!");
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if(left == 0) {
			stage.addAction(Actions.delay(2.5f, new Action() {
				@Override
				public boolean act(float delta) {
					game.setScreen(new VictoryScreen());
					return false;
				}
			}));
		}
		
		SpriteBatch sb = stage.getSpriteBatch();
		Camera cam = stage.getCamera();
		sb.begin();
		bf.draw(sb, "Blobs left: " + left, 
				cam.position.x - cam.viewportWidth / 2 + 4, cam.position.y - cam.viewportHeight / 2 + 16);
		sb.end();
	}
}
