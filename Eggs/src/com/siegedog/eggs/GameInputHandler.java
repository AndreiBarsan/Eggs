package com.siegedog.eggs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.siegedog.eggs.entities.Bouncie;
import com.siegedog.eggs.entities.MainParticle;
import com.siegedog.eggs.screens.GameScreen;
import com.siegedog.eggs.screens.Title1953;

public class GameInputHandler extends InputAdapter {

	private Title1953 screen;
	private Stage stage;
	
	private int lastX;
	private int lastY;
	
	private Bouncie swipeStartDude = null;
	private int swipeStartX;
	private int swipeStartY;
	
	static final int GESTURE_THRESHOLD2 = 80 * 80;
	
	public GameInputHandler(Title1953 scr) {
		screen = scr;
		stage = scr.getStage();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
							sp.setValue(sp.getValue() + ep.getValue());
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
		default:
			break;
		}
		
		return true;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int dx = (screenX - lastX);
		int dy = (screenY - lastY);
		Camera cam = screen.getStage().getCamera();
		cam.position.x -= dx;
		cam.position.y += dy;
		
		cam.position.x = MathUtils.clamp(cam.position.x, screen.x0 + cam.viewportWidth / 2, screen.x1 - cam.viewportWidth / 2);
		cam.position.y = MathUtils.clamp(cam.position.y, screen.y0 + cam.viewportHeight / 2, screen.y1 - cam.viewportHeight / 2);
		
		lastX = screenX;
		lastY = screenY;
		
		
		// remember - screenXY = 0, 0 is TOP left
		//System.out.println("SWIPE STARTED: " + swipeStartX + ", " + swipeStartY);
		//System.out.println("Current: " + screenX + ", " + screenY);
		//totalDelta.y *= -1;
		
		
		
		return super.touchDragged(screenX, screenY, pointer);
	}

}
