package com.ludum.skill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.ludum.entity.player.PlayerState;

public class Levitate extends Skill {
	private boolean isLevitating = false;
	
	public Levitate(Body b) {
		super(b);
	}
	
	@Override
	public PlayerState use() {
		PlayerState newState = PlayerState.FALLING;
		if (!isLevitating) {
			newState = PlayerState.FREEZING;
			body.setLinearVelocity(0, 0);
			body.setGravityScale(0);
			body.setType(BodyType.StaticBody);
			isLevitating = true;			
		} else {
			body.setGravityScale(1);
			body.setType(BodyType.DynamicBody);
			body.setLinearVelocity(new Vector2(0, 0));
			isLevitating = false;
		}
			
		return newState;
	}

	@Override
	public boolean isActive() {
		return isLevitating;
	}

	@Override
	public void update(float dt) {

	}

}
