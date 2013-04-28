package com.siegedog.eggs.entities;

import com.badlogic.gdx.math.MathUtils;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.math.Segment;
import com.siegedog.eggs.physics.PointShape;

public class Ray extends Dude {

	public Segment segment;
	
	public Ray(Segment s) {
		super(EggGame.R.spriteAsAnimatedSprite("ray"), new PointShape(s.start));
		segment = s;
		
		
	}
	
	@Override
	public void act(float delta) {
		Segment s = segment;
		setRotation(90 + MathUtils.radDeg * (float)Math.atan2(s.end.y - s.start.y, s.end.x - s.start.x));
		float sl = s.length();
		setScaleY(sl);
		setScaleX(1.0f);
		setOrigin(0.0f, 0.0f);
		setX(s.end.x);
		setY(s.end.y);
		
		super.act(delta);
	}
}
