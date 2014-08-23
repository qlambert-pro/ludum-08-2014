package com.ludum.game;

import com.badlogic.gdx.Game;
import com.ludum.entity.player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class LudumGame extends Game {
	private ClassicMode classicMode;
	
	@Override
	public void create() {
		
		
		classicMode = new ClassicMode(this);
		setScreen(classicMode);
		
	}

	public void setInputProcessor(InputProcessor ip) {
		Gdx.input.setInputProcessor(ip);
	}

}
