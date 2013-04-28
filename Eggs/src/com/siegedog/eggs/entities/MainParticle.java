package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.EggGame;

public class MainParticle extends Bouncie {

	public enum PState {
		Spawning,	// When fading in at the beginning
		Normal,		// When bouncing around
		Merging		// When about to fuse with another particle
	}
	
	public static final float RADIUS = 32.0f;
	
	private int value;
	private BitmapFont valueFont;
	
	public MainParticle(MainParticle other) {
		this(other.physics.getPosition(), other.physics.velocity, other.value);
	}
	
	public MainParticle(Vector2 position, Vector2 velocity, int value) {
		super(EggGame.R.spriteAsAnimatedSprite("mainParticle"), position, RADIUS);
		this.value = value;
		this.physics.velocity.set(velocity);
		valueFont = EggGame.R.font("motorwerk24");
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		Color tint = new Color((value * 2) / 255.0f, 0.0f, 0.0f, 1.0f);
		sprite.setColor(tint);
		super.draw(batch, parentAlpha);
		valueFont.setColor(getColor());		
		valueFont.drawWrapped(batch, String.valueOf(value), getX(), getY() + getHeight() / 2 + 12, getWidth(), HAlignment.CENTER);
	}
	
	@Override
	public MainParticle copy() {
		return new MainParticle(this);
	}
}
