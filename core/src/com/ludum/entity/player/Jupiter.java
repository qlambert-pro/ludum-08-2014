package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureType;
import com.ludum.skill.FriendlyDash;
import com.ludum.skill.SoloDash;

public class Jupiter extends Player{

	public Jupiter(Vector2 p, TextureRegion port, WorldState s) {
		super(p, port, s);
		s1 = new SoloDash(body);
		s2 = new FriendlyDash(body);
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