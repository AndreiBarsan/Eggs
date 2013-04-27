package com.siegedog.eggs.physics;

import com.badlogic.gdx.math.Vector2;

public class PointShape extends Shape {

	public Vector2 position = new Vector2();
	
	public PointShape(float x, float y) {
		position.set(x, y);
	}
	
	public PointShape(Vector2 vec) {
		position.set(vec);
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public void setX(float x) {
		position.x = x;
	}

	@Override
	public void setY(float y) {
		position.y = y;
	}

	@Override
	public Vector2 getDimensions() {
		return Vector2.Zero.cpy();
	}

	@Override
	public void setDimensions(float w, float h) {
		// nop
	}

	@Override
	protected Collision intersectsCircle(Circle circle) {
		// TODO: maybe support stuff like circle / aabb contains point
		return null;
	}

	@Override
	protected Collision intersectsAABB(AABB aabb) {
		return null;
	}

}
