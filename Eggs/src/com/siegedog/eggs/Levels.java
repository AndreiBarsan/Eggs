package com.siegedog.eggs;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.entities.MainParticle;
import com.siegedog.eggs.entities.Tron;
import com.siegedog.eggs.entities.TutorialMessage;

public class Levels {
	public static ArrayList<LevelData> levels = new ArrayList<LevelData>();
	static {
		int w = Gdx.graphics.getWidth();
		String action = (Gdx.app.getType() == ApplicationType.Android)
				? "tap" 
				: "click";
		
		String gestureAction = (Gdx.app.getType() == ApplicationType.Android)
				? "Swipe"
				: "Drag the mouse";
		
		BitmapFont fnt = (Gdx.app.getType() == ApplicationType.Android) 
				? EggGame.R.font("motorwerk24") 
				: EggGame.R.font("motorwerk24");
		
		LevelData l;
		l = new LevelData(1, 1, 100, 0, 90.0f,
				new TutorialMessage(gestureAction + " FROM one particle\nTO the other to fuse them!", fnt, new Vector2(0, 300), 0, w)
		);
		l.add(new MainParticle(new Vector2(Gdx.graphics.getWidth() / 2.0f - 100, 200), new Vector2(25.0f, -10.0f), 32));
		l.add(new MainParticle(new Vector2(Gdx.graphics.getWidth() / 2.0f + 100, 200), new Vector2(-5.0f, -8.0f), 32));
		levels.add(l);
		
		int ww = (int)((float)w * 0.75f);
		l = new LevelData(2, 2, 100, 5, 90.0f,
				new TutorialMessage("Merge particles with the smallest difference to win!",
						fnt, new Vector2(w * 0.125f, 200), -1, ww));
		l.add(new MainParticle(new Vector2(40, 50), randDir(15.0f), 22));
		l.add(new MainParticle(new Vector2(50, 90), randDir(15.0f), 24));
		l.add(new MainParticle(new Vector2(250, 390), randDir(65.0f), 224));
		levels.add(l);
		
		l = new LevelData(3, 2, 100, 30, 90.0f,
				new TutorialMessage("Double " + action + " a FREE SPACE to nudge nearby particles.",
						fnt, new Vector2(w * 0.125f, 200), -1, ww));
		l.add(new MainParticle(new Vector2(60, 90), randDir(50.0f), 15));
		l.add(new MainParticle(new Vector2(100, 100), randDir(50.0f), 13));
		l.add(new MainParticle(new Vector2(45, 55), randDir(50.0f), 18));
		
		l.add(new MainParticle(new Vector2(570, 250), randDir(50.0f), 79));
		l.add(new MainParticle(new Vector2(580, 310), randDir(50.0f), 99));
		l.add(new MainParticle(new Vector2(550, 450), randDir(50.0f), 103));
		levels.add(l);
		
		l = new LevelData(4, 3, 150, 30, 90.0f);
		l.add(new MainParticle(new Vector2(120, 190), randDir(50.0f), 15));
		l.add(new MainParticle(new Vector2(115, 200), randDir(50.0f), 24));
		l.add(new MainParticle(new Vector2(130, 255), randDir(50.0f), 43));
		l.add(new MainParticle(new Vector2(110, 250), randDir(50.0f), 79));
		l.add(new MainParticle(new Vector2(170, 310), randDir(50.0f), 50));
		l.add(new MainParticle(new Vector2(240, 120), randDir(50.0f), 293));
		levels.add(l);
		
		l = new LevelData(5, 1, 15, 10, 60.0f);
		l.add(new MainParticle(new Vector2(150, 150), randDir(45.0f), 35));
		l.add(new MainParticle(new Vector2(150, 250), randDir(45.0f), 47));
		l.add(new MainParticle(new Vector2(250, 250), randDir(45.0f), 42));
		l.add(new MainParticle(new Vector2(250, 150), randDir(45.0f), 42));
		levels.add(l);
		
		l = new LevelData(6, 1, 50, 0, 60.0f, 
				new TutorialMessage("The new item can alter a particle's value" +
						" without affecting the global instability! " +
						"\nUse this to your advantage!",
						fnt, new Vector2(w * 0.125f, 280.0f), -1, ww));
		
		l.add(new MainParticle(new Vector2(50, 350), randDir(45.0f), 120));
		
		l.add(new MainParticle(new Vector2(350, 90), randDir(25.0f), 20));
		l.add(new Tron(new Vector2(300, 80), randDir(25.0f), 100));
		
		levels.add(l);
		
		l = new LevelData(7, 1, 50, 10, 60.0f);
		l.add(new MainParticle	(new Vector2(150, 155), randDir(50.0f), 370));
		l.add(new Tron			(new Vector2(120, 160), randDir(20.0f), 140));
		l.add(new MainParticle	(new Vector2(520, 190), randDir(50.0f), 50));
		l.add(new Tron			(new Vector2(500, 110), randDir(20.0f), -190));
		
		levels.add(l);
		
		l = new LevelData(8, 2, 50, 10, 60.0f);
		l.add(new MainParticle	(new Vector2(300, 300), randDir(50.0f), 90));
		l.add(new MainParticle	(new Vector2(200, 200), randDir(50.0f), 30));
		l.add(new MainParticle	(new Vector2(250, 200), randDir(50.0f), 340));
		l.add(new Tron			(new Vector2(220, 170), randDir(30.0f), -52));
		l.add(new Tron			(new Vector2(180, 190), randDir(30.0f), -120));
		
		l.add(new MainParticle	(new Vector2(640, 300), randDir(50.0f), 22));
		l.add(new MainParticle	(new Vector2(680, 290), randDir(50.0f), 29));
		l.add(new MainParticle	(new Vector2(610, 320), randDir(50.0f), 74));
		
		levels.add(l);
		
		l = new LevelData(9, 4, 20, 11, 25.0f);
		l.add(new MainParticle	(new Vector2(220, 115), randDir(60.0f), 11));
		l.add(new MainParticle	(new Vector2(420, 315), randDir(60.0f), 15));
		l.add(new MainParticle	(new Vector2(191, 30),  randDir(60.0f), 81));
		l.add(new MainParticle	(new Vector2(291, 130), randDir(60.0f), 84));
		l.add(new MainParticle	(new Vector2(100, 79),  randDir(60.0f), 420));
		l.add(new MainParticle	(new Vector2(150, 320), randDir(60.0f), 423));
		l.add(new MainParticle	(new Vector2(550, 120), randDir(60.0f), 111));
		l.add(new MainParticle	(new Vector2(650, 90),  randDir(60.0f), 112));
		levels.add(l);
		
		l = new LevelData(10, 1, 20, 10, 25.0f);
		l.add(new MainParticle	(new Vector2(250, 250), randDir(40.0f), 730));
		l.add(new Tron 			(new Vector2(200, 250), randDir(30.0f), -100));
		l.add(new Tron 			(new Vector2(300, 250), randDir(30.0f), -100));
		l.add(new Tron 			(new Vector2(250, 200), randDir(30.0f), -100));
		l.add(new Tron 			(new Vector2(250, 300), randDir(30.0f), -100));
		
		l.add(new MainParticle	(new Vector2(630, 125), randDir(40.0f), 200));
		l.add(new Tron			(new Vector2(710, 125), randDir(30.0f), +30));
		l.add(new Tron			(new Vector2(550, 125), randDir(30.0f), +30));
		l.add(new Tron			(new Vector2(630, 205), randDir(30.0f), +30));
		l.add(new Tron			(new Vector2(630, 45),  randDir(30.0f), +30));
		
		levels.add(l);
	}
	
	public static Vector2 randDir(float amount) {
		return new Vector2(
					MathUtils.random(-1.0f, 1.0f),
					MathUtils.random(-1.0f, 1.0f)
				).nor().mul(amount);
	}
}
