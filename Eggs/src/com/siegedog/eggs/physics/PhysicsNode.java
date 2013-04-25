package com.siegedog.eggs.physics;

public class PhysicsNode {

	public enum ShapeType {
		AABB,
		Circle
	}
	
	private ShapeType shape;
	private Circle circle;
	private AABB aabb;
	
	public float restitution = 1.0f;
	
	public PhysicsNode(Circle circle) {
		this.circle = circle;
		shape = ShapeType.Circle;
	}
	
	public PhysicsNode(AABB aabb) {
		this.aabb = aabb;
		shape = ShapeType.AABB;
	}
	
	public void update(float delta) {
		
	}
	
	public ShapeType getShapeType() {
		return shape;
	}
}
