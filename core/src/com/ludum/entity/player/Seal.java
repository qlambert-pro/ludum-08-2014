package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;

public class Seal extends Player{
	
	private boolean levitate;
	private float gravitySave;
	private Vector2 savePos;

	public Seal(Vector2 spawn, Vector2 mapSize, TextureRegion port, WorldState s) {
		super(spawn, mapSize, port, s);
		height = ConfigManager.sealHeight;
		physicsSize = ConfigManager.sealPhysicsSize;
	}
	
	@Override
	public void update(float dt){
		if(state == PlayerState.JUMPING || state == PlayerState.FALLING){
			textureType = TextureType.SealJump;
		}else if(state == PlayerState.RUNNING){
			textureType = TextureType.SealRun;
		}else if(state == PlayerState.STANDING){
			textureType = TextureType.SealIdle;
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
	
	@Override
	public void useSkill1() {
		levitate = !levitate;
		if(levitate){
			body.setLinearVelocity(0, 0);
			savePos = body.getPosition();
			gravitySave = body.getGravityScale();
			body.setGravityScale(0);
			body.setType(BodyType.StaticBody);
		}else{
			body.setGravityScale(gravitySave);
			body.setType(BodyType.DynamicBody);
		}
	}
	
	@Override
	public void updatePhysics(float dt){
		if(!levitate){
			super.updatePhysics(dt);
		}else{
		}
	}
}
