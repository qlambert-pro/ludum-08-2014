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
import com.ludum.configuration.ConfigManager;
import com.ludum.controls.PlayerControls;
import com.ludum.entity.player.Player;
import com.ludum.entity.player.PlayerFactory;
import com.ludum.physics.PhysicsManager;



public class ClassicMode extends ScreenAdapter {
	private Game game;
	private List<Player> characters = new ArrayList<Player>();
	private Map testMap;
	private SpriteBatch spriteBatch;

	private int currentCharacterIndex = 0;
	private List<InputProcessor> characterControllers =
			new ArrayList<InputProcessor>();
	
	public ClassicMode(Game g) {
		//Gdx.audio.newMusic();
		game = g;
		spriteBatch = new SpriteBatch();

		testMap=new Map();
		testMap.create();
		
		characters.add(PlayerFactory.getFactory().getAlice(testMap.getSpawn(0)));
		characterControllers.add(new PlayerControls(characters.get(0), this));
		
		characters.add(PlayerFactory.getFactory().getBob(testMap.getSpawn(1)));
		characterControllers.add(new PlayerControls(characters.get(1), this));
		
		((LudumGame) game).setInputProcessor(characterControllers.get(0));
	}
	
	
	private void update(float dt) {
		for (Player p : characters)
			p.updatePhysics(dt);
		PhysicsManager.getInstance().update(dt);
		for (Player p : characters)
			p.update(dt);		
	}

	private void draw(float dt) {

		/* Render part */
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		testMap.render();
		for (Player p : characters)
			p.draw(spriteBatch);

		drawUI();
	}

	private void drawUI() {
		int i = 0;
		for (Player p : characters) {
			p.drawUI(spriteBatch,
					 new Vector2(i*ConfigManager.portraitSizeX, 0),
					 p == characters.get(currentCharacterIndex));
			i++;
		}
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
		currentCharacterIndex = (currentCharacterIndex + 1) % characters.size();
		((LudumGame) game).removeInputProcessor(characterControllers.get(0));
		characterControllers.add(characterControllers.remove(0));
		((LudumGame) game).setInputProcessor(characterControllers.get(0));
		centerCamera();
	}
	
	public void swapWorld() {
		//TODO change the relevant bloc's physic and rendering
		System.out.println("Calling swapWorld");
	}
}
