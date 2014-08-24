package com.ludum.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.ludum.rendering.TextureManager;

public class SoundManager {
	private static SoundManager singleton;
	
	private Music music;
	public static SoundManager getInstance(){
		if (singleton == null)
			singleton = new SoundManager();
		return singleton;
	}
	
	private SoundManager() {
		
	}
	
	public void startBackGroundMusic(){
		music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.wav"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();
	}
}
