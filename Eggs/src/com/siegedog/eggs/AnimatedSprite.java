package com.siegedog.eggs;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class AnimatedSprite extends Sprite {

	private float delay, activeDelay;
	
	/// Whether the current animation should be looping
	private boolean looping = false;
	
	/// Action to be executed when the animation finishes
	private Runnable onLoop;
	
	/// Wheter the sprite is flipped
	public boolean flipped = false; 
	
	/// Animation timer
	private float time = 0f;
	
	private HashMap<String, DAnimation> animations = new HashMap<String, DAnimation>();
	private DAnimation activeAnimation;


	/**
	 *  Slightly improves the built-in animation class, by exposing the
	 *  frame count.
	 * @author SiegeDog
	 *
	 */
	public class DAnimation extends Animation {

		int frameCount;
		
		public DAnimation(float frameDuration, Array<TextureRegion> keyFrames) {
			super(frameDuration, keyFrames);
			frameCount = keyFrames.size;
		}
		
		public int getFrameCount() {
			return frameCount;
		}
		
	}
	

	public AnimatedSprite(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	public AnimatedSprite(Texture texture, int srcX, int srcY, int srcWidth,
			int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public AnimatedSprite(Texture texture, int srcWidth, int srcHeight) {
		super(texture, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public AnimatedSprite(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public AnimatedSprite(TextureRegion region, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		super(region, srcX, srcY, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public AnimatedSprite(TextureRegion region) {
		super(region);
		// TODO Auto-generated constructor stub
	}

	public void addAnimationByFrameCount(String name, int frames, float frameTime) {
		addAnimationByFrameCount(name, frames, frameTime, 0f);
	}
	
	// most animations are just subsequent texture coords of the current one
	public void addAnimationByFrameCount(String name, int frames, float frameTime, float delay) {
		Array<TextureRegion> keyFrames = new Array<TextureRegion>();
		
		this.delay = delay;
		int w = (int) getWidth();
		int h = (int) getHeight();
		Texture texture = getTexture();
		
		for(int i = 0; i<frames; i++) {
			keyFrames.add(new TextureRegion(texture, getRegionX() + i * w, getRegionY() , w, h));
		}
		
		animations.put(name, new DAnimation(frameTime, keyFrames));
	}

	public void addAnimation(String name, DAnimation animation) {
		animations.put(name, animation);
	}

	public void play(String name) {
		if (animations.containsKey(name)) {
			
			if(activeAnimation != animations.get(name)) {
				time = 0f;
				activeAnimation = animations.get(name);
			}
		}
	}
	
	public void stop() {
		activeAnimation = null;
	}
	
	public void setPosition(Vector2 p) {
		setPosition(p.x, p.y);
	}

	public void setPlayMode(int mode) {
		activeAnimation.setPlayMode(mode);
	}

	public void onLoop(Runnable onLoop) {
		this.onLoop = onLoop;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public void update(float delta) {
		
		activeDelay-=delta;
		
		if(activeDelay <= 0f) {
			time += delta;
			
			if (activeAnimation != null) {
				TextureRegion tr = new TextureRegion(activeAnimation.getKeyFrame(time, true));			

				// TODO: reimplement on loop event
				setRegion(tr);
			}
		}
	}

}

