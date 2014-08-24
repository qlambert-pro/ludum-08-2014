package com.ludum.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
	private static int PORTRAITS_COLS = 2;
	private static int PORTRAITS_ROWS = 1;
	private Animation animation[] = new Animation[TextureType.values().length];
	private Texture walkSheet;
	private Texture portraitSheet;
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
		initPortraits(TextureType.Portraits, portraitSheet, portraits);
		
		portraitSheet = new Texture(TextureType.Portraits.getFileName());
		TextureRegion[][] tmp = TextureRegion.split(portraitSheet,
				portraitSheet.getWidth()/PORTRAITS_COLS,
				portraitSheet.getHeight()/PORTRAITS_ROWS);
        portraits = new TextureRegion[PORTRAITS_COLS * PORTRAITS_ROWS];
        int index = 0;
        for (int i = 0; i < PORTRAITS_ROWS; i++) {
            for (int j = 0; j < PORTRAITS_COLS; j++) {
                portraits[index++] = tmp[i][j];
            }
        }
	}
	
	private static final void initPortraits(TextureType type,
											Texture portraitSheet,
											TextureRegion[] portraits) {

	}
	
	private static final void init(TextureType type, Texture walkSheet, TextureRegion[][] frames, Animation animation[]){
		walkSheet = new Texture(type.getFileName());
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/type.nbFrame(), walkSheet.getHeight());
        frames[type.ordinal()] = new TextureRegion[type.nbFrame()];
        for (int i = 0; i < type.nbFrame(); i++) {
        	frames[type.ordinal()][i] = tmp[0][i];
        }
        animation[type.ordinal()] = new Animation(0.1f, frames[type.ordinal()]);
	}
	
	public TextureRegion getTextureRegion(TextureType type, float stateTime){
		return animation[type.ordinal()].getKeyFrame(stateTime,true);
	}
	
	
	public TextureRegion getSwanPortraitTextureRegion() {
		return portraits[0];
	}

	public TextureRegion getJupiterPortraitTextureRegion() {
		return portraits[1];
	}
	
	public TextureRegion getEnd() {
		return portraits[0];
	}
	
}
