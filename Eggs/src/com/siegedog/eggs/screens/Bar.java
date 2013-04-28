package com.siegedog.eggs.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.entities.Dude;
import com.siegedog.eggs.physics.AABB;


public class Bar extends Dude {

	Sprite progress;
	/** 0..1, by the way */
	public float percent = 0.00f;
	
	public Bar(Sprite frame, Sprite progress, Vector2 pos) {
		super(new AnimatedSprite(frame), new AABB(pos.x, pos.y, frame.getWidth(), frame.getHeight()));
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		// Draw the frame normally
		super.draw(batch, parentAlpha);
	}

}
