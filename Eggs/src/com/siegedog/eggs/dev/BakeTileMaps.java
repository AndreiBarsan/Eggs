package com.siegedog.eggs.dev;

import com.badlogic.gdx.tiledmappacker.TiledMapPacker;

public class BakeTileMaps {
	public static void main(String[] args) {
		TiledMapPacker.main(new String[] {
    			"../Eggs/assets/lvl/",
    			"../Eggs/assets/lvl/baked/"
    	});
	}
}
