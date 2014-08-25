package com.ludum.skill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Dash extends Skill {
	protected Body body;
	private boolean isDashing = false;
	
	public Dash(Body b) {
		body = b; 
	}
	
	@Override
	public void use() {
		isDashing = true;
		body.setLinearVelocity(getSpeedVector());			
	}

	public boolean isDashing() {
		return isDashing;
	}
	
	public void endDash() {
		isDashing = false;
	}
	
	protected abstract Vector2 getSpeedVector();
}
