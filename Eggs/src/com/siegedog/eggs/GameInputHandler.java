package com.siegedog.eggs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.siegedog.eggs.entities.Bouncie;
import com.siegedog.eggs.entities.FLabel;
import com.siegedog.eggs.entities.MainParticle;
import com.siegedog.eggs.screens.Title1953;
import com.siegedog.eggs.screens.Title1953.State;
import com.siegedog.eggs.util.Log;

public class GameInputHandler extends InputAdapter {

	private Title1953 screen;
	private Stage stage;
	
	private int lastX;
	private int lastY;
	
	private Bouncie swipeStartDude = null;
	private int swipeStartX;
	private int swipeStartY;
	
	static final int GESTURE_THRESHOLD2 = 80 * 80;
	private long lastTap = 0;
	static private final int TAP_THRESHOLD = 200;
	
	BitmapFont guiFont = EggGame.R.font("motorwerk32");
	
	public GameInputHandler(Title1953 scr) {
		screen = scr;
		stage = scr.getStage();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		if(screen.state == State.Gameplay) {
			lastX = screenX;
			lastY = screenY;
			
			swipeStartX = screenX;
			swipeStartY = screenY;
			
			Vector2 coords = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
			Actor result = stage.hit(coords.x, coords.y, true);
			System.out.println("TOUCH DOWN (" + coords.x + ", " + coords.y + "): " + result);
			if(null != result) {
				if(result instanceof Bouncie) {
					swipeStartDude = (Bouncie) result;
				} else {
					System.out.println(result.getClass());
				}
			}
		}
		
		return true; 
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch(screen.state) {
		case TitleShown:
			if(screen.continueEnabled) {
				screen.hideTitle();
			}
			break;
				
		case Gameplay:
			Vector2 totalDelta = new Vector2(screenX - swipeStartX, swipeStartY - screenY); // yes, this is good
			if(totalDelta.len2() > GESTURE_THRESHOLD2) {
				System.out.println("Long nuff");
				if(null != swipeStartDude) {
					Bouncie bestMatch = swipeStartDude.tryFindFuse(totalDelta.angle());
					if(null != bestMatch) {
						// If we found something to fuse with
						System.out.println("Found neighbor to fuse with!!!");
						if(swipeStartDude instanceof MainParticle && bestMatch instanceof MainParticle) {
							MainParticle sp = (MainParticle)swipeStartDude;
							MainParticle ep = (MainParticle)bestMatch;
							int dif = Math.abs(sp.getValue() - ep.getValue());
							// TODO: only change the particles' internal state here,
							// and do the merge once they actually collide
							//  -> non-interactive & speed one towards other
							// -> set a goal for one of them - when it detects it's almost in the exact
							// same place as the goal Particle, perform the merge
							screen.instability += dif;
							sp.setValue( (sp.getValue() + ep.getValue()) / 2);
							Vector2 labelPos = new Vector2(Math.min(sp.getX(), ep.getX()) + Math.abs(ep.getX() - sp.getX()) / 2,
									Math.min(sp.getY(), ep.getY()) + Math.abs(ep.getY() - sp.getY()) / 2 + 20.0f);
							screen.addDude("overlay", new FLabel("+" + dif, guiFont, labelPos, 
									new Vector2(0.0f, -20.0f), 100, 1.2f));
						}
						bestMatch.kill();
					}
				}
			}
			break;
				
		case EndingLevel:
			break;
			
		case GameOver:
			if(screen.continueEnabled) {
				screen.hideRetry();
			}
			break;
			
		case LevelWon:
			if(screen.continueEnabled) {
				screen.hideStats();
			}
			break;
		case StartingLevel:
			break;
		
		case FinishedGame:
			if(screen.continueEnabled) {
				screen.finishedToTitle();
			}
		}
		
		long now = System.currentTimeMillis();
		long delta = now - lastTap;
		if(delta < TAP_THRESHOLD) {
			doubleTapped(screenX, screenY);
			lastTap = 0;
		} else {
			lastTap = now;
		}
		
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(screen.state == State.Gameplay) {
			if(null == swipeStartDude) {
				// Only drag the screen when not gesturing a merge command
				int dx = (screenX - lastX);
				int dy = (screenY - lastY);
				Camera cam = screen.getStage().getCamera();
				cam.position.x -= dx;
				cam.position.y += dy;
				
				cam.position.x = MathUtils.clamp(cam.position.x, screen.x0 + cam.viewportWidth / 2, screen.x1 - cam.viewportWidth / 2);
				cam.position.y = MathUtils.clamp(cam.position.y, screen.y0 + cam.viewportHeight / 2, screen.y1 - cam.viewportHeight / 2);
			}
			
			lastX = screenX;
			lastY = screenY;
		}
		
		return true;
	}
	
	private void doubleTapped(int screenX, int screenY) {
		if(screen.state == State.Gameplay) {
			Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
			Actor result = screen.getLayer("main").hit(stageCoords.x, stageCoords.y, true);
			if(null == result) {
				screen.createPulse(stageCoords.x, stageCoords.y);
			}
		}
	}

}
