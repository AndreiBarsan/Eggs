package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {

	private Sprite sprite;
	
	public Background(Sprite sprite) {
		this.sprite = sprite;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		Camera cam = getStage().getCamera();
		float cx = cam.position.x;
		float cy = cam.position.y;
		float cw = cam.viewportWidth;
		float ch = cam.viewportHeight;
		
		int sw = (int) sprite.getWidth();
		int sh = (int) sprite.getHeight();
		
		int x0 = (int) (cx - cw / 2);
		int y0 = (int) (cy - ch / 2);
		
		int x1 = (int) (cx + cw / 2);
		int y1 = (int) (cy + ch / 2);
		
		
		for(int i = (x0 / sw) * sw; i < (x1 / sw) * sw; i+= sw ) {
			sprite.setPosition(i, 10);
			sprite.draw(batch);
		}
	}
}
