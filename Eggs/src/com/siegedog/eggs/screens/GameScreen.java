package com.siegedog.eggs.screens;

import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.util.Log;

public class GameScreen implements Screen {

	protected EggGame game;
	protected Stage stage = new Stage();
	private ArrayList<Dude> deadDudes = new ArrayList<Dude>();
	
	public void init(EggGame game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
		
		for(Dude d : deadDudes) {
			if(!stage.getActors().removeValue(d, true)) {
				Log.E("Failed removing a dude");
			}
		}
		
		deadDudes.clear();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
		stage.addActor(dude);
		dude.init(this);
	}
	
	public EggGame getGame() {
		return game;
	}
	
	/* PHYSICS */
	public boolean dudeVSDude(Dude a, Dude b) {
		if(a.getX() + a.getWidth() < b.getX() || a.getX() > b.getX() + b.getWidth()) return false;
		if(a.getY() + a.getHeight() < b.getY() || a.getY() > b.getY() + b.getHeight()) return false;
		
		return true;
	}
	
	public void checkCollisions() {
		Array<Actor> actors = stage.getActors();
		for(int i = 0; i < actors.size - 1; ++i) {
			for(int j = i + 1; j < actors.size; ++j) {
				Dude d1 = (Dude)actors.get(i);
				Dude d2 = (Dude)actors.get(j);
				
				
			}
		}
	}
}
