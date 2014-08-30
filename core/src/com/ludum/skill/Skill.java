package com.ludum.skill;

import com.ludum.entity.player.PlayerState;

public abstract class Skill {
	public abstract PlayerState use();
	public abstract boolean isActive();
	public abstract void update(float dt);
}
