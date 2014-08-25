package com.ludum.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
	private static int PORTRAITS_COLS = 2;
	private static int PORTRAITS_ROWS = 1;
	private Animation animation[] = new Animation[TextureType.values().length];
	private Texture walkSheet;
	
	private static Texture swan;
	private static Texture swanSelect;
	private static Texture death;
	private static Texture deathSelect;
	private static Texture jupiter;
	private static Texture jupiterSelect;
	
	private TextureRegion[][] frames = new TextureRegion[TextureType.values().length][];
	private TextureRegion[] portraits;
	private static TextureManager singleton;
	
	public static TextureManager getInstance(){
		if (singleton == null)
			singleton = new TextureManager();
		return singleton;
	}
	
	private TextureManager(){
		init(TextureType.SwanIdle, walkSheet, frames, animation);
		init(TextureType.SwanJump, walkSheet, frames, animation);
		init(TextureType.SwanRun, walkSheet, frames, animation);
		init(TextureType.SwanHightJump, walkSheet, frames, animation);
		init(TextureType.SwanWallClimbing, walkSheet, frames, animation);
		init(TextureType.SwanHightJump, walkSheet, frames, animation);
		
		
		init(TextureType.JupiterIdle, walkSheet, frames, animation);
		init(TextureType.JupiterJumpFall, walkSheet, frames, animation);
		init(TextureType.JupiterRun, walkSheet, frames, animation);
		
		init(TextureType.SealIdle, walkSheet, frames, animation);
		init(TextureType.SealJump, walkSheet, frames, animation);
		init(TextureType.SealRun, walkSheet, frames, animation);
		init(TextureType.SealLevitation, walkSheet, frames, animation);
		
		initPortraits();
	}
	
	private static final void initPortraits() {
		swan  = new Texture(Gdx.files.internal("jumper.png"));
		swanSelect = new Texture(Gdx.files.internal("jumper-select.gif"));
		death = new Texture(Gdx.files.internal("death.png"));
		deathSelect = new Texture(Gdx.files.internal("death-select.gif"));
		jupiter = new Texture(Gdx.files.internal("jupiter.png"));
		jupiterSelect = new Texture(Gdx.files.internal("jupiter-select.gif"));
	}
	
	private static final void init(TextureType type, Texture walkSheet, TextureRegion[][] frames, Animation animation[]){
		walkSheet = new Texture(type.getFileName());
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/type.nbFrame(), walkSheet.getHeight());
        frames[type.ordinal()] = new TextureRegion[type.nbFrame()];
        for (int i = 0; i < type.nbFrame(); i++) {
        	frames[type.ordinal()][i] = tmp[0][i];
        }
        animation[type.ordinal()] = new Animation(0.15f, frames[type.ordinal()]);
	}
	
	public TextureRegion getTextureRegion(TextureType type, float stateTime){
		return animation[type.ordinal()].getKeyFrame(stateTime,true);
	}
	
	
	public Texture getSwanPortrait() {
		return swan;
	}

	public Texture getSwanPortraitSelected() {
		return swanSelect;
	}
	
	public Texture getDeathPortrait() {
		return death;
	}

	public Texture getDeathPortraitSelected() {
		return deathSelect;
	}
	public Texture getJupiterPortrait() {
		return jupiter;
	}

	public Texture getJupiterPortraitSelected() {
		return jupiterSelect;
	}
	
	public TextureRegion getEnd() {
		return portraits[0];
	}
	
}
