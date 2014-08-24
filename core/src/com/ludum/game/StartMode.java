package com.ludum.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class StartMode extends ScreenAdapter {
	private LudumGame game;
	private TextButton classicMode;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private BitmapFont font;
	private TextureAtlas buttonsAtlas;
	private Skin buttonSkin;

	public StartMode(LudumGame g) {
		game = g;

		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		batch = new SpriteBatch();

		buttonsAtlas = new TextureAtlas(Gdx.files.internal("button.pack"));
		buttonSkin = new Skin();
		buttonSkin.addRegions(buttonsAtlas);
		font = new BitmapFont();

		stage = new Stage();

		TextButtonStyle style = new TextButtonStyle();
		style.up = buttonSkin.getDrawable("buttonOff");
		style.over = buttonSkin.getDrawable("buttonOn");
		style.font = font;

		classicMode = new TextButton("Start", style);
		classicMode.setPosition(225, 300);
		classicMode.setHeight(50);
		classicMode.setWidth(200);
		classicMode.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.startClassicMode();
				return true;
			}
		});

		stage.addActor(classicMode);
	}

	@Override
	public void show() {
		game.addInputProcessor(stage);
	}

	@Override
	public void hide() {
		game.removeInputProcessor(stage);
	}

	@Override
	public void render(float dt) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.draw();
		batch.end();
	}
}
