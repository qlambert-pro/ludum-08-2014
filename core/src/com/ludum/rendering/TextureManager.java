package com.ludum.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
	private Animation animation[] = new Animation[TextureType.values().length];
	private Texture walkSheet;
	private TextureRegion[] frames;
	private static int FRAME_COLS = 4;
	private static int FRAME_ROWS = 1;
	private static TextureManager singleton;
	
	public static TextureManager getInstance(){
		if (singleton == null)
			singleton = new TextureManager();
		return singleton;
	}
	
	private TextureManager(){
		init(TextureType.PlayerRun, walkSheet, frames, animation);
	}
	
	private static final void init(TextureType type, Texture walkSheet, TextureRegion[] frames, Animation animation[]){
		walkSheet = new Texture(type.getFileName());
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation[0] = new Animation(0.1f, frames);
	}
	
	public TextureRegion getTextureRegion(TextureType type, float stateTime){
		return animation[type.getRow()*(FRAME_ROWS-1) + type.getCol()].getKeyFrame(stateTime,true);
		
	}
}
