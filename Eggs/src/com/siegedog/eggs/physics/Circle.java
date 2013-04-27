package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class Circle extends Shape {
	public float radius;
	public float x;
	public float y;
	
	public Circle(Circle other) {
		x = other.x;
		y = other.y;
		radius = other.radius;
	}
	
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
		float d2 = ((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
		float r2 = r * r;
		if(r2 < d2) {
			return null;
		}
		
		Vector2 v = new Vector2(other.x - x, other.y - y);
		
		float d = (float)Math.sqrt(d2); 
		if(Math.abs(d) < 0.0001) {
			// Circles are overlapping
			return new Collision(new Vector2(1.0f, 0.0f), radius);
		}
		else {
			Vector2 normal = new Vector2(v).div(d);
			return new Collision(normal, r - d);
		}				
	}

	@Override
	protected Collision intersectsAABB(AABB other) {
		return Shape.AABBvsCircle(other, this);
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
		return new Vector2(radius * 2, radius * 2);
	}

	@Override
	public void setDimensions(float w, float h) {
		assert w == h : "Width and height must always be the same for circles - ellipses not supported.";
		radius = w;
	}
	
	@Override
	public Circle copy() {
		return new Circle(this);
	}
}
