package com.ludum.skill;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ludum.configuration.ConfigManager;

public class SoloDash extends Skill {
	protected Body body;
	
	public SoloDash(Body b) {
		body = b;				
	}
	
	@Override
	public void use() {
		body.applyLinearImpulse(new Vector2(ConfigManager.dashX, 0),
				body.getWorldCenter(), true);
	}

}
