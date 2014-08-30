package com.ludum.entity.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;
import com.ludum.skill.Levitate;

public class Seal extends Player {

	public Seal(Vector2 spawn, Vector2 mapSize, Texture port, Texture port2, WorldState s) {
		super(spawn, mapSize, port,port2, s);
		height = ConfigManager.sealHeight;
		physicsSize = ConfigManager.sealPhysicsSize;
		canWalkOnWater = true;		
	}
	@Override
	public void init() {
		super.init();
		
		s1 = new Levitate(body);
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
