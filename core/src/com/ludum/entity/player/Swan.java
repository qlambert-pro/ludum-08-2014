package com.ludum.entity.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureType;
import com.ludum.sound.SoundManager;


public class Swan extends Player{
	int nbJump = 0;
	public Swan(Vector2 spawn, Vector2 mapSize, Texture port, Texture port2, WorldState s) {
		super(spawn, mapSize, port,port2, s);
		height = ConfigManager.swanHeight;
		physicsSize = ConfigManager.swanPhysicsSize;
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
	protected void updateJumping(float dt) {
		Vector2 speed = body.getLinearVelocity();
		//if (!botContactList.isEmpty())
		if (jumpState == PlayerJumpState.JUMP && nbJump < 2) {
			SoundManager.getInstance().jump();
			nbJump++;
			float speedChangeY = (float) (Math.sqrt(2 * ConfigManager.gravity
					* ConfigManager.jumpHeight) - speed.y);
			float impulseY = body.getMass() * speedChangeY;
			body.applyLinearImpulse(new Vector2(0, impulseY),
					body.getWorldCenter(), true);
		} else if (jumpState == PlayerJumpState.STOPJUMP) {
			if (speed.y > 0) {
				body.setLinearVelocity(speed.x, 0);			
			}
		} 
		
		jumpState = PlayerJumpState.NONE;
	}
	
	
	@Override
	protected void updateState() {
		if ((s1 == null || !s1.isActive()) &&
			(s2 == null || !s2.isActive()) &&
			(dashLeft  == null || !dashLeft.isActive()) &&
			(dashRight == null || !dashRight.isActive())) {
			
			Vector2 speed = body.getLinearVelocity();

			if (speed.y > 0) {
				if(nbJump == 2)
					state = PlayerState.DOUBLEJUMPING;
				else
					state = PlayerState.JUMPING;
			} else if (botContactList.isEmpty()) {
				if (state == PlayerState.RUNNING ||
					state == PlayerState.STANDING)
					nbJump = 1;
				state = PlayerState.FALLING;
			} else if (moveRight ^ moveLeft) {
				state = PlayerState.RUNNING;
				nbJump = 0;
			} else {
				state = PlayerState.STANDING;
				nbJump = 0;
			}
		}
	}
}
