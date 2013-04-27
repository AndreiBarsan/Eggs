package com.siegedog.eggs;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.entities.Dude;
import com.siegedog.eggs.screens.GameplayScreen;
import com.siegedog.eggs.util.Log;

public class DemoInput extends InputAdapter {

	final int klimit = 512;
	boolean keys[] = new boolean[klimit];
	private GameplayScreen screen;
	int lastX = -1;
	int lastY = -1;
	long lastTap;
	
	/** Measured in milliseconds */
	public final int TAP_TIME = 200;
		
	public DemoInput(GameplayScreen toMonitor) {
		screen = toMonitor;
	}
		
	@Override
	public boolean keyDown(int keycode) {
		if(keycode > klimit) {
			Log.E("Invalid keycode. Plz fix me.");
		}
		keys[keycode] = true;
		return super.keyDown(keycode);
	}
	
	public boolean pollKey(int keycode) {
		assert keycode < klimit : "Only 512 keycodes supported...";
		return keys[keycode];
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		long now = System.currentTimeMillis();
		if(now - lastTap < TAP_TIME) {
			killRandomGuy(screenX, screenY);
		}
		lastTap = now;
		return super.touchUp(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		lastX = screenX;
		lastY = screenY;
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int dx = (screenX - lastX) / screen.getScale();
		int dy = (screenY - lastY) / screen.getScale();
		Camera cam = screen.getStage().getCamera();
		cam.position.x -= dx;
		cam.position.y += dy;
		
		cam.position.x = MathUtils.clamp(cam.position.x, screen.x0 + cam.viewportWidth / 2, screen.x1 - cam.viewportWidth / 2);
		cam.position.y = MathUtils.clamp(cam.position.y, screen.y0 + cam.viewportHeight / 2, screen.y1 - cam.viewportHeight / 2);
		
		lastX = screenX;
		lastY = screenY;
		
		return super.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode > klimit) {
			Log.E("Invalid keycode. Plz fix me.");
		}
		
		keys[keycode] = false;
		return super.keyUp(keycode);
	}
	
	private void killRandomGuy(int tapX, int tapY) {
		Vector2 coords = screen.getStage().screenToStageCoordinates(new Vector2(tapX, tapY));
		Dude result = (Dude)screen.getStage().hit(coords.x, coords.y, true);
		if(result != null) {
			result.kill();
		}
	}
}
