package com.siegedog.eggs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.physics.AABB;

public class Block extends Dude {

	public Block(Vector2 pos, Vector2 dim) {
		this(null, pos, dim);
	}
	
	public Block(AnimatedSprite sprite, Vector2 pos, Vector2 dim) {
		super(sprite, new AABB(pos, dim));
		physics.interactive = true;
		physics.setMass(0.0f);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {

		sprite.setOrigin(0.0f, 0.0f);
		sprite.setSize(physics.getDimensions().x, physics.getDimensions().y);
		sprite.setPosition(physics.getX(), physics.getY());
		sprite.draw(batch);		
		
	}

}
