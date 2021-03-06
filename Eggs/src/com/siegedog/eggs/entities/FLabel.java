package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.physics.PointShape;

/**
 * A floating label. It's an actor that renders itself as text.
 */
public class FLabel extends Dude {

	public String message;
	public BitmapFont font;
	public Vector2 velocity;
	
	public HAlignment alignment = HAlignment.CENTER;
	
	public boolean relativeToCamera = false;
	
	protected float wrapWidth;
	
	public FLabel(String message, BitmapFont font, Vector2 position) {
		this(message, font, position, new Vector2(0.0f, 0.0f));
	}
	
	public FLabel(String message, BitmapFont font, Vector2 position, Vector2 velocity) {
		this(message, font, position, velocity, 0.0f, 0.0f);
	}
	
	public FLabel(String message, BitmapFont font, Vector2 position, float width) {
		this(message, font, position, new Vector2(0.0f, 0.0f), width);
	}
	
	public FLabel(String message, BitmapFont font, Vector2 position, Vector2 velocity, float width) {
		this(message, font, position, velocity, width, 0.0f);
	}
	
	public FLabel(String message, BitmapFont font, Vector2 position, Vector2 velocity, float width, float lifespan) {
		super(null, new PointShape(position));
		
		this.message = message;
		this.font = font;
		this.velocity = velocity;
		
		this.wrapWidth = width;
		if(lifespan != 0.0f) {
			addAction(Actions.moveBy(0.0f, velocity.len() * lifespan, lifespan, Interpolation.exp5Out));
			addAction(Actions.sequence(
					Actions.delay(0.33f * lifespan),
					Actions.fadeOut(0.66f * lifespan),
					new Action() {
						
						@Override
						public boolean act(float delta) {
							kill();
							return true;
						}
					}));
		} else {
			addAction(Actions.moveBy(0.0f, velocity.len() * 5.0f));
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		font.setColor(getColor());
		float xx = getX();
		float yy = getY();
		
		if(relativeToCamera) {
			Camera cam = screen.getStage().getCamera();
			xx += cam.position.x - cam.viewportWidth / 2.0f;
			yy += cam.position.y - cam.viewportHeight / 2.0f;
		}
		
		if(wrapWidth != 0.0f) {
			font.drawWrapped(batch, message, xx, yy, wrapWidth, alignment);
		}
		else {
			font.drawMultiLine(batch, message, xx, yy);
		}
	}

}
