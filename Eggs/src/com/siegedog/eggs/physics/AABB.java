package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class AABB extends Shape {
	public Vector2 min;
	public Vector2 dimensions;
	
	public AABB(AABB other) {
		this.min = new Vector2(other.min);
		this.dimensions = new Vector2(other.dimensions);
	}
	
	public AABB(Vector2 min, Vector2 dimensions) {
		this.min = min;
		this.dimensions = dimensions;
	}
	
	public AABB(float xmin, float ymin, float width, float height) {
		min = new Vector2(xmin, ymin);
		dimensions = new Vector2(width, height);
	}
	
	public Vector2 getCenter() {
		return new Vector2(min.x + dimensions.x / 2.0f, min.y + dimensions.y / 2.0f);
	}
	
	public Vector2 getPosition() {
		return min;
	}
	
	@Override
	public void setX(float x) {
		min.x = x;
	}
	
	@Override
	public void setY(float y) {
		min.y = y;
	}
	
	@Override
	public float getX() {
		return min.x;
	}
	
	@Override
	public float getY() {
		return min.y;
	}
	
	@Override
	protected Collision intersectsAABB(AABB other) {
	 Vector2 n = new Vector2(other.getCenter()).sub(this.getCenter());
	 Vector2 normal = new Vector2();
	 float aex = this.dimensions.x / 2.0f;
	 float bex = other.dimensions.x / 2.0f;
	 
	 float x_overlap = aex + bex - Math.abs(n.x);
	 if(x_overlap > 0.0f) {
		 float aey = this.dimensions.y / 2.0f;
		 float bey = other.dimensions.y / 2.0f;
		 
		 float y_overlap = aey + bey - Math.abs(n.y);
		 
		 if(y_overlap > 0.0f) {
			 if(x_overlap < y_overlap) {
				 if(n.x < 0.0f) {
					 normal.set(-1.0f, 0.0f);
				 } else {
					 normal.set(1.0f, 0.0f);
				 }
				 
				 float penetration = x_overlap;
				 return new Collision(normal, penetration);
			 }
			 else {
				 if(n.y < 0.0f) {
					 normal.set(0.0f, -1.0f);
				 } else {
					 normal.set(0.0f, 1.0f);
				 }
				 
				 float penetration = y_overlap;
				 return new Collision(normal, penetration);
			 }
		 }
	 }
	
	 return null;
	}
	
	@Override
	protected Collision intersectsCircle(Circle other) {
		return Shape.AABBvsCircle(this, other);
	}
	
	@Override
	public AABB copy() {
		return new AABB(this);
	}

	@Override
	public Vector2 getDimensions() {
		return dimensions;
	}

	@Override
	public void setDimensions(float w, float h) {
		dimensions.set(w, h);
	}
	
	public void setDimensions(Vector2 dim) {
		dimensions.set(dim);
	}
}
