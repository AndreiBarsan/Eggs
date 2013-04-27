package com.siegedog.eggs.screens;

import java.util.Comparator;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.entities.Background;
import com.siegedog.eggs.entities.FLabel;
import com.siegedog.eggs.entities.MainParticle;


public class Title1953 extends GameScreen {

	BitmapFont splashFont = EggGame.R.font("motorwerk128");
	BitmapFont guiFont = EggGame.R.font("motorwerk32");
	BitmapFont small = EggGame.R.font("motorwerk24");
	
	@Override
	public void show() {
		super.show();
		
		stage.addActor(new Background(EggGame.R.sprite("background")));
		
		FLabel splash;
		addDude("overlay", splash = new FLabel("1953", splashFont, new Vector2(0, Gdx.graphics.getHeight() + 100), Gdx.graphics.getWidth()));
		splash.addAction(Actions.moveTo(0.0f, 420.0f, 1.0f, Interpolation.exp10));
		splash.setZIndex(0);
		
		String action = (Gdx.app.getType() == ApplicationType.Desktop) ? "Click" : "Tap";
		FLabel tap = new FLabel(action + " to start", guiFont, new Vector2(0, 350), Gdx.graphics.getWidth());
		addDude("overlay", tap);
		tap.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f)
				));
		tap.getColor().a = 0.0f;
		
		Group g = new Group();
		
		String about = "Andrei Barsan, 2013\nsiegedog.com ";
		FLabel al = new FLabel(about, small, new Vector2(0, 45), Gdx.graphics.getWidth());
		al.addAction(Actions.sequence(
				Actions.delay(1.0f),
				Actions.fadeIn(0.33f)
				));
		al.getColor().a = 0.0f;
		addDude("overlay", al);
		
		int values[] = { 11, 12, 14, 41, 49, 56 }; 
		float spd = 50.0f;
		for(int i = 0; i < 6; ++i) {
			Vector2 pos = new Vector2(MathUtils.random(0.0f, stage.getWidth()), MathUtils.random(0.0f, stage.getHeight()));
			Vector2 vel = new Vector2(MathUtils.random(-1.0f, 1.0f), MathUtils.random(-1.0f, 1.0f)).nor().mul(spd);
			int r = values[i];
			MainParticle p = new MainParticle(pos, r);
			p.physics.velocity = vel;
			addDude(p);
		}
	}
		
	@Override
	public void render(float delta) {
		super.render(delta);
	}
}
