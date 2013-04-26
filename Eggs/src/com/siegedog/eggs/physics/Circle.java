package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class Circle extends Shape {
	public float radius;
	public float x;
	public float y;
		
	public Circle(Vector2 pos, float radius) {
		this(pos.x, pos.y, radius);
	}
	
	public Circle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	@Override
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}
	
	@Override
	protected Collision intersectsCircle(Circle other) {
		float r = radius + other.radius;
		r *= r;
		if(  r < ((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))) {
			return null;
		}
		
		Vector2 v = new Vector2(other.x - x, other.y - y);
		float penetration = v.len();
		
		return new Collision(v.nor(), penetration);				
	}

	@Override
	protected Collision intersectsAABB(AABB other) {
		throw new Error("No Circle vs AABB yet");
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public Vector2 getDimensions() {
		return new Vector2(radius, radius);
	}

	@Override
	public void setDimensions(float w, float h) {
		assert w == h : "Width and height must always be the same for circles - ellipses not supported.";
		radius = w;
	}
}
