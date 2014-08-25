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
	
	public Player getSeal(Vector2 spawn, Vector2 mapSize, WorldState state) {
		Player seal = new Seal(spawn, mapSize, TextureManager
				.getInstance().getJupiterPortraitTextureRegion(), state);
		return seal;
	}
}
