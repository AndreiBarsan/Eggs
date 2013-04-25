package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class AABB extends Shape {
	public Vector2 min;
	public Vector2 max;
	
	public AABB(Vector2 min, Vector2 max) {
		this.min = min;
		this.max = max;
	}
	
	public AABB(float xmin, float ymin, float xmax, float ymax) {
		min = new Vector2(xmin, ymin);
		max = new Vector2(xmax, ymax);
	}
	
	public Vector2 getCenter() {
		return new Vector2(min.x + (max.x - min.x) / 2, min.y + (max.y - min.y) / 2);
	}
	
	@Override
	protected Collision intersectsAABB(AABB other) {
		
	 if(	this.max.x < other.min.x || this.min.x > other.max.x
		|| 	this.max.y < other.min.y || this.min.y > other.max.y) 
	 {
		 return null;
	 }
	 
	 Vector2 normal = new Vector2(
			 other.getCenter().x - this.getCenter().x,
			 other.getCenter().y - this.getCenter().y);
	 float penetration = normal.len();
	 return new Collision(normal.nor(), penetration);
	}
	
	@Override
	protected Collision intersectsCircle(Circle other) {
		throw new Error("Circle vs AABB not supported yet");
	}
}
