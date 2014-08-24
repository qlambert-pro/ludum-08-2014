package com.ludum.game;

import com.badlogic.gdx.Game;
import com.ludum.game.ClassicMode;
import com.ludum.game.StartMode;
import com.ludum.game.CreditMode;
import com.ludum.entity.player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;


public class LudumGame extends Game {
	private StartMode startMode = null;
	private ClassicMode classicMode = null;
	private CreditMode creditMode = null;
	
	private InputProcessor inGameControl;
	private InputMultiplexer inputs = new InputMultiplexer();
	
	@Override
	public void create() {
		
		
		classicMode = new ClassicMode(this);
		creditMode = new CreditMode(this);
		startMode = new StartMode(this);
		
		setScreen(startMode);
		
		inGameControl = new InputAdapter() {					
					@Override
					public boolean keyDown(int keycode) {
						switch (keycode) {
						case Keys.S:
							classicMode.swapWorld();
							return true;
						}
						return false;
					}
				}; 		
		
		Gdx.input.setInputProcessor(inputs);
	}
	
	public void startClassicMode() {
		inputs.addProcessor(inGameControl);
		setScreen(classicMode);
	}

	public void startCreditMode() {
		inputs.removeProcessor(inGameControl);
		setScreen(creditMode);
	}
	
	public void addInputProcessor(InputProcessor ip) {
		inputs.addProcessor(ip);
	}

	public void removeInputProcessor(InputProcessor ip) {
		inputs.removeProcessor(ip);
	}
}
