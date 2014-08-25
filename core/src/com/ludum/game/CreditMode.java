package com.ludum.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ludum.rendering.TextureManager;

public class CreditMode extends ScreenAdapter {
	private SpriteBatch batch;
	private float timeCount;
	private OrthographicCamera cam;
	private Texture end;
	
	public CreditMode(LudumGame g) {
		batch = new SpriteBatch();
		timeCount = 0; 
		cam = new OrthographicCamera(1,1);
		cam.update();
		end = new Texture(Gdx.files.internal("end.png"));
	}
	
	@Override
	public void render(float dt) {
		timeCount += dt;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(end,-0.5f,-0.5f,1,1);
		batch.end();

		if(timeCount>5)
			System.exit(0);
	}
}
