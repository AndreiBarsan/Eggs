package com.siegedog.eggs.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.physics.Circle;

public abstract class Bouncie extends Dude {
	
	static public final float NEIGHBOR_ANGLE_THRESHOLD = 20.0f;
	
	public static class Neighbor {
		public float angleTo;
		public Bouncie data;
		
		public Neighbor(float angleTo, Bouncie data) {
			this.angleTo = angleTo;
			this.data = data;
		}
	}
	
	@Override
	public void beforeCollision() {
		// Make sure the old computed neighbors are cleaned
		clearNeighbors();
	}
	
	protected ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>();
	
	public Bouncie(Bouncie other) {
		super(new AnimatedSprite(other.sprite), other.physics.getShape().copy());
		physics.interactive = true;
		this.setTouchable(Touchable.enabled);
	}
	
	public Bouncie(AnimatedSprite sprite, Vector2 pos, float radius) {
		super(sprite, new Circle(pos, radius));
		physics.interactive = true;
		this.setTouchable(Touchable.enabled);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		bounceOnEdges();
	}
	
	public void addNeighbor(Bouncie other, float angle) {
		neighbors.add(new Neighbor(angle, other));
	}
	
	public void clearNeighbors() {
		neighbors.clear();
	}
	
	public Bouncie tryFindFuse(float angleDeg) {
		float bestDiff = NEIGHBOR_ANGLE_THRESHOLD;
		Neighbor bestNeighbor = null;
		System.out.println("Trying to find fuse for swipe angle: DEG " + angleDeg);
		for(Neighbor n : neighbors) {
			float diff = Math.abs(n.angleTo - angleDeg);
			if(diff < bestDiff) {
				bestDiff = diff;
				bestNeighbor = n;
			}
			System.out.println("FAIL: " + n.angleTo);
		}
		
		return (bestNeighbor != null) ? bestNeighbor.data : null;
	}

	private void bounceOnEdges() {
		float x0 = 0;
		float y0 = 0;
		float x1 = x0 + screen.getStage().getWidth();
		float y1 = y0 + screen.getStage().getHeight();
		/*
		if(getX() < x0 + getWidth() / 2.0f) {
			setX(x0 + getWidth() / 2.0f);
			physics.velocity.x = -physics.velocity.x;
		}
		
		if(getX() + getWidth() / 2.0f > x1) {
			setX(x1 - getWidth() / 2.0f);
			physics.velocity.x = -physics.velocity.x;
		}
		
		if(getY() < y0 + getHeight() / 2.0f) {
			setY(y0 + getHeight() / 2.0f);
			physics.velocity.y = -physics.velocity.y;
		}
		
		if(getY() + getHeight() / 2.0f  > y1) {
			setY(y1 - getHeight() / 2.0f);
			physics.velocity.y = -physics.velocity.y;
		}*/
		
		if(getX() < x0) {
			setX(x0 + getWidth() / 2.0f);
			physics.velocity.x = -physics.velocity.x;
		}
		
		if(getX() + getWidth() > x1) {
			setX(x1 - getWidth() / 2.0f);
			physics.velocity.x = -physics.velocity.x;
		}
		
		if(getY() < y0) {
			setY(y0 + getHeight() / 2.0f);
			physics.velocity.y = -physics.velocity.y;
		}
		
		if(getY() + getHeight() > y1) {
			setY(y1 - getHeight() / 2.0f);
			physics.velocity.y = -physics.velocity.y;
		}
	}
	
	public abstract Bouncie copy();
}
