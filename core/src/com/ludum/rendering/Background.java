package com.ludum.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Background {
	
	private static Background singleton;
	private Texture back;
	private Texture back1;
	private Texture back2;
	
	
	private Background(){
		back = new Texture(Gdx.files.internal("back.png"));
		back1 = new Texture(Gdx.files.internal("back1.png"));
        back2 = new Texture(Gdx.files.internal("back2.png"));
		back1.setWrap(TextureWrap.Repeat,TextureWrap.Repeat);
		back2.setWrap(TextureWrap.Repeat,TextureWrap.Repeat);
	}
	
	public static Background getInstance(){
		if(singleton == null){
			singleton = new Background();
		}
		return singleton;
	}
	
	public void render(Batch batch, float camPosX, float camPosY){
		batch.draw(back, - 10000 ,0,back.getWidth()*1000,back.getHeight(),100,1,0,0);
		batch.draw(back2, camPosX * 0.5f - 10000,0,back2.getWidth()*1000,back2.getHeight(),1000,1,0,0);
		batch.draw(back1, camPosX * 0.3f - 10000,0,back1.getWidth()*1000,back1.getHeight(),1000,1,0,0);
	}
}
