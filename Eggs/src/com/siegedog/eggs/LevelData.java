package com.siegedog.eggs;

import java.util.ArrayList;

import com.siegedog.eggs.entities.Bouncie;

public class LevelData {
	enum ParticleType {
		MainParticle,
		BonusParticle,
		DisrupterParticle
	}
	
	public ArrayList<Bouncie> entities = new ArrayList<Bouncie>();
	
	public int levelNumber;
	public int winCondition;
	public int meltdownThreshold;
	public int par;
	public float time;
	
	public LevelData(int levelNumber, int winCondition, int meltdownThreshold, int par, float time) {
		this.levelNumber = levelNumber;
		this.winCondition = winCondition;
		this.meltdownThreshold = meltdownThreshold;
		this.par = par;
		this.time = time;
	}

	public void add(Bouncie b) {
		entities.add(b);
	}
}
