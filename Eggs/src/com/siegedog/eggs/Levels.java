package com.siegedog.eggs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.siegedog.eggs.entities.MainParticle;

public class Levels {
	public static LevelData[] levels = new LevelData[5];
	static {
		LevelData l;
		l = new LevelData(1, 1, 100);
		l.add(new MainParticle(new Vector2(100, 200), new Vector2(40.0f, -10.0f), 32));
		l.add(new MainParticle(new Vector2(300, 200), new Vector2(-5.0f, -8.0f), 32));
		levels[1] = l;
		
		l = new LevelData(2, 2, 100);
		l.add(new MainParticle(new Vector2(30, 120), randDir(50.0f), 15));
		l.add(new MainParticle(new Vector2(70, 150), randDir(50.0f), 13));
		l.add(new MainParticle(new Vector2(15, 55), randDir(50.0f), 18));
		
		l.add(new MainParticle(new Vector2(310, 250), randDir(50.0f), 79));
		l.add(new MainParticle(new Vector2(320, 310), randDir(50.0f), 99));
		l.add(new MainParticle(new Vector2(310, 480), randDir(50.0f), 103));
		levels[2] = l;
	}
	
	public static Vector2 randDir(float amount) {
		return new Vector2(
					MathUtils.random(-1.0f, 1.0f),
					MathUtils.random(-1.0f, 1.0f)
				).nor().mul(amount);
	}
}
