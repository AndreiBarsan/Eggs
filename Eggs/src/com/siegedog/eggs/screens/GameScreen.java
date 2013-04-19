package com.siegedog.eggs.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.siegedog.eggs.EggGame;

public class GameScreen implements Screen {

	protected EggGame game;
	protected Stage stage = new Stage();
	
	public void init(EggGame game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
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

	
	public EggGame getGame() {
		return game;
	}
}
