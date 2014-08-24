package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureType;
import com.ludum.skill.Skill;

public class Jupiter extends Player{

	public Jupiter(Vector2 p, Skill s1, Skill s2, TextureRegion port, WorldState s) {
		super(p, s1, s2, port, s);
	}
	
	@Override
	public void update(float dt){
		if(state == PlayerState.JUMPING){
			textureType = TextureType.SwanJump;
		}else if(state == PlayerState.RUNNING){
			textureType = TextureType.SwanRun;
		}else if(state == PlayerState.STANDING){
			textureType = TextureType.JupiterIdle;
		}
		super.update(dt);
	}
	
}