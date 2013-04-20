package com.siegedog.eggs.physics;

public class Circle {
	public float radius;
	public float x;
	public float y;
	
	public boolean intersects(Circle other) {
		float r = radius + other.radius;
		r *= r;
		return r < ((x + other.x) * (x + other.x) +
					(y + other.y) * (y + other.y));
	}
}
