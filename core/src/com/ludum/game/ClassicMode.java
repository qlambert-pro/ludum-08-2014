package com.ludum.game;

import com.ludum.map.Map;
import com.ludum.map.MapLoader;
import com.ludum.map.WorldState;

import java.util.ArrayList;
import java.util.List;

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
import com.ludum.sound.SoundManager;

public class ClassicMode extends ScreenAdapter {
	private LudumGame game;

	private WorldState state;
	private MapLoader mapLoader;

	private SpriteBatch worldBatch;
	private SpriteBatch uiBatch;

	private int currentCharacterIndex = 0;
	private List<Player> characters = new ArrayList<Player>();
	private List<InputProcessor> characterControllers = new ArrayList<InputProcessor>();

	private Map map;
	private CharacterCenteredCamera cam;

	public ClassicMode(LudumGame g) {
		// Gdx.audio.newMusic();
		game = g;
		currentCharacterIndex = -1;
		worldBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		state = new WorldState();


		mapLoader = MapLoader.getLoader();
		loadLevel();
		SoundManager.getInstance().startBackGroundMusic();
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
		for (Player p : characters) {
			p.draw(worldBatch);
		}
		worldBatch.end();

		drawUI();
	}

	private void drawUI() {
		int i = 0;
		for (Player p : characters) {
			p.drawUI(uiBatch, new Vector2(i * ConfigManager.portraitSizeX, 0),
					p == characters.get(currentCharacterIndex));
			i++;
		}
	}

	@Override
	public void render(float dt) {
		update(dt);
		draw(dt);

		/* Check end condition */
		boolean end = true;
		for (Player p : characters) {
			if (!p.isAtEnd())
				end = false;
		}
		if (end) {
			if (mapLoader.isLastMap())
				game.startCreditMode();
			else {
				loadLevel();
			}
		}
	}

	public void nextCharacter() {
		characters.get(currentCharacterIndex).stop();
		
		game.removeInputProcessor(characterControllers
				.get(currentCharacterIndex));

		currentCharacterIndex = (currentCharacterIndex + 1) % characters.size();

		game.addInputProcessor(characterControllers.get(currentCharacterIndex));
		cam.changeCharacter(characters.get(currentCharacterIndex));
	}

	public void swapWorld() {
		state.swapWorld();
		map.changeWorld();
	}

	public void loadLevel() {
		/* Clear last map */
		if (currentCharacterIndex >= 0)
			game.removeInputProcessor(characterControllers
					.get(currentCharacterIndex));
		PhysicsManager.getInstance().clear();
		characterControllers.clear();
		characters.clear();

		/* Load new map */
		map = mapLoader.getNextMap(state);
		List<Vector2> spawnList = map.getSpawns();

		/* Spawn Player */
		PlayerFactory playerFactory = PlayerFactory.getFactory();
		for (int i = 0; i < spawnList.size(); i++) {
			Player p = playerFactory.getPlayer(spawnList.get(i), state, i);
			characters.add(p);
			characterControllers.add(new PlayerControls(p, this));
		}
		currentCharacterIndex = 0;

		/* Settup input and camera */
		game.addInputProcessor(characterControllers.get(currentCharacterIndex));
		cam = new CharacterCenteredCamera(characters.get(currentCharacterIndex));
	}
}
