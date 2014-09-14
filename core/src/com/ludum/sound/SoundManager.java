package com.ludum.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private static SoundManager singleton;
	
	
	private Sound music;
	private Sound jump;
	private Sound nope;
	private float volume = 1f;
	
	
	public static SoundManager getInstance(){
		if (singleton == null)
			singleton = new SoundManager();
		return singleton;
	}
	
	private SoundManager() {
		music = Gdx.audio.newSound(Gdx.files.internal("sound/music.wav"));				
		jump = Gdx.audio.newSound(Gdx.files.internal("sound/jump.ogg"));	
		nope = Gdx.audio.newSound(Gdx.files.internal("sound/nope.wav"));		
	}
	
	public void startBackGroundMusic(){
		long id = music.play();
		music.setLooping(id,true);
		music.setVolume(id, volume);
	}
	
	public void jump(){
		long id = jump.play();
		jump.setVolume(id, volume);
	}
	
	public void nope(){
		long id = nope.play();
		nope.setVolume(id, volume);
	}
	
}
