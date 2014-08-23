package com.ludum.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ludum.entity.Drawable;
import com.ludum.skill.Skill;
import com.ludum.entity.Entity;

public class Player extends Entity implements Drawable {

	private Skill s1;
	private Skill s2;

	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private SpriteBatch spriteBatch;
	private TextureRegion currentFrame;
	private static int FRAME_COLS = 4;
	private static int FRAME_ROWS = 1;

	public Player(Skill s1, Skill s2) {
		this.s1 = s1;
		this.s2 = s2;
		walkSheet = new Texture("player.png");
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.025f, walkFrames);
        currentFrame = walkFrames[0];
		spriteBatch = new SpriteBatch();
	}

	public void useSkill1() {
		s1.use();
	}

	public void useSkill2() {
		s2.use();
	}

	@Override
	public void draw(Batch batch) {
		spriteBatch.begin();
		spriteBatch.draw(currentFrame,0,0);
		spriteBatch.end();
	}
}
