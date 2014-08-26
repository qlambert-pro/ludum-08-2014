package com.ludum.map;

import java.util.ArrayList;

import com.ludum.physics.PhysicsManager;

public class MapLoader {
	private static MapLoader singleton;
	
	private ArrayList<String> mapNameList;
	private int currentMapIndex;

	static public MapLoader getLoader() {
		if(singleton == null)
			singleton = new MapLoader();
		
		return singleton;
	}
	
	private MapLoader() {
		mapNameList = new ArrayList<String>();
		mapNameList.add("basicMap.tmx");
		mapNameList.add("testMapJ2HD.tmx");
		mapNameList.add("testMapJ1HD.tmx");

		
		currentMapIndex = 0;
	}
	
	public boolean isLastMap() {
		return !(currentMapIndex < mapNameList.size());
	}
	
	public Map getNextMap(WorldState state) {
		if(isLastMap())
			return null;
		

		
		/* Load new map */
		Map map = new Map(mapNameList.get(currentMapIndex), state);
		map.load();
		currentMapIndex++;
		return map;
	}
}
