package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.math.Segment;
import com.siegedog.eggs.physics.PointShape;

public class Ray extends Dude {

	public Ray(Segment s) {
		super(EggGame.R.spriteAsAnimatedSprite("ray"), new PointShape(s.start));
		setRotation(90 + MathUtils.radDeg * (float)Math.atan2(s.end.y - s.start.y, s.end.x - s.start.x));
		float sl = s.length();
		setScaleY(sl);
		setOrigin(0.0f, 0.0f);
		setX(s.start.x + (-s.start.x + s.end.x) / 2.0f);
		setY(s.start.y + (-s.start.y + s.end.y) / 2.0f);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setScale(getScaleX(), getScaleY());
		super.draw(batch, parentAlpha);
	}

}
