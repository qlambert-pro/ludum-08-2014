package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;
import com.ludum.skill.Dash;
import com.ludum.skill.LeftDash;
import com.ludum.skill.RightDash;


public class Jupiter extends Player{
	private boolean isUsed = false;
	private long dashTimer = 0;
	
	public Jupiter(Vector2 spawn, Vector2 mapSize, TextureRegion port, WorldState s) {
		super(spawn, mapSize, port, s);
		height = ConfigManager.jupiterHeight;
		physicsSize = ConfigManager.jupiterPhysicsSize;
	}
	
	@Override
	public void updatePhysics(float dt) {
		Vector2 speed = body.getLinearVelocity();

		if (state == PlayerState.DASHING)
			updateDashing(speed.x, dt);
		else
			updateRunning(speed.x, dt);
		
		updateJumping(speed, dt);
		updateState();
		
		checkDeath();
	}
	
	protected void updateDashing(float horizontalSpeed, float dt) {

		
		if (((Dash) s1).isDashing() && dashTimer < ConfigManager.dashLengthMS) {
			s1.use();
		} else if (((Dash) s2).isDashing() && dashTimer < ConfigManager.dashLengthMS) {
			s2.use();
		}
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
		}
		if(state == PlayerState.FALLING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
					textureType, 1000);
		}
		
		dashTimer += dt*1000;
	}
	
	@Override
	public void init() {
		super.init();
		s1 = new LeftDash(body);
		s2 = new RightDash(body);
	}
	@Override
	public void useSkill1(){
		if (!isUsed) {
			super.useSkill1();
			isUsed = true;
			state = PlayerState.DASHING;
			dashTimer = 0;
			body.setLinearDamping(0);
		}
	}
	
	@Override
	public void useSkill2(){
		if (!isUsed) {
			super.useSkill2();
			isUsed = true;			
			state = PlayerState.DASHING;
			dashTimer = 0;
			body.setLinearDamping(0);
		}
	}
	
	@Override
	public void BeginContactHandler(PhysicsDataStructure struct, Contact contact) {
		super.BeginContactHandler(struct, contact);
		
		switch (struct.type) {
		case PLAYER:
			if (((Dash) s1).isDashing()) {
				((Dash) s1).endDash();
				/*TODO transfer dash*/
				((Player) struct.obj).body.applyLinearImpulse(new Vector2(-ConfigManager.bumpX,
					  	ConfigManager.bumpY),
						body.getWorldCenter(), true);
			} else if (((Dash) s2).isDashing()) {
				((Dash) s2).endDash();
				/*TODO transfer dash*/
				((Player) struct.obj).body.applyLinearImpulse(new Vector2(ConfigManager.bumpX,
																		  	ConfigManager.bumpY),
																			body.getWorldCenter(), true);
			}
		default:;				
		}		
	}
	
	protected void updateState() {
		Vector2 speed = body.getLinearVelocity();
		if(state != PlayerState.DASHING)			
			if (speed.y > 0) {
				state = PlayerState.JUMPING;
			} else if (speed.y < 0) {
				state = PlayerState.FALLING;
			} else if (moveRight ^ moveLeft) {
				state = PlayerState.RUNNING;
			} else {
				state = PlayerState.STANDING;
				isUsed = false;
			}
		else if (dashTimer >= ConfigManager.dashSpeed) {
			state = PlayerState.FALLING;
			((Dash) s1).endDash();
			((Dash) s2).endDash();
			body.setGravityScale(1);
		}
	}
	
}
