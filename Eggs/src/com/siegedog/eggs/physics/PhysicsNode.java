package com.siegedog.eggs.physics;

import static java.lang.Math.min;

import com.badlogic.gdx.math.Vector2;

public class PhysicsNode {

	private Shape shape;
	
	public Vector2 velocity = new Vector2();
	
	public float restitution = 1.0f;
	public boolean interactive = true;
	
	private float mass = 1.0f;
	private float invMass = 1 / mass;
	
	public PhysicsNode(Shape shape) {
		this.shape = shape;
	}
	
	public void update(float delta) {
		shape.setX(shape.getX() + velocity.x * delta);
		shape.setY(shape.getY() + velocity.y * delta);
	}
	
	public Collision intersect(PhysicsNode other) {
		return shape.intersects(other.shape);
	}
	
	public void resolveCollision(Collision col, PhysicsNode other) {
		Vector2 relativeVelocity = other.velocity.cpy().sub(velocity);
		float velAlongNormal = relativeVelocity.dot(col.normal);
		
		if(velAlongNormal > 0) {
			return;
		}
		
		float e = min(this.restitution, other.restitution);
		float j = -(1 + e) * velAlongNormal;
		j /= (this.invMass + other.invMass);
		
		Vector2 impulse = col.normal.cpy().mul(j);
		Vector2 thisSpd = impulse.cpy().mul(this.invMass);
		Vector2 otherSpd = impulse.cpy().mul(other.invMass);
		
		this.velocity.sub(thisSpd);
		other.velocity.add(otherSpd);
	}
	
	public void setX(float x) {
		shape.setX(x);
	}
	
	public void setY(float y) {
		shape.setY(y);
	}
	
	public float getX() {
		return shape.getX();
	}
	
	public float getY() {
		return shape.getY();
	}
	
	public Vector2 getDimensions() {
		return shape.getDimensions();
	}
	
	public void setDimensions(Vector2 dim) {
		setDimensions(dim.x, dim.y);
	}
	
	public void setDimensions(float x, float y) {
		shape.setDimensions(x, y);
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getInvMass() {
		return invMass;
	}
	
	public void setMass(float value) {
		this.mass = value;
		this.invMass = 1 / this.mass;
	}
}
