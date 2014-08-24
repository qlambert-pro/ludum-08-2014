package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureType;


public class Swan extends Player{
	int nbJump = 0;
	public Swan(Vector2 p, TextureRegion port, WorldState s) {
		super(p, port, s);
	}
	
	@Override
	public void update(float dt){
		if(state == PlayerState.JUMPING){
			textureType = TextureType.SwanJump;
		}else if(state == PlayerState.RUNNING){
			textureType = TextureType.SwanRun;
		}else if(state == PlayerState.STANDING){
			textureType = TextureType.SwanIdle;
		}else if(state == PlayerState.FALLING){
			textureType = TextureType.SwanJump;
		}else if(state == PlayerState.DOUBLEJUMPING){
			textureType = TextureType.SwanHightJump;
		}else if(state == PlayerState.WALLGRABING){
			textureType = TextureType.SwanWallClimbing;
		}else if(state == PlayerState.ATTACKING){
			textureType = TextureType.SwanHightJump;		
		}
		super.update(dt);
	}
	
	@Override
	protected void updateJumping(Vector2 speed, float dt) {
		//if (!botContactList.isEmpty())
		if (acc.y > 0 && nbJump < 2) {
			nbJump++;
			float speedChangeY = (float) (Math.sqrt(2 * ConfigManager.gravity
					* ConfigManager.jumpHeight) - speed.y);
			float impulseY = body.getMass() * speedChangeY;
			body.applyLinearImpulse(new Vector2(0, impulseY),
					body.getWorldCenter(), true);			
		} else if (acc.y < 0) {
			if (speed.y > 0) {
				body.setLinearVelocity(speed.x, 0);			
			}
		} 
		
		acc.y = 0;
	}
	
	
	@Override
	protected void updateState() {
		Vector2 speed = body.getLinearVelocity();
		
		if (speed.y > 0) {
			if(nbJump == 2)
				state = PlayerState.DOUBLEJUMPING;
			else
				state = PlayerState.JUMPING;
		} else if (botContactList.isEmpty()) {
			state = PlayerState.FALLING;
		} else if (speed.x != 0) {
			state = PlayerState.RUNNING;
			nbJump = 0;
		} else {
			state = PlayerState.STANDING;
			nbJump = 0;
		}
	}
}
