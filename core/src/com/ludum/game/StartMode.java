package com.ludum.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartMode extends ScreenAdapter {
	private LudumGame game;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Texture start;
	private InputProcessor control;

	public StartMode(LudumGame g) {
		game = g;
		batch = new SpriteBatch();
		start = new Texture(Gdx.files.internal("start.png"));
		cam = new OrthographicCamera(1,1);
		cam.update();
		control = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.ENTER:
					game.removeInputProcessor(control);
					game.startClassicMode();
					break;
				}
				return false;
			}
		};
		game.addInputProcessor(control);
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(start,-0.5f,-0.5f,1,1);
		batch.end();

	}
}
