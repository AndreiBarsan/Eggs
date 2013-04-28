package com.siegedog.eggs.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.physics.Circle;
import com.siegedog.eggs.physics.PhysicsNode;

public abstract class Bouncie extends Dude {
	
	// There are multiple stuff you will be able to merge around - this state
	// management system is common to all of them
	
	public enum PState {
		Spawning,	// When fading in at the beginning
		Normal,		// When bouncing around
		Merging		// When about to fuse with another particle
	}
	public PState state;
	protected boolean potato = false;
	
	protected BitmapFont smallValueFont;
	protected BitmapFont valueFont;
	protected BitmapFont guiFont;
	
	static public final float NEIGHBOR_ANGLE_THRESHOLD = 20.0f;
	static public final float NORMAL_SPEED2 = 50.0f * 50.0f;
	static public final float MIN_SPEED2 = 35.0f * 35.0f;
	static public final float MERGE_THRESHOLD2 = 15.0f * 15.0f; 
	
	private Vector2 storedMove;
	public Bouncie mergeTarget = null;
	
	public static class Neighbor {
		public float angleTo;
		public Bouncie data;
		
		public Neighbor(float angleTo, Bouncie data) {
			this.angleTo = angleTo;
			this.data = data;
		}
	}
	
	protected ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>();
	
	public Bouncie(AnimatedSprite sprite, PhysicsNode op, float radius) {
		super(sprite, new Circle(op.getPosition(), radius));
		physics.velocity.set(op.velocity);
		physics.restitution = op.restitution;
		physics.setMass(op.getMass());
		
		spawnLogic();
	}
	
	private void spawnLogic() {
		smallValueFont = EggGame.R.font("motorwerk20");
		valueFont = EggGame.R.font("motorwerk24");
		guiFont = EggGame.R.font("motorwerk32");
		
		state = PState.Spawning;
		physics.interactive = false;
		storedMove = physics.velocity.cpy();
		physics.velocity.set(0.0f, 0.0f);
		
		this.setTouchable(Touchable.disabled);
		
		getColor().a = 0.0f;
		addAction(Actions.sequence(
			Actions.fadeIn(1.0f),
			Actions.delay(0.33f, new Action() {
				public boolean act(float delta) {
					startInteracting();
					return true;
				}
			})));
	}
	
	public void applyPulse(float x, float y) {
		physics.velocity.add(x, y);
		if(MathUtils.random(100) == 42) {
			potato = true;
			sprite = new AnimatedSprite(EggGame.R.spriteAsAnimatedSprite("potato"));
			setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
		}
	}
	
	private void startInteracting() {
		state = PState.Normal;
		
		physics.interactive = true;
		this.setTouchable(Touchable.enabled);
		physics.velocity.set(storedMove);
	}
	
	@Override
	public void beforeCollision() {
		// Make sure the old computed neighbors are cleaned
		clearNeighbors();
	}
	
	protected void onMergeComplete() {
		mergeTarget.kill();
		physics.velocity.mul(0.45f);
	}
	
	/** Called only once, for the bouncie the player started the flick on.	 */
	public void beginMergeWith(Bouncie target) {
		this.mergeTarget = target;
		target.mergeTarget = this;
		
		state = PState.Merging;
		physics.interactive = false;
		this.setTouchable(Touchable.disabled);
		
		target.state = PState.Merging;
		target.physics.interactive = false;
		target.setTouchable(Touchable.enabled);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(isDead()) {
			// Thank you libgdx for allowing such flexible actor management... </sarcasm>
			return;
		}
		
		if(potato) {
			setRotation(getRotation() + physics.velocity.len() / 2.0f * delta);
		}
		
		switch(state) {
		case Merging:
			Vector2 dirTarget = new Vector2( mergeTarget.getX(), mergeTarget.getY() );
			dirTarget.sub(getX(), getY());
			if(dirTarget.len2() < MERGE_THRESHOLD2) {
				onMergeComplete();
			} else {
				physics.velocity.set(dirTarget.nor().mul(150.0f));
			}
			break;
			
		case Normal:
			bounceOnEdges();
			
			if(physics.velocity.len2() > NORMAL_SPEED2) {
				physics.velocity.mul(0.9995f);
			} else if(physics.velocity.len2() < MIN_SPEED2) {
				physics.velocity.mul(1.0005f);
			}
			break;
			
		case Spawning:
			break;
		}
		
	}
	
	public void addNeighbor(Bouncie other, float angle) {
		neighbors.add(new Neighbor(angle, other));
	}
	
	public void clearNeighbors() {
		neighbors.clear();
	}
	
	public Bouncie tryFindFuse(float angleDeg) {
		float bestDiff = NEIGHBOR_ANGLE_THRESHOLD;
		Neighbor bestNeighbor = null;
		for(Neighbor n : neighbors) {
			float diff = Math.abs(n.angleTo - angleDeg);
			if(diff < bestDiff) {
				bestDiff = diff;
				bestNeighbor = n;
			}
		}
		
		return (bestNeighbor != null) ? bestNeighbor.data : null;
	}
	
	protected void freshlyMerged() {
		state = PState.Normal;
		physics.interactive = true;
		this.setTouchable(Touchable.enabled);
	}

	private void bounceOnEdges() {
		float x0 = screen.x0;
		float y0 = screen.y0;
		float x1 = screen.x1;
		float y1 = screen.y1;
		
		if(getX() < x0) {
			setX(x0 + getWidth() / 2.0f);
			physics.velocity.x = -physics.velocity.x;
		}
		
		if(getX() + getWidth() > x1) {
			setX(x1 - getWidth() / 2.0f);
			physics.velocity.x = -physics.velocity.x;
		}
		
		if(getY() < y0) {
			setY(y0 + getHeight() / 2.0f);
			physics.velocity.y = -physics.velocity.y;
		}
		
		if(getY() + getHeight() > y1) {
			setY(y1 - getHeight() / 2.0f);
			physics.velocity.y = -physics.velocity.y;
		}
	}
	
	public abstract Bouncie copy();
}
