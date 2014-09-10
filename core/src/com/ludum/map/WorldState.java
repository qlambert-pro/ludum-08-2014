package com.ludum.map;

import com.ludum.sound.SoundManager;

public class WorldState {
	private WorldType state;
	private Map map;
	
	private boolean isSwapped;
	private boolean canSwap = true;
	
	public WorldState() {
		state = WorldType.LIGHT;		
	}	
	
	public void setMap(Map m) {
		map = m;
	}

	public void swapWorld() {
		if (canSwap) {
			isSwapped = false;				
			if (state == WorldType.LIGHT) {
				state = WorldType.DARK;
				map.setDarkWorld();
			} else {
				state = WorldType.LIGHT;
				map.setLightWorld();
			}
		}
		else 
			SoundManager.getInstance().nope();
	}
	
	public void canSwap(boolean b) {
		canSwap = b;
	}
	
	public boolean isSwapped() {
		return isSwapped;
	}
	
	public void hasSwapped() {
		isSwapped = true;
	}
	
	public WorldType getState() {
		return state;
	}
}
