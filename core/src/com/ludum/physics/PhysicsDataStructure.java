package com.ludum.physics;

public class PhysicsDataStructure {
	public PhysicsObject obj;
	public PhysicsObjectType type;

	public PhysicsDataStructure(PhysicsObject o, PhysicsObjectType t) {
		obj = o;
		type = t;
	}

}
