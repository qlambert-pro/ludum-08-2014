package com.ludum.physics;

import com.ludum.map.TileWorldType;

public class PhysicsDataStructure {
	public PhysicsObject obj;
	public PhysicsObjectType type;
	public TileWorldType world;
	
	public PhysicsDataStructure(PhysicsObject o, PhysicsObjectType t, TileWorldType w) {
		obj = o;
		type = t;
		world = w;
	}

}
