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

	public Player getSwan(Vector2 pos, WorldState state) {
		Player swan = new Player(pos, null, null, TextureManager.getInstance()
				.getSwanPortraitTextureRegion(), state);
		return swan;
	}

	public Player getJupiter(Vector2 pos, WorldState state) {
		Player jupiter = new Player(pos, null, null, TextureManager
				.getInstance().getJupiterPortraitTextureRegion(), state);

		return jupiter;
	}
	
	public Player getPlayer(Vector2 pos, WorldState state, int id) {
		if(id == 0) 
			return getSwan(pos, state);
		else if(id == 1)
			return getJupiter(pos, state);
		else
			return null;
	}
}
