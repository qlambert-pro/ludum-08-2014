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
import com.ludum.controls.PlayerControls;
import com.ludum.entity.player.Player;
import com.ludum.entity.player.PlayerFactory;
import com.ludum.physics.PhysicsManager;
import com.ludum.rendering.CharacterCenteredCamera;

public class ClassicMode extends ScreenAdapter {
	private Game game;
	private SpriteBatch spriteBatch;
	private Map map;
	private CharacterCenteredCamera cam;
	private List<Player> characters = new ArrayList<Player>();
	private List<InputProcessor> characterControllers = new ArrayList<InputProcessor>();
	private int currentPlayer;
	
	public ClassicMode(Game g) {
		// Gdx.audio.newMusic();
		game = g;
		spriteBatch = new SpriteBatch();

		map = new Map();
		map.load();

		characters
				.add(PlayerFactory.getFactory().getAlice(map.getSpawn(0)));
		characterControllers.add(new PlayerControls(characters.get(0), this));

		characters.add(PlayerFactory.getFactory().getBob(map.getSpawn(1)));
		characterControllers.add(new PlayerControls(characters.get(1), this));

		currentPlayer = 0;
		
		((LudumGame) game).setInputProcessor(characterControllers.get(currentPlayer));

		cam = new CharacterCenteredCamera(characters.get(currentPlayer));
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		map.render(cam);
		
		spriteBatch.setProjectionMatrix(cam.combined);

		spriteBatch.begin();
		for (Player p : characters)
		{
			p.draw(spriteBatch);
		}
		spriteBatch.end();
	}

	@Override
	public void render(float dt) {
		update(dt);
		draw(dt);
	}

	public void nextCharacter() {

		((LudumGame) game).removeInputProcessor(characterControllers.get(currentPlayer));
		currentPlayer = (currentPlayer + 1) % characterControllers.size();
		((LudumGame) game).setInputProcessor(characterControllers.get(currentPlayer));
		cam.changeCharacter(characters.get(currentPlayer));
	}

	public void swapWorld() {
		// TODO change the relevant bloc's physic and rendering
		System.out.println("Calling swapWorld");
	}
}
