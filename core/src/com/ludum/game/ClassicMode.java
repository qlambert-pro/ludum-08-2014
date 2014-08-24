package com.ludum.game;

import com.ludum.map.Map;
import com.ludum.map.WorldState;

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
import com.ludum.rendering.CharacterCenteredCamera;

public class ClassicMode extends ScreenAdapter {
	private Game game;
	
	private WorldState state;

	private SpriteBatch worldBatch;
	private SpriteBatch uiBatch;

	private int currentCharacterIndex = 0;
	private List<Player> characters = new ArrayList<Player>();
	private List<InputProcessor> characterControllers =
			new ArrayList<InputProcessor>();

	private Map map;
	private CharacterCenteredCamera cam;

	

	public ClassicMode(Game g) {
		// Gdx.audio.newMusic();
		game = g;
		worldBatch = new SpriteBatch();			
		uiBatch    = new SpriteBatch();

		state = new WorldState();
		
		map = new Map("testMap.tmx",state);
		map.load();

		addSwan();
		addJupiter();
		
		currentCharacterIndex = 0;
		
		((LudumGame) game).setInputProcessor(characterControllers.get(currentCharacterIndex));

		cam = new CharacterCenteredCamera(characters.get(currentCharacterIndex));
	}
	
	private void addSwan() {
		characters.add(PlayerFactory.getFactory().getSwan(
				map.getSpawn(characters.size()),state));
		characterControllers.add(new PlayerControls(characters.get(characters.size()-1),
				 				 this));
	}
	
	private void addJupiter() {
		characters.add(PlayerFactory.getFactory().getJupiter(
				map.getSpawn(characters.size()),state));
		characterControllers.add(new PlayerControls(characters.get(characters.size()-1),
				 				 this));
	}
	
	private void update(float dt) {
		for (Player p : characters)
			p.updatePhysics(dt);
		PhysicsManager.getInstance().update(dt);
		for (Player p : characters)
			p.update(dt);
		cam.follow();
	}

	private void draw(float dt) {
		/* Render part */
		Gdx.gl.glClearColor(0.3f, 0.5f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		map.render(cam);
		
		worldBatch.setProjectionMatrix(cam.combined);

		worldBatch.begin();
		for (Player p : characters)
		{
			p.draw(worldBatch);
		}
		worldBatch.end();

		drawUI();
	}

	private void drawUI() {
		int i = 0;
		for (Player p : characters) {
			p.drawUI(uiBatch,
					 new Vector2(i*ConfigManager.portraitSizeX, 0),
					 p == characters.get(currentCharacterIndex));
			i++;
		}
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
		
		cam.changeCharacter(characters.get(currentCharacterIndex));		
	}

	public void swapWorld() {
		state.swapWorld();
		map.changeWorld();
	}
}
