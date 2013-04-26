package com.siegedog.eggs.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.siegedog.eggs.Dude;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.physics.Collision;
import com.siegedog.eggs.util.Log;

public class GameScreen implements Screen {

	protected EggGame game;
	protected Stage stage = new Stage();
	private ArrayList<Dude> deadDudes = new ArrayList<Dude>();
	protected final int scale = 1;
	
	public void init(EggGame game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		checkCollisions();
		getStage().act(delta);
		getStage().draw();
		
		for(Dude d : deadDudes) {
			if(!getStage().getActors().removeValue(d, true)) {
				Log.E("Failed removing a dude");
			}
		}
		
		deadDudes.clear();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width / scale, height / scale, false);
	}

	@Override
	public void show() {		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

	/**
	 * Called by the dudes who get killed to tell the level that they're about to
	 * get removed.
	 * @param dude
	 */
	public void signalDead(Dude dude) {
		deadDudes.add(dude);
	}
	
	public void addDude(Dude dude) {
		getStage().addActor(dude);
		dude.init(this);
	}
	
	public EggGame getGame() {
		return game;
	}
	
	public Stage getStage() {
		return stage;
	}

	/* PHYSICS */
	public boolean dudeVSDude(Dude a, Dude b) {
		if(a.getX() + a.getWidth() < b.getX() || a.getX() > b.getX() + b.getWidth()) return false;
		if(a.getY() + a.getHeight() < b.getY() || a.getY() > b.getY() + b.getHeight()) return false;
		
		return true;
	}
	
	public void checkCollisions() {
		Array<Actor> actors = getStage().getActors();
		for(int i = 0; i < actors.size - 1; ++i) {
			Dude d1 = (Dude)actors.get(i);
			if( ! d1.physics.interactive) continue;
			for(int j = i + 1; j < actors.size; ++j) {
				Dude d2 = (Dude)actors.get(j);
				if( ! d2.physics.interactive) continue;
				
				Collision c = d1.physics.intersect(d2.physics);
				if(null != c) {
					d1.physics.resolveCollision(c, d2.physics);
				}
			}
		}
	}
}
