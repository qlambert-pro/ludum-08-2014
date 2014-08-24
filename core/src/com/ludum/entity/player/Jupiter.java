package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;
import com.ludum.skill.FriendlyDash;
import com.ludum.skill.SoloDash;

public class Jupiter extends Player{

	public Jupiter(Vector2 spawn, Vector2 mapSize, TextureRegion port, WorldState s) {
		super(spawn, mapSize, port, s);
		s1 = new SoloDash(body);
		s2 = new FriendlyDash(body);
	}
	
	@Override
	public void update(float dt){
		if(state == PlayerState.JUMPING || state == PlayerState.FALLING){
			textureType = TextureType.JupiterJumpFall;
		}else if(state == PlayerState.RUNNING){
			textureType = TextureType.JupiterRun;
		}else if(state == PlayerState.STANDING){
			textureType = TextureType.JupiterIdle;
		}
		super.update(dt);
		if(state == PlayerState.JUMPING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
				textureType, 0);
		}if(state == PlayerState.FALLING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
					textureType, 100);
		}
	}
	
}
