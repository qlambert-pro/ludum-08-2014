package com.ludum.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureManager;


public class PlayerFactory {
	private static PlayerFactory factory = null;
	
	private PlayerFactory() {

	}

	static public PlayerFactory getFactory() {
		if (factory == null)
			factory = new PlayerFactory();

		return factory;
	}

	public Player getSwan(Vector2 spawn, Vector2 mapSize, WorldState state) {
		Player swan = new Swan(spawn, mapSize, TextureManager.getInstance()
				.getSwanPortraitTextureRegion(), state);
		return swan;
	}

	public Player getJupiter(Vector2 spawn, Vector2 mapSize, WorldState state) {
		Player jupiter = new Jupiter(spawn, mapSize, TextureManager
				.getInstance().getJupiterPortraitTextureRegion(), state);
		return jupiter;
	}
	
	public Player getPlayer(Vector2 spawn, Vector2 mapSize,  WorldState state, int id) {
		if(id == 0) 
			return getSwan(spawn, mapSize, state);
		else if(id == 1)
			return getJupiter(spawn, mapSize, state);
		else
			return null;
	}
}
