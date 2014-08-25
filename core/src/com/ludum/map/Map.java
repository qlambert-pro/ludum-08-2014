package com.ludum.map;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.OrthographicCamera;
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

	private static final String LIGHT_COLLISION_LAYER_NAME = "lightWorld";
	private static final String DARK_COLLISION_LAYER_NAME = "darkWorld";
	private static final String SPAWNSWAN_LAYER_NAME = "spawnSwan";
	private static final String SPAWNJUPITER_LAYER_NAME = "spawnJupiter";
	private static final String SPAWNSEAL_LAYER_NAME = "spawnSeal";
	private static final String END1_LAYER_NAME = "end1";
	private static final String END2_LAYER_NAME = "end2";
	private static final String END3_LAYER_NAME = "end3";
	private static final String SPIKE_LAYER_NAME = "spike";
	private static final String LIGHTWATER_LAYER_NAME = "lightWater";
	private static final String DARKWATER_LAYER_NAME = "darkWater";

	private String mapName;
	private WorldState state;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private Collection<Edge> edges;
	private Vector2 spawnSwan;
	private Vector2 spawnJupiter;
	private Vector2 spawnSeal;
	private Vector2 size;
	private int endNumber;

	public Map(String file, WorldState s) {
		mapName = file;
		state = s;
		endNumber = 0;
	}

	public void load() {
		tiledMap = new TmxMapLoader().load(mapName);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		edges = new LinkedList<Edge>();
		;

		size = new Vector2(getLayer(LIGHT_COLLISION_LAYER_NAME).getWidth()
				* ConfigManager.minBlockSize, getLayer(
				LIGHT_COLLISION_LAYER_NAME).getHeight()
				* ConfigManager.minBlockSize);

		addCollisionEdges(getLayer(LIGHT_COLLISION_LAYER_NAME),
				PhysicsObjectType.LIGHTEDGE);
		addCollisionEdges(getLayer(DARK_COLLISION_LAYER_NAME),
				PhysicsObjectType.DARKEDGE);
		TiledMapTileLayer layer;

		layer = getLayer(END1_LAYER_NAME);
		if (layer != null) {
			addTriggerZones(layer, PhysicsObjectType.END1);
			endNumber++;
		}

		layer = getLayer(END2_LAYER_NAME);
		if (layer != null) {
			addTriggerZones(layer, PhysicsObjectType.END2);
			endNumber++;
		}

		layer = getLayer(END3_LAYER_NAME);
		if (layer != null) {
			addTriggerZones(layer, PhysicsObjectType.END3);
			endNumber++;
		}

		layer = getLayer(SPIKE_LAYER_NAME);
		if (layer != null) {
			addTriggerZones(layer, PhysicsObjectType.SPIKE);
		}

		layer = getLayer(LIGHTWATER_LAYER_NAME);
		if (layer != null) {
			addCollisionEdges(layer, PhysicsObjectType.LIGHTWATER);
		}

		layer = getLayer(DARKWATER_LAYER_NAME);
		if (layer != null) {
			addCollisionEdges(layer, PhysicsObjectType.DARKWATER);
		}

		spawnSwan = initSpawn(SPAWNSWAN_LAYER_NAME);
		spawnJupiter = initSpawn(SPAWNJUPITER_LAYER_NAME);
		spawnSeal = initSpawn(SPAWNSEAL_LAYER_NAME);
		changeWorld();
	}

	public void render(OrthographicCamera cam) {
		tiledMapRenderer.setView(cam);
		tiledMapRenderer.render();
	}

	public void changeWorld() {
		getLayer(LIGHT_COLLISION_LAYER_NAME).setVisible(
				state.getState() == WorldType.LIGHT);
		getLayer(DARK_COLLISION_LAYER_NAME).setVisible(
				state.getState() == WorldType.DARK);
		if (getLayer(LIGHTWATER_LAYER_NAME) != null)
			getLayer(LIGHTWATER_LAYER_NAME).setVisible(
					state.getState() == WorldType.LIGHT);
		if (getLayer(DARKWATER_LAYER_NAME) != null)
			getLayer(DARKWATER_LAYER_NAME).setVisible(
					state.getState() == WorldType.DARK);
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

	private Vector2 initSpawn(String layerName) {
		TiledMapTileLayer layer = getLayer(layerName);
		if (layer == null)
			return null;

		int width = layer.getWidth();
		int height = layer.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (layer.getCell(x, y) != null) {
					Vector2 spawn = new Vector2(x * ConfigManager.minBlockSize,
							y * ConfigManager.minBlockSize);
					return spawn;
				}
			}
		}
		return null;
	}

	private void addTriggerZones(TiledMapTileLayer layer, PhysicsObjectType type) {
		int width = layer.getWidth();
		int height = layer.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (layer.getCell(x, y) != null)
					createTrigger(x, y, 1, 1, type);
			}
		}
	}

	private void addCollisionEdges(TiledMapTileLayer layer,
			PhysicsObjectType type) {
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
			PhysicsObjectType type) {
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
			PhysicsObjectType type) {
		Vector2 beg = new Vector2(x1, y1);
		Vector2 end = new Vector2(x2, y2);
		Edge edge = new Edge(beg, end, type);
		edges.add(edge);
	}

	private void createTrigger(int x, int y, int i, int j,
			PhysicsObjectType type) {
		Vector2 pos = new Vector2(x, y);
		Vector2 size = new Vector2(i, j);
		Trigger trigger = new Trigger(pos, size, type);
	}

	private boolean shouldIAddEdge(int x, int y, TiledMapTileLayer layer) {
		Cell otherCell;
		if (x < 0 || x >= layer.getWidth() || y < 0 || y >= layer.getHeight()) {
			return true;
		}
		otherCell = layer.getCell(x, y);
		return otherCell == null;

	}
}
