package com.siegedog.eggs.screens;

import java.text.Format;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.GameInputHandler;
import com.siegedog.eggs.LevelData;
import com.siegedog.eggs.Levels;
import com.siegedog.eggs.entities.Background;
import com.siegedog.eggs.entities.Bouncie;
import com.siegedog.eggs.entities.Dude;
import com.siegedog.eggs.entities.FLabel;
import com.siegedog.eggs.entities.Ray;
import com.siegedog.eggs.math.Segment;


public class Title1953 extends GameScreen {

	public enum State {
		TitleShown,
		StartingLevel,
		Gameplay,
		EndingLevel,
		GameOver,
		LevelWon
	}
	
	public State state = State.TitleShown;
	
	BitmapFont splashFont = EggGame.R.font("motorwerk128");
	BitmapFont guiFont = EggGame.R.font("motorwerk32");
	BitmapFont small = EggGame.R.font("motorwerk24");
	
	FLabel instabilityIndicator;
	FLabel timeIndicator;
	
	FLabel splash;
	FLabel tap;
	FLabel al;
	
	FLabel winner;
	FLabel loseMessage;
	FLabel statReport;
	
	FLabel continueLabel;
	FLabel retryLabel;
	
	public int instability;
	public int currentLevel = 0;
	public float timeLeft;
	public LevelData levelData;
	
	String action;
	
	public boolean continueEnabled = false;
	
	public void beginLevel() {
		currentLevel++;
		restartLevel();
	}
	
	public void restartLevel() {
		state = State.Gameplay;
		levelData = Levels.levels[currentLevel];
		instability = 0;
		timeLeft = levelData.time;
		layers.get("main").clear();
		for(Bouncie b : levelData.entities) {
			addDude(b.copy());
		}
	}
	
	public void winLevel() {
		state = State.EndingLevel;
		stage.addAction(Actions.delay(1.0f, new Action() {
			public boolean act(float delta) {
				showStats();
				// TODO: show winner message
				return true;
			}
		}));
	}
	
	public void loseLevel() {
		state = State.EndingLevel;
		stage.addAction(Actions.delay(1.0f, new Action() {
			public boolean act(float delta) {
				// TODO: make screen go white like an explosion
				showRetry();
				return true;
			}
		}));
	}
	
	public void showStats() {
		state = State.LevelWon;
		continueEnabled = false;
		Camera cam = stage.getCamera();
		float sry = cam.position.y + cam.viewportHeight / 2.0f - 150;
		statReport.setPosition(cam.position.x - Gdx.graphics.getWidth() * 1.5f, sry);
		statReport.addAction(Actions.moveTo(cam.position.x - cam.viewportWidth / 2.0f, sry, 1.0f, Interpolation.exp10In));
		statReport.message = "Stats: \nLevel: " + currentLevel + "\nRank: Whatevs";
		
		continueLabel.message = action + " to continue";
		prepareContinueLabel();
		// And await click / tap
	}
	
	public void showRetry() {
		state = State.GameOver;
		continueEnabled = false;
		Camera cam = stage.getCamera();
		float sry = cam.position.y + cam.viewportHeight / 2.0f - 150;
		loseMessage.setPosition(cam.position.x - Gdx.graphics.getWidth() * 1.5f, sry);
		loseMessage.addAction(Actions.moveTo(cam.position.x - cam.viewportWidth / 2.0f, sry, 1.0f, Interpolation.exp10In));
		
		continueLabel.message = action + " to retry";
		prepareContinueLabel();
		// And await click / tap
	}
	
	private void prepareContinueLabel() {
		Camera cam = stage.getCamera();
		float sry = cam.position.y + cam.viewportHeight / 2.0f - 150;
		continueLabel.setPosition(cam.position.x - Gdx.graphics.getWidth() / 2, sry - 250);
		continueLabel.getColor().a = 0.0f;
		continueLabel.addAction(Actions.sequence(
				Actions.delay(1.5f),
				Actions.fadeIn(1.0f, Interpolation.exp10),
				new Action() {
					public boolean act(float delta) {
						continueEnabled = true;
						return true;
					}
				}
				));
	}
	
	public void hideStats() {
		continueEnabled = false;
		Camera cam = stage.getCamera();
		float sry = cam.position.y + cam.viewportHeight / 2.0f - 150;
		statReport.addAction(Actions.moveTo(cam.position.x - cam.viewportWidth * 1.5f, sry, 1.0f, Interpolation.exp10In));
		continueLabel.addAction(Actions.sequence(
				Actions.fadeOut(1.0f, Interpolation.exp10),
				Actions.delay(0.5f, 
				new Action() {
					public boolean act(float delta) {
						beginLevel();
						return true;
					}
				})));
	}
	
	public void hideRetry() {
		continueEnabled = false;
		Camera cam = stage.getCamera();
		float sry = 100 + cam.position.y - cam.viewportHeight / 2.0f;
		loseMessage.addAction(Actions.moveTo(cam.position.x - Gdx.graphics.getWidth() * 1.5f, sry, 1.0f, Interpolation.exp10In));
		continueLabel.addAction(Actions.sequence(
				Actions.fadeOut(1.0f, Interpolation.exp10),
				Actions.delay(0.5f, 
				new Action() {
					public boolean act(float delta) {
						restartLevel();
						return true;
					}
				})));
	}
	
	public void hideTitle() {
		continueEnabled = false;
		splash.addAction(Actions.moveTo(0.0f,  Gdx.graphics.getHeight() + 100, 1.0f, Interpolation.exp10));
		tap.addAction(Actions.moveTo(-1000.0f, 350, 1.0f, Interpolation.exp10));
		al.addAction(Actions.moveTo(1000.0f, 60, 1.0f, Interpolation.exp10));
		state = State.StartingLevel;
		stage.addAction(Actions.sequence(Actions.delay(0.9f), Actions.run(new Runnable() {
			public void run() {
				beginLevel();
			}
		})));
	}
	
	public void showTitle() {
		continueEnabled = false;
		splash.addAction(Actions.moveTo(0.0f, 420.0f, 1.0f, Interpolation.exp10));
		tap.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f)
				));
		tap.getColor().a = 0.0f;
		al.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f),
				new Action() {
					public boolean act(float delta) {
						continueEnabled = true;
						return true;
					}
				}));
		al.getColor().a = 0.0f;
		
		state = State.TitleShown;
	}
	
	@Override
	public void show() {
		super.show();
		
		addDude("background", new Background(EggGame.R.sprite("background")));
		addDude("overlay", splash = new FLabel("1953", splashFont, new Vector2(0, Gdx.graphics.getHeight() + 100), Gdx.graphics.getWidth()));
		
		action = (Gdx.app.getType() == ApplicationType.Desktop) ? "Click" : "Tap";
		tap = new FLabel(action + " to start", guiFont, new Vector2(0, 320), Gdx.graphics.getWidth());
		addDude("overlay", tap);
		
		String about = "Andrei Barsan, 2013\nsiegedog.com ";
		al = new FLabel(about, small, new Vector2(0, 60), Gdx.graphics.getWidth());
		addDude("overlay", al);
		
		winner = new FLabel("Level complete!", splashFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", winner);
		
		loseMessage = new FLabel("Level failed!", splashFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", loseMessage);
		
		statReport = new FLabel("Stats: ", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", statReport);
		
		instabilityIndicator = new FLabel("", guiFont, new Vector2(10.0f, 10.0f), Gdx.graphics.getWidth());
		instabilityIndicator.alignment = HAlignment.LEFT;
		addDude("overlay", instabilityIndicator);
		
		timeIndicator = new FLabel("", guiFont, new Vector2(), Gdx.graphics.getWidth());
		timeIndicator.alignment = HAlignment.RIGHT;
		addDude("overlay", timeIndicator);
		
		continueLabel = new FLabel(action + " to continue", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", continueLabel);
		
		retryLabel = new FLabel(action + " to retry", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", retryLabel);
		
		showTitle();
		
		GameInputHandler ih = new GameInputHandler(this);
		Gdx.input.setInputProcessor(ih);
	}
	
	final int LIM2 = 180 * 180;
	
	@Override
	protected void pairwiseCheck(Dude d1, Dude d2) {
		float dx = d1.physics.getX() - d2.physics.getX();
		float dy = d1.physics.getY() - d2.physics.getY();
		Vector2 dir = new Vector2(dx, dy);
		if(dir.len2() < LIM2) {
			addDude("rays", new Ray(new Segment(d1.physics.getPosition(), d2.physics.getPosition())));
			if(d1 instanceof Bouncie && d2 instanceof Bouncie) {
				float rangle = 180.0f + dir.angle();
				if(rangle > 360.0f) rangle -= 360.0f;
				((Bouncie) d1).addNeighbor((Bouncie) d2, rangle);
				((Bouncie) d2).addNeighbor((Bouncie) d1, dir.angle()); // The opposite angle
			}
		}
	}
		
	@Override
	public void render(float delta) {
		super.render(delta);
		layers.get("rays").clear();
		Camera cam = stage.getCamera();
		if(state == State.Gameplay) {
			
			timeLeft -= delta;
			
			timeIndicator.setVisible(true);
			timeIndicator.message = String.format("Time: %.0f''", timeLeft);
			timeIndicator.setPosition(cornerLeftX() - 20, cornerLeftY() + 32);			
			
			instabilityIndicator.setVisible(true);
			instabilityIndicator.message = "Instability: " + instability + " / " + levelData.meltdownThreshold;
			instabilityIndicator.setPosition(cornerLeftX() + 8, cornerLeftY() + 32);
			
			if(instability >= levelData.meltdownThreshold || timeLeft <= 0) {
				loseLevel();
			} else if(layers.get("main").getChildren().size <= levelData.winCondition) {
				winLevel();
			}
		} else {
			timeIndicator.setVisible(false);
			instabilityIndicator.setVisible(false);
		}
	}
	
	private float cornerLeftX() {
		Camera cam = stage.getCamera();
		return cam.position.x - cam.viewportWidth / 2.0f;
	}
	
	private float cornerRightX() {
		Camera cam = stage.getCamera();
		return cam.position.x + cam.viewportWidth / 2.0f;
	}
	
	private float cornerLeftY() {
		Camera cam = stage.getCamera();
		return cam.position.y - cam.viewportHeight / 2.0f;
	}

	private float cornerRightY() {
		Camera cam = stage.getCamera();
		return cam.position.y + cam.viewportHeight / 2.0f;
	}
}
