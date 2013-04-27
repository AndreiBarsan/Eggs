package com.siegedog.eggs.entities;

import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.physics.Circle;

public abstract class Bouncie extends Dude {
	
	public Bouncie(AnimatedSprite sprite, Vector2 pos, float radius) {
		super(sprite, new Circle(pos, radius));
		physics.interactive = true;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		bounceOnEdges();
	}

	private void bounceOnEdges() {
		float x0 = 0;
		float y0 = 0;
		float x1 = x0 + screen.getStage().getWidth();
		float y1 = y0 + screen.getStage().getHeight();
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
	}
}
