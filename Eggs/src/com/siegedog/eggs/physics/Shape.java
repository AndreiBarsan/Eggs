package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public abstract class Shape {
	
	public final Collision intersects(Shape shape) {
		if(shape instanceof Circle) {
			return intersectsCircle((Circle)shape);
		}
		else {
			return intersectsAABB((AABB)shape);
		}
		
	}
	
	public abstract Vector2 getPosition();
	
	public abstract float getX();
	public abstract float getY();
	
	public abstract void setX(float x);
	public abstract void setY(float y);
	
	protected abstract Collision intersectsCircle(Circle circle);
	protected abstract Collision intersectsAABB(AABB aabb);
}
