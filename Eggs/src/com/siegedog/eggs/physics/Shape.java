package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.MathUtils;
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
	
	public abstract Vector2 getDimensions();
	public abstract void setDimensions(float w, float h);
	
	protected abstract Collision intersectsCircle(Circle circle);
	protected abstract Collision intersectsAABB(AABB aabb);
	
	protected static Collision AABBvsCircle(AABB A, Circle B) {
		
		Vector2 AtoB = B.getPosition().cpy().sub(A.getCenter());
		
		float xe = A.getDimensions().x / 2;
		float ye = A.getDimensions().y / 2;
		
		Vector2 closest = new Vector2(
				MathUtils.clamp(AtoB.x, -xe, xe),
				MathUtils.clamp(AtoB.y, -ye, ye)
			);
		
		boolean inside = false;
		if(AtoB.epsilonEquals(closest, (float) 1e-4)) {
			inside = true;
			
			if(Math.abs(AtoB.x) > Math.abs(AtoB.y)) {
				if(closest.x > 0.0f) {
					closest.x = xe;
				} else {
					closest.x = -xe;
				}
			}
			else {
				if(closest.y > 0.0f) {
					closest.y = ye;
				} else {
					closest.y = -ye;
				}
			}
		}
		
		Vector2 normal = new Vector2(AtoB).sub(closest);
		float d = normal.len2();
		float r = B.radius;
		
		// No collision
		if(d > r * r && !inside)  {
			return null;
		}
		
		d = (float) Math.sqrt(d);
		
		float penetration = r + d;
		if(inside) {
			normal.set(AtoB).mul(1.0f).nor();
		}
		else {
			normal.set(AtoB).mul(-1.0f).nor();
		}
		
		return new Collision(normal, penetration);
	}
}
