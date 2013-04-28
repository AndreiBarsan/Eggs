package com.siegedog.eggs.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.AnimatedSprite;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.GameInputHandler;
import com.siegedog.eggs.LevelData;
import com.siegedog.eggs.Levels;
import com.siegedog.eggs.entities.Background;
import com.siegedog.eggs.entities.Bouncie;
import com.siegedog.eggs.entities.Dude;
import com.siegedog.eggs.entities.FLabel;
import com.siegedog.eggs.entities.Ray;
import com.siegedog.eggs.entities.TutorialMessage;
import com.siegedog.eggs.math.Segment;
import com.siegedog.eggs.physics.AABB;
import com.siegedog.eggs.physics.PointShape;


public class Title1951 extends GameScreen {

	public enum State {
		TitleShown,		// Title screen is being shown
		StartingLevel,	// Small pause before stuff gets spawned
		Gameplay,		
		EndingLevel,	// Small pause before win / loss message is shown
		GameOver,		// Level failed
		LevelWon,		// Level finished - rank and whatnot
		FinishedGame,	// The final stats
	}
	
	public State state = State.TitleShown;
	
	String timeLabel = (Gdx.app.getType() == ApplicationType.Android) ? "T" : "Time";
	
	Dude grain;
	Dude flash;
	Bar instabar;
	
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
	
	FLabel beatGameMessage;
	FLabel beatGameStats;
	
	FLabel goal;
	
	public int instability;
	public int currentLevel = 2;
	public float timeLeft;
	public LevelData levelData;
	
	String action;
	
	Dude logo;
	
	public boolean continueEnabled = false;
	
	public void beginLevel() {
		currentLevel++;
		if(currentLevel > Levels.levels.size()) {
			finishedGame();
		} else {
			restartLevel();
		}
	}
	
	public void restartLevel() {
		state = State.Gameplay;
		levelData = Levels.levels.get(currentLevel - 1);
		instability = 0;
		timeLeft = levelData.time;
		layers.get("main").clear();
		Bouncie[] qr = new Bouncie[levelData.entities.size()];
		int index = 0;
		// Hacky index-based solution is meant to compensate for the fact that 
		// we can't define connections that easily since we're copying stuff
		for(Bouncie b : levelData.entities) {
			Bouncie d = b.copy();
			addDude(d);
			qr[index++] = d;
		}
		
		if(null != levelData.tuts) {
			for(TutorialMessage tut : levelData.tuts) {
				addDude("overlay", tut);
				tut.getColor().a = 1.0f;
				tut.setVisible(true);
				if(tut.pointsAtIndex != -1) {
					tut.pointsAt = qr[tut.pointsAtIndex];
				}
			}
		}
	}
	
	public void winLevel() {
		state = State.EndingLevel;
		hideTutorials();
		
		winner.setVisible(true);
		winner.setPosition(cornerLeftX(), topY() - 80);
		winner.addAction(Actions.fadeIn(0.33f));
		
		stage.addAction(Actions.delay(1.0f, new Action() {
			public boolean act(float delta) {
				showStats();
				return true;
			}
		}));
	}
	
	public void loseLevel() {
		state = State.EndingLevel;
		hideTutorials();
		
		flash.setX(cornerLeftX());
		flash.setY(cornerLeftY());
		flash.setVisible(true);
		flash.getColor().a = 0.0f;
		flash.addAction(Actions.sequence(
				Actions.fadeIn(0.15f),
				Actions.delay(1.50f),
				Actions.fadeOut(3.0f),
				Actions.hide()
				));
		stage.addAction(Actions.delay(0.5f, new Action() {
			public boolean act(float delta) {
				// TODO: make screen go white like an explosion
				showRetry();
				return true;
			}
		}));
	}
	
	private void hideTutorials() {
		for(TutorialMessage tut : levelData.tuts) {
			tut.addAction(Actions.sequence(
					Actions.fadeOut(0.33f),
					Actions.hide()
					));
		}
	}
	
	public void showStats() {
		state = State.LevelWon;
		continueEnabled = false;
		Camera cam = stage.getCamera();
		float sry = cam.position.y + cam.viewportHeight / 2.0f - 150;
		statReport.setPosition(cam.position.x - Gdx.graphics.getWidth() * 1.5f, sry);
		statReport.addAction(Actions.moveTo(cam.position.x - cam.viewportWidth / 2.0f, sry, 1.0f, Interpolation.exp10In));
		int spare = levelData.par - instability;
		String rank = "";
		if(spare <= 0) {
			rank = "A+";
		} else if(spare < 10) {
			rank = "A";
		} else if (spare < 30) {
			rank = "B";
		} else if (spare < 60) {
			rank = "C";
		} else {
			rank = "D";
		}
		statReport.message = "Stats: \nLevel: " + currentLevel + "\n"
				+ "Instability: " + instability + " / " + levelData.meltdownThreshold + "\n"
				+ "Rank: " + rank;
		
		continueLabel.message = action + " to continue";
		prepareContinueLabel();
		// And await click / tap
	}
	
	public void showRetry() {
		state = State.GameOver;
		continueEnabled = false;
		
		float sry = topY() - 40.0f;
		loseMessage.setPosition(cornerLeftX() - Gdx.graphics.getWidth(), sry);
		loseMessage.addAction(Actions.moveTo(cornerLeftX(), sry, 1.0f, Interpolation.exp10In));
		
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
		statReport.addAction(Actions.moveTo(cam.position.x - Gdx.graphics.getWidth() * 1.5f, sry, 1.0f, Interpolation.exp10In));
		winner.addAction(Actions.sequence(
				Actions.fadeOut(0.33f),
				Actions.hide()
				));
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
		float sry = topY() - 40.0f;
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
		splash.addAction(Actions.sequence(
				Actions.moveTo(0.0f,  Gdx.graphics.getHeight() + 100, 1.0f, Interpolation.exp10),
				Actions.hide()
				));
		tap.addAction(Actions.sequence(
				Actions.moveTo(cornerLeftX() - Gdx.graphics.getWidth(), topY() - 110, 1.0f, Interpolation.exp10),
				Actions.hide()
				));
		al.addAction(Actions.sequence(
				Actions.moveTo(cornerLeftX() + Gdx.graphics.getWidth(), 60, 1.0f, Interpolation.exp10),
				Actions.hide()
				));
		
		state = State.StartingLevel;
		stage.addAction(Actions.sequence(Actions.delay(0.9f), Actions.run(new Runnable() {
			public void run() {
				beginLevel();
			}
		})));
		
		logo.addAction(Actions.fadeOut(1.0f));
	}
	
	public void showTitle() {
		continueEnabled = false;
		
		splash.setPosition(cornerLeftX(), topY() + 250f);
		splash.setVisible(true);
		splash.addAction(Actions.moveTo(cornerLeftX(), topY() - 10.0f, 1.0f, Interpolation.exp10));
		
		tap.setVisible(true);
		tap.setPosition(cornerLeftX(), topY() - 110);
		tap.message = action + " to start";
		tap.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f)
				));
		tap.getColor().a = 0.0f;
		
		al.setVisible(true);
		al.setPosition(cornerLeftX() + 0, cornerLeftY() + 60);
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
		
		logo.getColor().a = 0.0f;
		logo.setPosition(cornerLeftX() + Gdx.graphics.getWidth() / 2.0f - logo.getSprite().getWidth() / 2.0f,
				cornerLeftY() + Gdx.graphics.getHeight() / 2.0f - logo.getSprite().getHeight() / 2.0f - 32.0f);
		logo.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f)
				));
		
		state = State.TitleShown;
	}
	
	public void finishedGame() {
		continueEnabled = false;
		state = State.FinishedGame;
		beatGameMessage.setPosition(cornerLeftX(), topY());
		beatGameMessage.addAction(Actions.moveTo(cornerLeftX(), topY() - 30.0f, 1.0f, Interpolation.exp10));
		
		beatGameStats.getColor().a = 0.0f;
		beatGameStats.message = "Congratulations!"; // End game stats go here!
		beatGameStats.setPosition(cornerLeftX(), topY() - 100.0f);
		beatGameStats.addAction(Actions.fadeIn(1.0f));
		
		tap.setVisible(true);
		tap.setPosition(cornerLeftX(), cornerLeftY() + 85);
		tap.message = action + " to return to the titlescreen";
		tap.getColor().a = 0.0f;
		tap.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f),
				new Action() {
					public boolean act(float delta) {
						continueEnabled = true;
						return true;
					}
				}));
	}
	
	public void finishedToTitle() {
		continueEnabled = false;
		tap.addAction(Actions.moveTo(cornerLeftX(), cornerLeftY() + Gdx.graphics.getHeight() + 100, 1.0f, Interpolation.exp10));
		beatGameStats.addAction(Actions.fadeOut(1.0f));
		
		beatGameMessage.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.moveTo(cornerLeftX(), cornerLeftY() + Gdx.graphics.getHeight() + 100, 1.0f, Interpolation.exp10),
				new Action() {
					public boolean act(float delta) {
						showTitle();
						currentLevel = 0;
						return true;
					}
				}));
	}
	
	@Override
	public void show() {
		super.show();
		
		x1 = 800;
		y1 = 480;
		
		addDude("effects", flash = new Dude(EggGame.R.spriteAsAnimatedSprite("pixel"),
				new AABB(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
		flash.stretchSprite = true;
		flash.setVisible(false);
		
		addDude("effects", grain = new Dude(EggGame.R.spriteAsAnimatedSprite("grain"),
				new AABB(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
		grain.stretchSprite = true;
		
		
		
		instabar = new Bar(EggGame.R.sprite("instabar"), EggGame.R.sprite("instabarFill"), new Vector2());
		addDude("overlay", instabar);
		
		goal = new FLabel("", guiFont, new Vector2());
		addDude("overlay", goal);
		
		logo = new Dude(EggGame.R.spriteAsAnimatedSprite("logo"), new PointShape(0, 0));
		addDude("overlay", logo);
		
		addDude("background", new Background(EggGame.R.sprite("background")));
		addDude("overlay", splash = new FLabel("1951", splashFont, new Vector2(0, Gdx.graphics.getHeight() + 100), Gdx.graphics.getWidth()));
		
		action = (Gdx.app.getType() == ApplicationType.Desktop) ? "Click" : "Tap";
		tap = new FLabel("", guiFont, new Vector2(), Gdx.graphics.getWidth());
		addDude("overlay", tap);
		
		String about = "Andrei Barsan, 2013\nsiegedog.com ";
		al = new FLabel(about, small, new Vector2(), Gdx.graphics.getWidth());
		addDude("overlay", al);
		
		winner = new FLabel("Level complete!", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", winner);
		
		loseMessage = new FLabel("Level failed!", splashFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", loseMessage);
		
		statReport = new FLabel("Stats: ", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", statReport);
		
		instabilityIndicator = new FLabel("", small, new Vector2(10.0f, 10.0f), EggGame.R.sprite("instabar").getWidth());
		addDude("overlay", instabilityIndicator);
		
		timeIndicator = new FLabel("", guiFont, new Vector2(), Gdx.graphics.getWidth());
		timeIndicator.alignment = HAlignment.RIGHT;
		addDude("overlay", timeIndicator);
		
		continueLabel = new FLabel(action + " to continue", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", continueLabel);
		
		retryLabel = new FLabel(action + " to retry", guiFont, new Vector2(-1500f, -1500f), Gdx.graphics.getWidth());
		addDude("overlay", retryLabel);
		
		beatGameMessage = new FLabel("Game finished", guiFont, new Vector2(), Gdx.graphics.getWidth());
		addDude("overlay", beatGameMessage);
		
		beatGameStats = new FLabel("", guiFont, new Vector2(), Gdx.graphics.getWidth());
		addDude("overlay", beatGameStats);
		
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

		grain.setX(cornerLeftX());
		grain.setY(cornerLeftY());
		
		if(state == State.Gameplay) {
			
			timeLeft -= delta;
						
			timeIndicator.setVisible(true);
			timeIndicator.message = String.format("%s: %.0f''", timeLabel, timeLeft);
			if(timeLeft < 10) {
				timeIndicator.setColor(Color.RED);
			}
			timeIndicator.setPosition(cornerLeftX() - 20, cornerLeftY() + 43);			
			
			instabilityIndicator.setVisible(true);
			instabilityIndicator.message = "Instability: " + instability + " / " + levelData.meltdownThreshold;
			instabilityIndicator.setPosition(cornerLeftX() + 8, cornerLeftY() + 42);
			
			instabar.setVisible(true);
			instabar.percent = instability / (float)levelData.meltdownThreshold;
			instabar.setPosition(cornerLeftX() + instabar.getWidth() / 2.0f + 8, cornerLeftY() + 32);
			
			goal.setVisible(true);
			goal.message = "Goal: " + levelData.winCondition + " " + derpPlur("particle", levelData.winCondition) + ".";
			goal.setPosition(cornerLeftX() + 8, topY() - 16);
			
			if(instability >= levelData.meltdownThreshold || timeLeft <= 0) {
				loseLevel();
			} else if(layers.get("main").getChildren().size <= levelData.winCondition) {
				winLevel();
			}
		} else {
			timeIndicator.setVisible(false);
			instabilityIndicator.setVisible(false);
			instabar.setVisible(false);
			goal.setVisible(false);
		}
	
		super.render(delta);
		layers.get("rays").clear();
	}
	
	/** The hackyness is strong in this one */
	public void createPulse(float x, float y) {
		if(currentLevel < 3) return;
		
		final float PULSERADIUS = 300;
		final float PR2 = PULSERADIUS * PULSERADIUS;
		final float PULSEFORCE_MIN = 25;
		final float PULSEFORCE_MAX = 200;

		for(Actor actor : getLayer("main").getChildren()) {
			if(actor instanceof Bouncie) {
				Vector2 dir = new Vector2(actor.getX() + actor.getWidth() / 2.0f, actor.getY() + actor.getHeight() / 2.0f);
				dir.sub(new Vector2(x, y));
				if(dir.len2() < PR2) {
					float power = Interpolation.linear.apply(PULSEFORCE_MAX, PULSEFORCE_MIN, dir.len() / PULSERADIUS);
					dir.nor();
					dir.mul(power);
					Bouncie b = (Bouncie)actor;
					b.applyPulse(dir.x, dir.y);
					AnimatedSprite pspr = EggGame.R.spriteAsAnimatedSprite("pulse");
					final Dude pulseGfx = new Dude(pspr, new AABB(x - PULSERADIUS / 2.0f, y - PULSERADIUS / 2.0f, PULSERADIUS, PULSERADIUS));
					pulseGfx.setScale(0.0f);
					pulseGfx.stretchSprite = true;
					//pulseGfx.setOrigin(pspr.getWidth() / 2.0f, pspr.getHeight() / 2.0f);
					pulseGfx.setOrigin(PULSERADIUS / 2.0f, PULSERADIUS / 2.0f);
					pulseGfx.addAction(Actions.sequence(
							Actions.scaleTo(1.0f, 1.0f, 0.20f),
							Actions.fadeOut(0.55f),
							new Action() {
								public boolean act(float delta) {
									pulseGfx.kill();
									return true;
								}
							}));
					addDude("main", pulseGfx);
				}
			}
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
	
	private float topY() {
		Camera cam = stage.getCamera();
		return cam.position.y + cam.viewportHeight / 2.0f; 
	}
	
	private String derpPlur(String word, int nr) {
		return nr == 1 ? word : word + "s";
	}
}
