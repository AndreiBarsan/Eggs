package com.siegedog.eggs.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.util.Log;

class Dude extends Actor {
	protected AnimatedSprite sprite;
	protected GameScreen screen;
	protected String label = null;
	
	private boolean dead = false;
	
	public Vector2 speed = new Vector2();
	
	public Dude(AnimatedSprite sprite) {
		this.sprite = new AnimatedSprite(sprite);
	}
	
	public Dude(Actor other, AnimatedSprite sprite) {
		setX(other.getX());
		setY(other.getY());
		setOrigin(other.getOriginX(), other.getOriginY());
		setWidth(other.getWidth());
		setHeight(other.getHeight());
		setZIndex(100);
		this.sprite = new AnimatedSprite(sprite);
		
		System.out.println(other);
	}
	
	public void init(GameScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public void act(float delta) {
		if(dead) {
			return;
		}
		
		super.act(delta);

		sprite.update(Gdx.graphics.getDeltaTime());
		
		setX(getX() + delta * speed.x);
		setY(getY() + delta * speed.y);
	}
	
	public void kill() {
		if(isDead()) {
			Log.E("Killed enemy twice. Look for bugs!");
		}
		dead = true;
		screen.signalDead(this);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(dead) {
			return;
		}
		
		super.draw(batch, parentAlpha);

		if(sprite != null) {
			sprite.setPosition(getX(), getY());
			sprite.setOrigin(getOriginX(), getOriginY());
			sprite.setRotation(getRotation());
			sprite.draw(batch);
		}
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