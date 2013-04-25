package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TileMapRendererLoader;
import com.badlogic.gdx.assets.loaders.TileMapRendererLoader.TileMapParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
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
	public int x0 = 0;
	public int y0 = 0;
	public int x1 = 512;
	public int y1 = 512;
	
	TileMapRenderer tmr;
	
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
				physics.velocity.x = -physics.velocity.x;
			}
			
			if(getRight() > x1) {
				setX(x1 - getWidth());
				physics.velocity.x = -physics.velocity.x;
			}
			
			if(getY() < y0) {
				setY(y0);
				physics.velocity.y = -physics.velocity.y;
			}
			
			if(getTop() > y1) {
				setY(y1 - getHeight());
				physics.velocity.y = -physics.velocity.y;
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
			switch(MathUtils.random(0, 2)) {
			case 0:
				EggGame.R.play("enemySound01");
				break;
				
			case 1:
				EggGame.R.play("enemySound02");
				break;
				
			case 2:
				EggGame.R.play("enemySound03");
				break;
			}
			
		}
	}
	
	@Override
	public void init(EggGame game) {
		super.init(game);
		Gdx.input.setInputProcessor(new DemoInput(this));
		
		TileMapRendererLoader loader = new TileMapRendererLoader(new InternalFileHandleResolver());
		TileMapParameter param = new TileMapParameter("assets/data/lvl/img", 1, 1);
		
		tmr = loader.load(EggGame.R.getInternal(), "assets/data/lvl/baked/testLevel.tmx", param);
	}
	
	@Override
	public void show() {
		super.show();
		
		left = 25;
		
		//*
		for(int i = 0; i < left; ++i) {
			
			Blob blob = new Blob();
			blob.setX(MathUtils.random(x0, x1));
			blob.setY(MathUtils.random(y0, y1));
			blob.physics.velocity = new Vector2(MathUtils.random(-50.0f, 50.0f), MathUtils.random(-50.0f, 50.0f));
			blob.onDeath = new Runnable() {
				@Override
				public void run() {
					left--;
				}
			};
			addDude(blob);
		}
		//*/
		
		Log.D("Entered Gameplay with the resources loaded well!");
	}
	
	@Override
	public void render(float delta) {
		tmr.render((OrthographicCamera) stage.getCamera());
		
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
		
		bf.draw(sb, "FPS " + Gdx.graphics.getFramesPerSecond(), 
				cam.position.x + cam.viewportWidth / 2 - 50, cam.position.y - cam.viewportHeight / 2 + 16);
		sb.end();
	}
	
	public int getScale() {
		return scale;
	}
}
