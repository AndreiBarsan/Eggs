package com.siegedog.eggs;

import java.util.ArrayList;

import com.siegedog.eggs.entities.Bouncie;
import com.siegedog.eggs.entities.TutorialMessage;

public class LevelData {
	
	public ArrayList<Bouncie> entities = new ArrayList<Bouncie>();
	
	public int levelNumber;
	public int winCondition;
	public int meltdownThreshold;
	public int par;
	public float time;
	
	public TutorialMessage[] tuts;
	
	public LevelData(int levelNumber, int winCondition, int meltdownThreshold, int par, float time) {
		this(levelNumber, winCondition, meltdownThreshold, par, time, (TutorialMessage[]) null);
	}
	
	public LevelData(int levelNumber, int winCondition, int meltdownThreshold, int par, float time, TutorialMessage... tuts) {
		this.levelNumber = levelNumber;
		this.winCondition = winCondition;
		this.meltdownThreshold = meltdownThreshold;
		this.par = par;
		this.time = time;
		this.tuts = tuts;
	}

	public void add(Bouncie b) {
		entities.add(b);
	}
}
