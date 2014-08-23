package com.ludum.game;
import com.ludum.map.Map;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.ludum.map.Map;

public class ClassicMode extends ScreenAdapter {
	private Game game;
	private Map testMap;
	
	public ClassicMode(Game g) {
		game = g;
		testMap=new Map();
		testMap.create();
	}
	
	
	private void update(float dt) {
		
	}

	private void draw(float dt) {
		testMap.render();
		
	}
	
	@Override
	public void render(float dt) {
		
		update(dt);
		draw(dt);
		
	}	
}
