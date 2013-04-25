package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class Collision {
	public Vector2 normal;
	public float penetration;
	
	public Collision(Vector2 normal, float penetration) {
		this.normal = normal;
		this.penetration = penetration;
	}
	
}
