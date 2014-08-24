package com.ludum.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.ludum.map.WorldState;

public class PlayerFactory {
	private static PlayerFactory factory = null;
	
	private Player alice  = null;
	private Player bob    = null;
	private Player cecile = null;
	private Player damien = null;
	
	private PlayerFactory() {
		
	}
	
	static public PlayerFactory getFactory() {
		if (factory == null)
			factory = new PlayerFactory();
		
		return factory;		
	}
	
	public Player getAlice(Vector2 pos, WorldState state) {
		if (alice == null)
			alice = new Player(pos, null, null, state);
		else
			alice.reset(pos);
		return alice;
	}
	
	public Player getBob(Vector2 pos, WorldState state) {
		if (bob == null)
			bob = new Player(pos, null, null, state);
		else
			bob.reset(pos);
		return bob;
	}
	
	public Player getCecile(Vector2 pos, WorldState state) {
		if (cecile == null)
			cecile = new Player(pos, null, null, state);
		else
			cecile.reset(pos);
		return cecile;
	}
	
	public Player getDamien(Vector2 pos, WorldState state) {
		if (damien == null)
			damien = new Player(pos, null, null, state);
		else
			damien.reset(pos);
		return damien;
	}
}
