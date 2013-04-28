package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.physics.PhysicsNode;
import com.siegedog.eggs.physics.PointShape;
import com.siegedog.eggs.physics.Shape;
import com.siegedog.eggs.screens.GameScreen;
import com.siegedog.eggs.util.Log;

public class Dude extends Actor {
	protected AnimatedSprite sprite;
	protected GameScreen screen;
	protected String label = null;
	
	private boolean dead = false;
	
	public PhysicsNode physics;
	
	public Runnable onDeath = null;
	
	public Dude(Actor other, AnimatedSprite sprite) {
		this(other, sprite, new PointShape(new Vector2()));
	}
	
	public Dude(Actor other, AnimatedSprite sprite, Shape shape) {
		this(sprite, shape);
		setX(other.getX());
		setY(other.getY());
		//setOrigin(other.getOriginX(), other.getOriginY());
		
		setWidth(other.getWidth());
		setHeight(other.getHeight());
	}
	
	public Dude(AnimatedSprite sprite, Shape shape) {
		if(sprite != null) {
			this.sprite = new AnimatedSprite(sprite);
		}
		setTouchable(Touchable.disabled);
		
		physics = new PhysicsNode(shape);
		physics.interactive = false;
	}
	
	public void init(GameScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public void act(float delta) {
		// I... am... deeply sorry, ye mighty gods of programming
		super.setX(physics.getX() - physics.getDimensions().x / 2.0f);
		super.setY(physics.getY() - physics.getDimensions().y / 2.0f);
		super.setWidth(physics.getDimensions().x);
		super.setHeight(physics.getDimensions().y);
		
		super.act(delta);

		//setOrigin(physics.getDimensions().x / 2.0f, physics.getDimensions().y / 2.0f);
		
		if(sprite != null) {
			sprite.update(delta);
		}
		
		physics.update(delta);
	}
	
	public void kill() {
		if(isDead()) {
			Log.E("Killed an enemy twice. Look for bugs!");
		}
		dead = true;
		
		if(onDeath != null) {
			onDeath.run();
		}
		
		System.out.println("Removing: " + this);
		if(! remove()) {
			Log.E("Failed removing actor.");
		}
	}
	
	public void beforeCollision() {
		// TODO: maybe remove the need for this hook
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(dead) {
			return;
		}
		
		super.draw(batch, parentAlpha);

		if(sprite != null) {
			sprite.setPosition(getX(), getY());
			//sprite.setPosition(getX(), getY());
			//sprite.setOrigin(getOriginX(), getOriginY());
			sprite.setRotation(getRotation());
			sprite.draw(batch);
		}
	}
	
	@Override
	public void setX(float x) {
		physics.setX(x);
	}
	
	@Override
	public void setY(float y) {
		physics.setY(y);
	}
	
	@Override
	public void setBounds(float x, float y, float width, float height) {
		physics.setX(x);
		physics.setY(y);
		physics.setDimensions(width, height);
	}
	
	@Override
	public void setPosition(float x, float y) {
		physics.setX(x);
		physics.setY(y);
	}
	
	public void setDimensions(Vector2 dim) {
		physics.setDimensions(dim);
	}
	
	public float getWidth() {
		return physics.getDimensions().x;
	}
	
	public float getHeight() {
		return physics.getDimensions().y;
	}
	
	@Override
	public float getRight() {
		return getX() + getWidth();
	}
	
	@Override
	public float getTop() {
		return getY() + getHeight();
	}
		
	public boolean isDead() {
		return dead;
	}
	
	public AnimatedSprite getSprite() {
		return sprite;
	}

	public void setSprite(AnimatedSprite sprite) {
		this.sprite = sprite;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}