package com.ludum.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;

public class ClassicMode extends ScreenAdapter {
	private Game game;
	private List<InputProcessor> characterControllers =
			new ArrayList<InputProcessor>();
	
	public ClassicMode(Game g) {
		game = g;
	}
	
	
	private void update(float dt) {
		
	}

	private void draw(float dt) {
		
	}
	
	private void centerCamera() {
		//TODO center camera on currently controlled player
	}

	@Override
	public void render(float dt) {
		update(dt);
		draw(dt);
	}	

	public void nextCharacter() {
		centerCamera();
		characterControllers.add(characterControllers.remove(0));
		((LudumGame) game).setInputProcessor(characterControllers.get(0));
	}
}
