package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class Circle extends Shape {
	public float radius;
	public float x;
	public float y;
		
	@Override
	protected Collision intersectsCircle(Circle other) {
		float r = radius + other.radius;
		r *= r;
		if(  r >= ((x + other.x) * (x + other.x) + (y + other.y) * (y + other.y))) {
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

}
