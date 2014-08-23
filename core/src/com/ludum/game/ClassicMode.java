package com.ludum.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

public class ClassicMode extends ScreenAdapter {
	private Game game;
	
	
	public ClassicMode(Game g) {
		game = g;
	}
	
	
	private void update(float dt) {
		
	}

	private void draw(float dt) {
		
	}
	
	@Override
	public void render(float dt) {
		update(dt);
		draw(dt);
	}	
}
