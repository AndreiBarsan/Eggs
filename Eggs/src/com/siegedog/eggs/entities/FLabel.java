package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.physics.AABB;
import com.siegedog.eggs.physics.PointShape;

/**
 * A floating label. It's an actor that renders itself as text.
 */
public class FLabel extends Dude {

	public String message;
	public BitmapFont font;
	public Vector2 velocity;
	
	public HAlignment alignment = HAlignment.CENTER;
	
	private boolean timed;
	private float lifespan;
	private float lifespanLeft;
	
	private float wrapWidth;
	
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
		if(lifespan == 0.0f) {
			timed = false;
		}  else {
			timed = true;
			this.lifespan = lifespan;
			this.lifespanLeft = this.lifespan;
		}
		
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
		if(wrapWidth != 0.0f) {
			font.drawWrapped(batch, message, getX(), getY(), wrapWidth, alignment);
		}
		else {
			font.drawMultiLine(batch, message, getX(), getY());
		}
	}

}
