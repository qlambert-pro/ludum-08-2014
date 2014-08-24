package com.ludum.game;

import com.badlogic.gdx.Game;
import com.ludum.game.ClassicMode;
import com.ludum.game.StartMode;
import com.ludum.game.CreditMode;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class LudumGame extends Game {
	private StartMode startMode = null;
	private ClassicMode classicMode = null;
	private CreditMode creditMode = null;

	private InputProcessor inGameControl;
	private InputMultiplexer inputs = new InputMultiplexer();

	@Override
	public void create() {

		startMode = new StartMode(this);
		setScreen(startMode);

		inGameControl = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				}
				return false;
			}
		};

		Gdx.input.setInputProcessor(inputs);
	}

	public void startClassicMode() {
		if (classicMode == null)
			classicMode = new ClassicMode(this);

		inputs.addProcessor(inGameControl);
		setScreen(classicMode);
	}

	public void startCreditMode() {
		if (creditMode == null)
			creditMode = new CreditMode(this);

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
