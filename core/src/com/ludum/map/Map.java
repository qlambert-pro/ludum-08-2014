package com.ludum.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ludum.configuration.ConfigManager;
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.physics.PhysicsManager;
import com.ludum.physics.PhysicsObject;
import com.ludum.physics.PhysicsObjectType;

public class Map extends ApplicationAdapter {
	
	private static final String LIGHT_COLLISION_LAYER_NAME = "lightWorld";
	private static final String DARK_COLLISION_LAYER_NAME = "darkWorld";
	private static final String SPAWN1_LAYER_NAME = "spawn1";
	private static final String SPAWN2_LAYER_NAME = "spawn2";

	private TiledMap tiledMap;
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;
	private Collection<Edge> brightEdges;
	private Collection<Edge> darkEdges;
	private List<Vector2> spawnList;
	
	enum WorldType {
		LIGHT, DARK;
	}

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
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

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	public void changeWorld() {
		String layerDark = "foregroundDark";
		String layerWhite = "foregroungWhite";
		if (getLayer(layerDark).isVisible()) {
			getLayer(layerDark).setVisible(false);
			getLayer(layerWhite).setVisible(true);
		} else {
			getLayer(layerDark).setVisible(true);
			getLayer(layerWhite).setVisible(false);
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
