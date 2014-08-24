package com.ludum.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.physics.PhysicsManager;
import com.ludum.physics.PhysicsObject;
import com.ludum.physics.PhysicsObjectType;

public class Trigger implements PhysicsObject {

	private Body body;
	
	public Trigger(Vector2 pos, Vector2 size, PhysicsObjectType type) {
		PhysicsDataStructure pds = new PhysicsDataStructure(this, type);
		body = PhysicsManager.getInstance().createTrigger(pos, size, pds);
	}

	@Override
	public void BeginContactHandler(PhysicsDataStructure struct, Contact contact) {
		// :D	
	}

	@Override
	public void EndContactHandler(PhysicsDataStructure struct, Contact contact) {
		// :D	
	}

	@Override
	public void PreContactHandler(PhysicsDataStructure b, Contact contact) {
		// :D
	}
	
}