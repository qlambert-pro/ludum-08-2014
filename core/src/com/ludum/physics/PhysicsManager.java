package com.ludum.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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

	public static PhysicsManager getInstance() {
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
	
	public Body createDynamicRectangle(Vector2 pos, Vector2 size, PhysicsDataStructure s)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(WORLD_TO_BOX * pos.x, WORLD_TO_BOX * pos.y);
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		Body b = world.createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(WORLD_TO_BOX * size.x/2, WORLD_TO_BOX * size.y/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.0f;
		
		b.createFixture(fixtureDef);	    
		b.setUserData(s);
		return b;
	}

}
