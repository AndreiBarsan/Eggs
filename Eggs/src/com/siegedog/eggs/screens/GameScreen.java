package com.siegedog.eggs.screens;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.siegedog.eggs.EggGame;
import com.siegedog.eggs.entities.Dude;
import com.siegedog.eggs.physics.Collision;
import com.siegedog.eggs.util.Log;

public class GameScreen implements Screen {

	protected EggGame game;
	protected Stage stage = new Stage();
	private ArrayList<Dude> deadDudes = new ArrayList<Dude>();
	/* Rendering scale - >1 induces a retro effect */
	protected final int scale = 1;
	
	public void init(EggGame game) {
		this.game = game;
		
		addLayer("main");
		addLayer("overlay");
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
		addDude("main", dude);
	}

	private HashMap<String, Group> layers = new HashMap<String, Group>();
	
	public void addDude(String layer, Dude dude) {
		if(!layers.containsKey(layer)) {
			addLayer(layer);
		} 
		
		layers.get(layer).addActor(dude);
		dude.init(this);
	}
	
	private void addLayer(String name) {
		Group lg = new Group();
		lg.setName(name);
		layers.put(name, lg);
		stage.addActor(lg);
	}
	
	public EggGame getGame() {
		return game;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void checkCollisions() {
		Array<Actor> actors = getStage().getActors();
		Array<Dude> dudes = new Array<Dude>();
		for(Actor a : actors) {
			if(a instanceof Group) {
				dudes.addAll(((Group) a).getChildren());
			} else {
				// don't add non-dudes - they're guaranteed not to have physics
			}
		}
				
		for(int i = 0; i < dudes.size; ++i) {
			Dude d1 = dudes.get(i);
			if( ! d1.physics.interactive) continue;
			for(int j = i + 1; j < dudes.size; ++j) {
				Dude d2 = dudes.get(j);
				if( ! d2.physics.interactive) continue;
				Collision c = d1.physics.intersect(d2.physics);
				if(null != c) {
					d1.physics.resolveCollision(c, d2.physics);
				}
			}
		}
	}
}
