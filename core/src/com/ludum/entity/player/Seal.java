package com.ludum.entity.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;

public class Seal extends Player {

	private float gravitySave;
	private Vector2 savePos;
	private PlayerState saveState;

	public Seal(Vector2 spawn, Vector2 mapSize, Texture port, Texture port2, WorldState s) {
		super(spawn, mapSize, port,port2, s);
		height = ConfigManager.sealHeight;
		physicsSize = ConfigManager.sealPhysicsSize;
		canWalkOnWater = true;
	}

	@Override
	public void update(float dt) {
		if (state == PlayerState.JUMPING || state == PlayerState.FALLING) {
			textureType = TextureType.SealJump;
		} else if (state == PlayerState.RUNNING) {
			textureType = TextureType.SealRun;
		} else if (state == PlayerState.STANDING) {
			textureType = TextureType.SealIdle;
		} else if (state == PlayerState.FREEZING) {
			textureType = TextureType.SealLevitation;
		}
		super.update(dt);
		if (state == PlayerState.JUMPING) {
			currentFrame = TextureManager.getInstance().getTextureRegion(
				textureType, 0);
		} else if(state == PlayerState.FALLING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
					textureType, 100);
		}		
	}

	@Override
	public void useSkill1() {

		if (state != PlayerState.FREEZING) {
			saveState = state;
			state = PlayerState.FREEZING;
			body.setLinearVelocity(0, 0);
			savePos = body.getPosition();
			gravitySave = body.getGravityScale();
			body.setGravityScale(0);
			body.setType(BodyType.StaticBody);
			botContactList.clear();
			dashTimer=ConfigManager.dashLengthMS;
		} else {
			state = saveState;
			body.setGravityScale(gravitySave);
			body.setType(BodyType.DynamicBody);
		}
	}

	@Override
	public void updatePhysics(float dt) {
		if (state != PlayerState.FREEZING) {
			super.updatePhysics(dt);
		} else {
		}
	}

	@Override
	public void draw(Batch batch) {
		if (state != PlayerState.FREEZING) {
			float sizeX = height * currentFrame.getRegionWidth()
					/ currentFrame.getRegionHeight();
			batch.draw(currentFrame, pos.x - sizeX / 2, pos.y - height / 2,
					sizeX, height);
		} else {
			float sizeX = ConfigManager.sealLevitHeight * currentFrame.getRegionWidth()
					/ currentFrame.getRegionHeight();
			batch.draw(currentFrame, pos.x - sizeX / 2, pos.y - ConfigManager.sealLevitHeight / 2,
					sizeX, ConfigManager.sealLevitHeight);
		}
	}
}
