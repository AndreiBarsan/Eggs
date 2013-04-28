package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.physics.Circle;
import com.siegedog.eggs.physics.PhysicsNode;

/**
 * These dudes can be combined with other nodes to lower or increase their
 * values, without affecting the overall instability.
 * 
 * @author SiegeDog
 *
 */
public class Tron extends Bouncie {

	static final float RADIUS = 24.0f; 
	public int value;
	
	private Color posColor = new Color(0.2f, 0.95f, 0.2f, 1.0f);
	private Color negColor = new Color(0.2f, 0.1f, 0.69f, 1.0f);
	
	/** To be used only when defining levels */
	public Tron(Vector2 pos, Vector2 vel, int val) {
		this(new PhysicsNode(new Circle(pos.x, pos.y, RADIUS)), val);
		physics.velocity.set(vel);
	}
	
	
	public Tron(Tron other) {
		this(other.physics, other.value);
	}
	
	private Tron(PhysicsNode phy, int val) {
		super(EggGame.R.spriteAsAnimatedSprite("tron"), phy, RADIUS);
		value = val;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(value > 0) {
			setColor(posColor);
		} else {
			setColor(negColor);
		}
		
		smallValueFont.setColor(Color.WHITE);	
		String sgn = value > 0 ? "+" : "";
		smallValueFont.drawWrapped(batch, sgn + String.valueOf(value), getX() - 10, getY() + getHeight() / 2 + 8, getWidth() + 20, HAlignment.CENTER);
	}

	@Override
	public Tron copy() {
		return new Tron(this);
	}

}
