package com.ludum.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private static SoundManager singleton;
	
	
	private Sound music;
	private Sound jump;
	private Sound nope;
	private float volume = 0.1f;
	
	
	public static SoundManager getInstance(){
		if (singleton == null)
			singleton = new SoundManager();
		return singleton;
	}
	
	private SoundManager() {
		music = Gdx.audio.newSound(Gdx.files.internal("sound/music.wav"));
		music.setLooping(0,true);
		music.setVolume(0, volume);
		jump = Gdx.audio.newSound(Gdx.files.internal("sound/jump.ogg"));
		jump.setVolume(0, volume);
		nope = Gdx.audio.newSound(Gdx.files.internal("sound/nope.wav"));
		nope.setVolume(0, volume);
	}
	
	public void startBackGroundMusic(){
		music.play();
	}
	
	public void jump(){
		jump.play();
	}
	
	public void nope(){
		nope.play();
	}
	
}
