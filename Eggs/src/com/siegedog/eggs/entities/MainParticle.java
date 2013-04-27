package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.EggGame;

public class MainParticle extends Particle {

	public static final float RADIUS = 24.0f;
	
	private float value;
	private BitmapFont valueFont;
	
	public MainParticle(Vector2 position, float value) {
		super(position, RADIUS);
		this.value = value;
		
		valueFont = EggGame.R.font("motorwerk16");
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		valueFont.draw(batch, String.valueOf(value), getX(), getY());
	}
}
