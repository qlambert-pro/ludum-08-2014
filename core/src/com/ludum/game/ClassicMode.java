package com.ludum.game;

import com.ludum.map.Map;


import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ludum.controls.PlayerControls;
import com.ludum.entity.player.Player;
import com.ludum.physics.PhysicsManager;



public class ClassicMode extends ScreenAdapter {
	private Game game;
	private Player player;
	private Map testMap;
	private SpriteBatch spriteBatch;
	private List<InputProcessor> characterControllers =
			new ArrayList<InputProcessor>();
	
	public ClassicMode(Game g) {
		game = g;
		spriteBatch = new SpriteBatch();
		player = new Player(new Vector2(0,0),null,null);
		characterControllers.add(new PlayerControls(player, this));
		((LudumGame) game).setInputProcessor(characterControllers.get(0));
		testMap=new Map();
		testMap.create();

	}
	
	
	private void update(float dt) {
		player.updatePhysics(dt);
		PhysicsManager.getInstance().update(dt);
		player.update(dt);
	}

	private void draw(float dt) {

		

		/* Render part */
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		testMap.render();
		player.draw(spriteBatch);

		
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
