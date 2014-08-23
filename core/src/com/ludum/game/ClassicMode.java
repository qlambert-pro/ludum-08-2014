package com.ludum.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ludum.entity.player.Player;

public class ClassicMode extends ScreenAdapter {
	private Game game;
	private Player player;
	private SpriteBatch spriteBatch;
	
	public ClassicMode(Game g) {
		game = g;
		spriteBatch = new SpriteBatch();
		player = new Player(null,null);
	}
	
	
	private void update(float dt) {
		
	}

	private void draw(float dt) {
		player.draw(spriteBatch);
	}
	
	@Override
	public void render(float dt) {
		update(dt);
		draw(dt);
	}	
}
