package com.ludum.skill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ludum.configuration.ConfigManager;



public class RightDash extends Dash {

	public RightDash(Body b) {
		super(b);
	}

	@Override
	protected Vector2 getSpeedVector() {
		return new Vector2(ConfigManager.dashSpeed, 0);
	}

}
