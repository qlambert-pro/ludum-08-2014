package com.ludum.map;

public class WorldState {
	private WorldType state;

	public WorldState() {
		state = WorldType.LIGHT;
	}

	public WorldState(WorldType type) {
		state = type;
	}

	public void swapWorld() {
		if (state == WorldType.LIGHT)
			state = WorldType.DARK;
		else
			state = WorldType.LIGHT;
	}
	
	public WorldType getState() {
		return state;
	}
}
