package com.siegedog.eggs.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.entities.FLabel;


public class Title1953 extends GameScreen {

	BitmapFont splashFont = EggGame.R.font("motorwerk128");
	BitmapFont guiFont = EggGame.R.font("motorwerk32");
	
	@Override
	public void show() {
		super.show();
		
		FLabel splash;
		addDude(splash = new FLabel("1953", splashFont, new Vector2(0, Gdx.graphics.getHeight() + 100), Gdx.graphics.getWidth()));
		splash.addAction(Actions.moveTo(0.0f, 420.0f, 1.0f, Interpolation.exp10));
		
		String action = (Gdx.app.getType() == ApplicationType.Desktop) ? "Click" : "Tap";
		FLabel tap = new FLabel(action + " to start", guiFont, new Vector2(0, 350), Gdx.graphics.getWidth());
		addDude(tap);
		tap.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f)
				));
		tap.getColor().a = 0.0f;
	}
		
	@Override
	public void render(float delta) {
		super.render(delta);
	}
}
