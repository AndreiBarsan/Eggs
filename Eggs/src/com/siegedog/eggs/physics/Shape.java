package com.siegedog.eggs.physics;

public abstract class Shape {
	
	public final Collision intersects(Shape shape) {
		if(shape instanceof Circle) {
			return intersectsCircle((Circle)shape);
		}
		else {
			return intersectsAABB((AABB)shape);
		}
		
	}
	
	protected abstract Collision intersectsCircle(Circle circle);
	protected abstract Collision intersectsAABB(AABB aabb);
}
