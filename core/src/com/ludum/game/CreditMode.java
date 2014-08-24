package com.ludum.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ludum.rendering.TextureManager;

public class CreditMode extends ScreenAdapter {
	private SpriteBatch batch;
	private float timeCount;	
	
	public CreditMode(LudumGame g) {
		batch = new SpriteBatch();
		timeCount = 0; 
	}
	
	@Override
	public void render(float dt) {
		timeCount += dt;
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(TextureManager.getInstance().getEnd(), 0, 0);
		batch.end();

		if(timeCount>5)
			System.exit(0);
	}
}
