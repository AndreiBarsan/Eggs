package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.physics.AABB;
import com.siegedog.eggs.physics.PointShape;

/**
 * A floating label. It's an actor that renders itself as text.
 */
public class FLabel extends Dude {

	public String message;
	public BitmapFont font;
	public Vector2 velocity;
	
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
		this.velocity = velocity;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(timed) {
			lifespanLeft -= delta;
			if(lifespanLeft <= 0.0f) {
				kill();
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		font.setColor(getColor());
		if(wrapWidth != 0.0f) {
			font.drawWrapped(batch, message, getX(), getY(), wrapWidth, HAlignment.CENTER);
		}
		else {
			font.drawMultiLine(batch, message, getX(), getY());
		}
	}

}
