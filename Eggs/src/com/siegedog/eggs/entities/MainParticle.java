package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.EggGame;

public class MainParticle extends Bouncie {

	public static final float RADIUS = 32.0f;
	
	private int value;
	private BitmapFont valueFont;
	
	public MainParticle(Vector2 position, int value) {
		super(EggGame.R.spriteAsAnimatedSprite("mainParticle"), position, RADIUS);
		this.value = value;
		valueFont = EggGame.R.font("motorwerk24");
	}

	public float getValue() {
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
		valueFont.drawWrapped(batch, String.valueOf(value), getX(), getY() + getHeight() / 2.0f + 12, getWidth(), HAlignment.CENTER);
	}
}
