package com.ludum.skill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ludum.configuration.ConfigManager;
import com.ludum.entity.player.PlayerState;


public abstract class Dash extends Skill {
	private boolean isDashing = false;
	private float dashTimer = 0;
	
	public Dash(Body b) {
		super(b);
	}
	
	@Override
	public PlayerState use() {
		isDashing = true;
		dashTimer = 0;
		body.setGravityScale(0);
		body.setLinearVelocity(getSpeedVector());
		
		return PlayerState.DASHING;
	}

	public boolean isActive() {
		return isDashing;
	}
	
	public void endDash() {
		isDashing = false;
		body.setGravityScale(1);
		body.setLinearVelocity(new Vector2(0, 0));
	}
	
	public void update(float dt) {
		if (isDashing) {
			dashTimer += dt*1000;
			if (dashTimer >= ConfigManager.dashLengthMS)
				endDash();
		}
	}
	
	protected abstract Vector2 getSpeedVector();
}
