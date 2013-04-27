package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TileMapRendererLoader;
import com.badlogic.gdx.assets.loaders.TileMapRendererLoader.TileMapParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.DemoInput;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.entities.Block;
import com.siegedog.eggs.entities.Dude;
import com.siegedog.eggs.physics.AABB;
import com.siegedog.eggs.physics.Circle;
import com.siegedog.eggs.util.Log;
import com.siegedog.eggs.util.Resources;

public class GameplayScreen extends GameScreen {

	private int left;
	BitmapFont bf;
	public int x0 = 0;
	public int y0 = 0;
	public int x1 = 512;
	public int y1 = 512;
	
	TileMapRenderer tmr;
	
	class Blob extends Dude {
		public Blob() {
			super(EggGame.R.animatedSprite("enemy"),
					new AABB(0, 0, 24, 32));
			sprite.play("wobble");
			sprite.setPlayMode(Animation.LOOP_PINGPONG);
			setName("enemy");
			
			setTouchable(Touchable.enabled);
			physics.interactive = true;
		}
		
		@Override
		public void act(float delta) {
			super.act(delta);
			
			setWidth(physics.getDimensions().x);
			setHeight(physics.getDimensions().y);
			//setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			
			if(getX() < x0) {
				setX(x0);
				physics.velocity.x = -physics.velocity.x;
			}
			
			if(getRight() > x1) {
				setX(x1 - getWidth());
				physics.velocity.x = -physics.velocity.x;
			}
			
			//*
			if(getY() < y0) {
				setY(y0);
				physics.velocity.y = -physics.velocity.y;
			}
			//*/
			
			if(getTop() > y1) {
				setY(y1 - getHeight());
				physics.velocity.y = -physics.velocity.y;
			}
			
			// System.out.println(getX() + ", " + getY() + " --" + getWidth() + " " + getHeight());
		}

		@Override
		public void kill() {
			super.kill();
			final Dude explosion = new Dude(this, EggGame.R.animatedSprite("explosion"), new AABB(physics.getAABB()));
			explosion.setName("explosion");
			explosion.getSprite().play("explode");
			explosion.getSprite().onLoop(new Runnable() {
				@Override
				public void run() {
					explosion.kill();
				}
			});
			
			Dude deadBody = new Dude(this, EggGame.R.animatedSprite("enemy"), new AABB(physics.getAABB()));
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
		
		// NOTE: pos is actual center for circles, but bottom left for AABBs
		// keep that in mind when drawing sprites
		
	}
	
	@Override
	public void init(EggGame game) {
		super.init(game);
		Gdx.input.setInputProcessor(new DemoInput(this));
		
		TileMapRendererLoader loader = new TileMapRendererLoader(new InternalFileHandleResolver());
		TileMapParameter param = new TileMapParameter(Resources.levelImageRoot, 1, 1);
		
		tmr = loader.load(EggGame.R.getInternal(), Resources.levelRootBaked + "testLevel.tmx", param);
		
		bf = EggGame.R.font("motorwerk16");
	}
	
	@Override
	public void show() {
		super.show();
		
		left = 25;
		
		//*
		for(int i = 0; i < left; ++i) {
			
			Blob blob = new Blob();
			blob.setX(MathUtils.random(x0 + 250, x1));
			blob.setY(MathUtils.random(y0 + 180, y1));
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
		
		Block b = new Block(EggGame.R.spriteAsAnimatedSprite("crate"), new Vector2(120, 220), new Vector2(64, 64));
		//addDude(b);
		
		float csize = 128;
		Block floor = new Block(
				EggGame.R.spriteAsAnimatedSprite("crate"),
				new Vector2(120, 240), new Vector2(csize, csize));
		addDude(floor);
		
		Dude box = new Dude(EggGame.R.spriteAsAnimatedSprite("crate"), new AABB(300, 230, 32, 32));
		box.physics.velocity.x = -80.0f;
		box.physics.interactive = true;
		addDude(box);
		
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
				cam.position.x - cam.viewportWidth / 2 + 20, cam.position.y - cam.viewportHeight / 2 + 32);
		
		bf.draw(sb, "FPS " + Gdx.graphics.getFramesPerSecond(), 
				cam.position.x + cam.viewportWidth / 2 - 100, cam.position.y - cam.viewportHeight / 2 + 32);
		sb.end();
	}
	
	public int getScale() {
		return scale;
	}
}
