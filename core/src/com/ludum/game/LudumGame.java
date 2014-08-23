package com.ludum.game;

import com.badlogic.gdx.Game;
import com.ludum.entity.player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;


public class LudumGame extends Game {
	private ClassicMode classicMode;
	private InputMultiplexer inputs = new InputMultiplexer();
	
	@Override
	public void create() {
		
		
		classicMode = new ClassicMode(this);
		setScreen(classicMode);
		
		inputs.addProcessor(
				new InputAdapter() {					
					@Override
					public boolean keyDown(int keycode) {
						switch (keycode) {
						case Keys.S:
							classicMode.swapWorld();
							return true;
						}
						return false;
					}
				});
		
		Gdx.input.setInputProcessor(inputs);
	}

	public void setInputProcessor(InputProcessor ip) {
		inputs.addProcessor(ip);
	}

	public void removeInputProcessor(InputProcessor ip) {
		inputs.removeProcessor(ip);
	}
}
