package com.ludum.skill;

import com.badlogic.gdx.physics.box2d.Body;
import com.ludum.entity.player.PlayerState;

public abstract class Skill {
	protected Body body;
	
	public Skill(Body b) {
		body = b;
	}
	public abstract PlayerState use();
	public abstract boolean isActive();
	public abstract void update(float dt);
}
