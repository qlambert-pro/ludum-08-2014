package com.ludum.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ludum.configuration.ConfigManager;

public class PhysicsManager {
	/* Physics parameter */
	static private int velocityIterations = 8;
	static private int positionIterations = 3;
	static public final float WORLD_TO_BOX = 1f / ConfigManager.minBlockSize;
	static public final float BOX_TO_WORLD = 1f / WORLD_TO_BOX;
	static PhysicsManager singleton;

	/* Attributs */
	private World world;
	private float updateCount;

	static PhysicsManager getInstance() {
		if (singleton == null)
			singleton = new PhysicsManager();
		return singleton;
	}

	public PhysicsManager() {
		world = new World(new Vector2(0, ConfigManager.gravity), true);
		world.setContactListener(new PhysicsContactListener());
		updateCount = 0;
	}

	public void update(float dt) {
		updateCount += dt;
		while (updateCount > ConfigManager.physicsStepSize) {
			world.step(ConfigManager.physicsStepSize, velocityIterations,
					positionIterations);
			updateCount -= ConfigManager.physicsStepSize;
		}
	}

	public void clear() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body b : bodies)
			world.destroyBody(b);
	}

}
