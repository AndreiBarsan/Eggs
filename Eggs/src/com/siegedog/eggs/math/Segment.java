package com.siegedog.eggs.math;

import com.badlogic.gdx.math.Vector2;

public class Segment {
	public Vector2 start;
	public Vector2 end;
	public Segment(Vector2 start, Vector2 end) {
		this.start = start;
		this.end = end;
	}
	
	public float length() {
		return new Vector2(end).sub(start).len();
	}
}