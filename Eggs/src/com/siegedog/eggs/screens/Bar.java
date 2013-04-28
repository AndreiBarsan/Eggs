package com.siegedog.eggs.screens;

import com.badlogic.gdx.graphics.g2d.NinePatch;
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
	NinePatch np;
	
	public Bar(Sprite frame, Sprite progress, Vector2 pos) {
		super(new AnimatedSprite(frame), new AABB(pos.x, pos.y, frame.getWidth(), frame.getHeight()));
		this.progress = progress; 
		
		np = new NinePatch(progress, 4, 4, 4, 4);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		float w =  percent * sprite.getWidth();
		
		if(w > 8) {
			np.draw(batch, getX(), getY() + 2, w, sprite.getHeight() - 3.0f);
		}
		
		
		// Draw the frame normally
		super.draw(batch, parentAlpha);
	}

}
