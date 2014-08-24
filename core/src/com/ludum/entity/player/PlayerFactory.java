package com.ludum.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.ludum.rendering.TextureManager;

public class PlayerFactory {
	private static PlayerFactory factory = null;
	
	private Player swan  = null;
	private Player jupiter    = null;
	/*
	private Player cecile = null;
	private Player damien = null;
	*/
	
	private PlayerFactory() {
		
	}
	
	static public PlayerFactory getFactory() {
		if (factory == null)
			factory = new PlayerFactory();
		
		return factory;		
	}
	
	public Player getSwan(Vector2 pos) {
		if (swan == null)
			swan = new Player(pos,
							  null,
							  null,
							  TextureManager.getInstance().getSwanPortraitTextureRegion());
		else
			swan.reset(pos);
		return swan;
	}
	
	public Player getJupiter(Vector2 pos) {
		if (jupiter == null)
			jupiter = new Player(pos,
					  			 null,
					  			 null,
					  			 TextureManager.getInstance().getJupiterPortraitTextureRegion());
		else
			jupiter.reset(pos);
		return jupiter;
	}
	
/*	public Player getCecile(Vector2 pos) {
		if (cecile == null)
			cecile = new Player(pos, null, null);
		else
			cecile.reset(pos);
		return cecile;
	}
	
	public Player getDamien(Vector2 pos) {
		if (damien == null)
			damien = new Player(pos, null, null);
		else
			damien.reset(pos);
		return damien;
	}*/
}
