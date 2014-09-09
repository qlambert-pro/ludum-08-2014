package com.ludum.map;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;
import com.ludum.physics.PhysicsObjectType;

public class Map {

	private static final String LIGHT_LAYER_NAME = "lightWorld";
	private static final String DARK_LAYER_NAME = "darkWorld";


	private String mapName;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private Collection<Edge> edges;
	private Vector2 spawnSwan;
	private Vector2 spawnJupiter;
	private Vector2 spawnSeal;
	private Vector2 size;
	private int endNumber;

	public Map(String file) {
		mapName = file;
		endNumber = 0;
	}

	public void load() {
		tiledMap = new TmxMapLoader().load(mapName);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		edges = new LinkedList<Edge>();
		

		size = new Vector2(getLayer(LIGHT_LAYER_NAME).getWidth()
				* ConfigManager.minBlockSize, getLayer(
				LIGHT_LAYER_NAME).getHeight()
				* ConfigManager.minBlockSize);

		TiledMapTileLayer darkLayer  = getLayer(DARK_LAYER_NAME);
		TiledMapTileLayer lightLayer = getLayer(LIGHT_LAYER_NAME);
		
		for (int x = 0; x < darkLayer.getWidth(); x++) {
			for (int y = 0; y < darkLayer.getHeight(); y++) {
				Cell darkCell  = darkLayer.getCell(x, y);
				Cell lightCell = lightLayer.getCell(x, y);
						
				String darkType = null;
				String lightType = null;
				
				MapProperties darkProperties= null; 
				if (darkCell != null) {
					darkProperties  = darkCell.getTile().getProperties();
					if (darkProperties != null)
						darkType = (String) darkProperties.get(TilePropertieConstants.PROPERTY_NAME_TYPE);
				}
				
				MapProperties lightProperties = null;
				if (lightCell != null) {
					lightProperties = lightCell.getTile().getProperties();
					if (lightProperties != null)
						lightType = (String) lightProperties.get(TilePropertieConstants.PROPERTY_NAME_TYPE);
				}

				
				if (darkType != null || lightType != null) {
					if (darkType != null && darkType.equals(lightType))						
						addTile(x, y, getTypeFromString(darkType), darkLayer, TileWorldType.BOTH, darkProperties);
					else {
						if (darkType != null)
							addTile(x, y, getTypeFromString(darkType), darkLayer, TileWorldType.DARK, darkProperties);
						
						if (lightType != null)
							addTile(x, y, getTypeFromString(lightType), lightLayer, TileWorldType.LIGHT, lightProperties);
					}
				}
			}
		}
		
		setLightWorld();
	}
	
	private void addTile(int x, int y, TileType type, TiledMapTileLayer layer, TileWorldType world, MapProperties properties) {
		switch (type) {
		case SOLID:
			createTrigger(x, y, 1, 1, type, world);
			extendCell(x, y, layer, type, world);
			break;
		case END:
			endNumber++;
			createTrigger(x, y, 1, 1, type, world);
			break;
		case SPIKE:
			createTrigger(x, y, 1, 1, type, world);
			break;
		case WATER:
			createTrigger(x, y, 1, 1, type, world);
			extendCell(x, y, layer, type, world);
			break;
		case SPAWN:
			Vector2 spawn = new Vector2(x * ConfigManager.minBlockSize, y * ConfigManager.minBlockSize);
			String character = (String) properties.get(TilePropertieConstants.PROPERTY_NAME_CHARACTER);
			if (character != null && character.equals(TilePropertieConstants.PROPERTY_VALUE_CHARACTER_JUPITER))
				spawnJupiter = spawn;
			else if (character != null && character.equals(TilePropertieConstants.PROPERTY_VALUE_CHARACTER_SEAL))
				spawnSeal = spawn;
			else if (character != null && character.equals(TilePropertieConstants.PROPERTY_VALUE_CHARACTER_SWAN))
				spawnSwan = spawn;
			break;
		default:
			break;
		}
		
	}

	public void render(OrthographicCamera cam) {
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
	}

	public void setLightWorld() {
		getLayer(LIGHT_LAYER_NAME).setVisible(true);
		getLayer(DARK_LAYER_NAME).setVisible(false);
	}
	
	public void setDarkWorld() {
		getLayer(LIGHT_LAYER_NAME).setVisible(false);
		getLayer(DARK_LAYER_NAME).setVisible(true);
	}

	private TiledMapTileLayer getLayer(String layerName) {
		return (TiledMapTileLayer) tiledMap.getLayers().get(layerName);
	}

	public Vector2 getSpawnSwan() {
		return spawnSwan;
	}

	public Vector2 getSpawnJupiter() {
		return spawnJupiter;
	}

	public Vector2 getSpawnSeal() {
		return spawnSeal;
	}

	public Vector2 getSize() {
		return size;
	}

	public int getEndNumber() {
		return endNumber;
	}

	private void extendCell(int x, int y, TiledMapTileLayer layer,
			TileType type, TileWorldType world) {
		// Left
		if (shouldIAddEdge(x - 1, y, type, layer))
			createEdge(x, y, x, y + 1, type, world);

		// Right
		if (shouldIAddEdge(x + 1, y, type, layer))
			createEdge(x + 1, y, x + 1, y + 1, type, world);

		// Top
		if (shouldIAddEdge(x, y + 1, type, layer))
			createEdge(x, y + 1, x + 1, y + 1, type, world);

		// Bottom
		if (shouldIAddEdge(x, y - 1, type, layer))
			createEdge(x, y, x + 1, y, type, world);
	}

	private void createEdge(int x1, int y1, int x2, int y2,
			TileType type, TileWorldType world) {
		Vector2 beg = new Vector2(x1, y1);
		Vector2 end = new Vector2(x2, y2);
		Edge edge = new Edge(beg, end, getPhysicTypeFromTileType(type), world);
		edges.add(edge);
	}

	private void createTrigger(int x, int y, int i, int j,
			TileType type, TileWorldType world) {
		Vector2 pos = new Vector2(x, y);
		Vector2 size = new Vector2(i, j);
		new Trigger(pos, size, getPhysicTypeFromTileType(type), world);
	}

	private boolean shouldIAddEdge(int x, int y, TileType type, TiledMapTileLayer layer) {
		Cell otherCell;
		if (x < 0 || x >= layer.getWidth() || y < 0 || y >= layer.getHeight()) {
			return true;
		}
		otherCell = layer.getCell(x, y);		
				
		String otherType = null;
		
		if (otherCell != null) {
			MapProperties otherProperties  = otherCell.getTile().getProperties();
			if (otherProperties != null)
				otherType = (String) otherProperties.get(TilePropertieConstants.PROPERTY_NAME_TYPE);
		}						
		
		return type != getTypeFromString(otherType);
	}
	
	private TileType getTypeFromString(String s) {
		if (s == null)
			return null;
		
		TileType type = null;
		
		if (s.equals(TilePropertieConstants.PROPERTY_VALUE_TYPE_SPIKES))
			type = TileType.SPIKE;
		else if (s.equals(TilePropertieConstants.PROPERTY_VALUE_TYPE_WATER))
			type = TileType.WATER;
		else if (s.equals(TilePropertieConstants.PROPERTY_VALUE_TYPE_SOLID))
			type = TileType.SOLID;
		else if (s.equals(TilePropertieConstants.PROPERTY_VALUE_TYPE_END))
			type = TileType.END;
		else if (s.equals(TilePropertieConstants.PROPERTY_VALUE_TYPE_SPAWN))
			type = TileType.SPAWN;
		else if (s.equals(TilePropertieConstants.PROPERTY_VALUE_TYPE_NONE))
			type = TileType.NONE;
		
		return type;
	}
	
	private PhysicsObjectType getPhysicTypeFromTileType(TileType t){
		PhysicsObjectType type = null;
		
		switch(t) {
		case SOLID:
			type = PhysicsObjectType.SOLID;
			break;
		case END:
			if (endNumber == 1)
				type = PhysicsObjectType.END1;
			else if (endNumber == 2)
				type = PhysicsObjectType.END2;
			else if (endNumber == 3)
				type = PhysicsObjectType.END3;
			break;
		case SPIKE:
			type = PhysicsObjectType.SPIKE;
			break;
		case WATER:
			type = PhysicsObjectType.WATER;
			break;
		default:
			break;
		}
		
		return type;
	}
}
