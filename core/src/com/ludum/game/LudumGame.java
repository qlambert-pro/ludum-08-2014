package com.ludum.game;

import com.badlogic.gdx.Game;

public class LudumGame extends Game {
	private ClassicMode classicMode;
	
	
	@Override
	public void create() {
	classicMode = new ClassicMode(this);
	setScreen(classicMode);
	
	}

}
