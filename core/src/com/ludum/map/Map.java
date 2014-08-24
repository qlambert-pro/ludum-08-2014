package com.ludum.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;

public class Map {
	
	private static final String LIGHT_COLLISION_LAYER_NAME = "lightWorld";
	private static final String DARK_COLLISION_LAYER_NAME = "darkWorld";
	private static final String SPAWN1_LAYER_NAME = "spawn1";
	private static final String SPAWN2_LAYER_NAME = "spawn2";

	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private Collection<Edge> brightEdges;
	private Collection<Edge> darkEdges;
	private List<Vector2> spawnList;
	
	enum WorldType {
		LIGHT, DARK;
	}

	public void load() {
		tiledMap = new TmxMapLoader().load("testMap.tmx");
		// changeWorld();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		brightEdges = new LinkedList<Edge>();
		darkEdges = new LinkedList<Edge>();
		addCollisionEdges(getLayer(LIGHT_COLLISION_LAYER_NAME), Map.WorldType.LIGHT);
		addCollisionEdges(getLayer(DARK_COLLISION_LAYER_NAME), Map.WorldType.DARK);
	
		spawnList = new ArrayList<Vector2>();
		initSpawn(SPAWN1_LAYER_NAME);
		initSpawn(SPAWN2_LAYER_NAME);
	}

	public void render(OrthographicCamera cam) {
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
	}

	public void changeWorld() {
		if (getLayer(LIGHT_COLLISION_LAYER_NAME).isVisible()) {
			getLayer(LIGHT_COLLISION_LAYER_NAME).setVisible(false);
			getLayer(DARK_COLLISION_LAYER_NAME).setVisible(true);
		} else {
			getLayer(LIGHT_COLLISION_LAYER_NAME).setVisible(true);
			getLayer(DARK_COLLISION_LAYER_NAME).setVisible(false);
		}
	}

	private TiledMapTileLayer getLayer(String layerName) {
		return (TiledMapTileLayer) tiledMap.getLayers().get(layerName);

	}

	public Vector2 getSpawn(int id) {
		return spawnList.get(id);
	}
	
	private void initSpawn(String layerName) {
		TiledMapTileLayer layer = getLayer(layerName);
		int width = layer.getWidth();
		int height = layer.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (layer.getCell(x, y) != null) {
					Vector2 spawn =  new Vector2(x * ConfigManager.minBlockSize,
												 y * ConfigManager.minBlockSize);
					spawnList.add(spawn);
				}
			}
		}
	}
	
	private void addCollisionEdges(TiledMapTileLayer layer,
			Map.WorldType type) {
		int width = layer.getWidth();
		int height = layer.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (layer.getCell(x, y) != null)
					extendCell(x, y, layer, type);
			}
		}
	}

	private void extendCell(int x, int y, TiledMapTileLayer layer,
			Map.WorldType type) {
		// Left
		if (shouldIAddEdge(x - 1, y, layer))
			createEdge(x, y, x, y + 1, type);

		// Right
		if (shouldIAddEdge(x + 1, y, layer))
			createEdge(x + 1, y, x + 1, y + 1, type);

		// Top
		if (shouldIAddEdge(x, y + 1, layer))
			createEdge(x, y + 1, x + 1, y + 1, type);

		// Bottom
		if (shouldIAddEdge(x, y - 1, layer))
			createEdge(x, y, x + 1, y, type);
	}
	
	private void createEdge(int x1, int y1, int x2, int y2,
			Map.WorldType type) {
		Vector2 beg = new Vector2(x1, y1);
		Vector2 end = new Vector2(x2, y2);
		Edge edge = new Edge(beg, end);
		if (type == Map.WorldType.LIGHT) {
			brightEdges.add(edge);	
		} else {
			darkEdges.add(edge);
		}
	}

	private boolean shouldIAddEdge(int x, int y, TiledMapTileLayer layer) {
		Cell otherCell;
		if (x < 0 || x >= layer.getWidth()
			|| y < 0 || y >= layer.getHeight()) {
			return true;
		}
		otherCell = layer.getCell(x, y);
		return otherCell == null;
		
	}
}
