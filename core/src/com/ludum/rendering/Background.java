package com.ludum.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.ludum.map.WorldState;
import com.ludum.map.WorldType;

public class Background {

	private static Background singleton;
	private Texture back;
	private Texture back1;
	private Texture back2;

	private Texture backt;
	private Texture back1t;
	private Texture back2t;

	private Background() {
		back = new Texture(Gdx.files.internal("back.png"));
		back1 = new Texture(Gdx.files.internal("back1.png"));
		back2 = new Texture(Gdx.files.internal("back2.png"));
		back1.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		back2.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		backt = new Texture(Gdx.files.internal("backt.png"));
		back1t = new Texture(Gdx.files.internal("back1t.png"));
		back2t = new Texture(Gdx.files.internal("back2t.png"));
		back1t.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		back2t.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	}

	public static Background getInstance() {
		if (singleton == null) {
			singleton = new Background();
		}
		return singleton;
	}

	public void render(Batch batch, float camPosX, float camPosY, WorldState state) {
		if (state.getState() == WorldType.LIGHT) {
			batch.draw(back, -10000, 0, back.getWidth() * 1000, back.getHeight(), 100, 1, 0, 0);
			batch.draw(back2, camPosX * 0.5f - 10000, 0, back2.getWidth() * 1000, back2.getHeight(), 1000, 1, 0, 0);
			batch.draw(back1, camPosX * 0.3f - 10000, 0, back1.getWidth() * 1000, back1.getHeight(), 1000, 1, 0, 0);
		} else {
			batch.draw(backt, -10000, 0, backt.getWidth() * 1000, backt.getHeight(), 100, 1, 0, 0);
			batch.draw(back2t, camPosX * 0.5f - 10000, 0, back2t.getWidth() * 1000, back2t.getHeight(), 1000, 1, 0, 0);
			batch.draw(back1t, camPosX * 0.3f - 10000, 0, back1t.getWidth() * 1000, back1t.getHeight(), 1000, 1, 0, 0);
		}
	}
}
