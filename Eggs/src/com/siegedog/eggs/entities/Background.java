package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.physics.PointShape;

public class Background extends Dude {

	private Sprite sprite;
	
	public Background(Sprite sprite) {
		super((AnimatedSprite)null, new PointShape(0.0f, 0.0f));
		this.sprite = sprite;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
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
		
		
		for(int i = (x0 / sw) * sw; i < (x1 / sw + 1) * sw; i += sw ) {
			for(int j = (y0 / sh) * sh; j < (y1 / sh + 1) * sh; j += sh) {
				sprite.setPosition(i, j);
				sprite.draw(batch);
			}
		}
	}
}
