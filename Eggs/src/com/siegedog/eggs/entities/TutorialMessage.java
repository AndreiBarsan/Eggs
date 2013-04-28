package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.math.Segment;
import com.siegedog.eggs.screens.GameScreen;

public class TutorialMessage extends FLabel {

	public Bouncie pointsAt;
	public int pointsAtIndex;
	private Ray ray;
	
	public TutorialMessage(String message, BitmapFont font, Vector2 position, int pointsAtIndex, int width) {
		super(message, font, position, new Vector2(), width, 0);
		this.pointsAtIndex = pointsAtIndex;
		relativeToCamera = true;
		// Temporary "point to" 0, 0 before being initialized properly
		ray = new Ray(new Segment(position.x, position.y, 0, 0));
		ray.setVisible(false);
	}
	
	@Override
	public void init(GameScreen screen) {
		screen.addDude("rays", ray);
		super.init(screen);
	}
	
	@Override
	public void act(float delta) {
		if(isDead()) {
			return;
		}
		
		if(pointsAt != null) {
			ray.setVisible(true);
			ray.segment.end.set(pointsAt.getX() + pointsAt.getWidth() / 2.0f,
				pointsAt.getY() + pointsAt.getHeight() / 2.0f);
		}
		
		super.act(delta);
	}
	
	@Override
	public void kill() {
		super.kill();
		ray.kill();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		//ray.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}

}
