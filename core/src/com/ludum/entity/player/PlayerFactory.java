package com.ludum.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureManager;

public class PlayerFactory {
	private static PlayerFactory factory = null;
	
	private Player swan  = null;
	private Player jupiter    = null;
	
	private PlayerFactory() {
		
	}
	
	static public PlayerFactory getFactory() {
		if (factory == null)
			factory = new PlayerFactory();
		
		return factory;		
	}
	
	public Player getSwan(Vector2 pos, WorldState state) {
		if (swan == null)
			swan = new Player(pos,
							  null,
							  null,
							  TextureManager.getInstance().getSwanPortraitTextureRegion(),
							  state);
		else
			swan.reset(pos);
		return swan;
	}
	
	public Player getJupiter(Vector2 pos, WorldState state) {
		if (jupiter == null)
			jupiter = new Player(pos,
					  			 null,
					  			 null,
					  			 TextureManager.getInstance().getJupiterPortraitTextureRegion(),
					  			 state);
		else
			jupiter.reset(pos);
		return jupiter;
	}
}
