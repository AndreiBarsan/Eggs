package com.siegedog.eggs.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.physics.Circle;
import com.siegedog.eggs.physics.PhysicsNode;
import com.siegedog.eggs.screens.Title1951;

public class MainParticle extends Bouncie {

	public static final float RADIUS = 32.0f;
	
	private int value;
	private BitmapFont valueFont;
	private BitmapFont guiFont;
	
	/** To be used only when defining levels */
	public MainParticle(Vector2 pos, Vector2 vel, int val) {
		this(new PhysicsNode(new Circle(pos, RADIUS)), val);
		physics.velocity.set(vel);
		physics.restitution = 0.75f;
	}
	
	public MainParticle(MainParticle other) {
		this(other.physics, other.value);
	}
	
	public MainParticle(PhysicsNode phy, int value) {
		super(EggGame.R.spriteAsAnimatedSprite("mainParticle"), phy, RADIUS);
		this.value = value;
		valueFont = EggGame.R.font("motorwerk24");
		guiFont = EggGame.R.font("motorwerk32");
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(potato) {
			setColor(Color.WHITE.cpy());
		} else {
			Color tint = new Color((value * 2) / 255.0f, 0.0f, 0.0f, getColor().a);
			setColor(tint);
		}
		super.draw(batch, parentAlpha);
		
		valueFont.setColor(Color.WHITE);		
		valueFont.drawWrapped(batch, String.valueOf(value), getX(), getY() + getHeight() / 2 + 12, getWidth(), HAlignment.CENTER);
	}
	
	@Override
	protected void onMergeComplete() {
		super.onMergeComplete();
		
		if(mergeTarget instanceof MainParticle) {
			MainParticle mpt = (MainParticle)mergeTarget;
			int dif = Math.abs(this.getValue() - mpt.getValue());
			((Title1951)screen).instability += dif;
			value = (value + mpt.value) / 2;
			
			Vector2 labelPos = new Vector2(
					Math.min(getX(), mpt.getX()) + Math.abs(getX() - mpt.getX()) / 2,
					Math.min(getY(), mpt.getY()) + Math.abs(getY() - mpt.getY()) / 2 + 20.0f);
			
			Vector2 fspeed = new Vector2(0.0f, -20.0f);
			screen.addDude("overlay", 
					new FLabel("+" + dif, guiFont, labelPos, fspeed, 100, 1.2f));
		}
		
		freshlyMerged();
	}
	
	@Override
	protected void freshlyMerged() {
		state = PState.Normal;
		physics.interactive = true;
		this.setTouchable(Touchable.enabled);
	}
	
	@Override
	public MainParticle copy() {
		return new MainParticle(this);
	}
}
