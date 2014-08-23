package com.ludum.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

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

public class Map extends ApplicationAdapter {

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		tiledMap = new TmxMapLoader().load("stupidMap.tmx");
		changeWorld();
		ArrayList<Rectangle> test = getCollisionNormalWorld();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

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
		if (getLayers(layerDark).isVisible()) {
			getLayers(layerDark).setVisible(false);
			getLayers(layerWhite).setVisible(true);
		} else {
			getLayers(layerDark).setVisible(true);
			getLayers(layerWhite).setVisible(false);

		}
	}

	private MapLayer getLayers(String layerName) {
		return tiledMap.getLayers().get(layerName);

	}

	private ArrayList<Rectangle> getCollision(String layerName) {
		MapLayer collision = getLayers(layerName);
		MapLayer collisionNormal = getLayers("coli");
		ArrayList<Rectangle> rectangleList = new ArrayList<Rectangle>();

		for (int i = 0; i < collision.getObjects().getCount(); i++) {

			MapObjects MapObjects = collision.getObjects();
			rectangleList.add(((RectangleMapObject) MapObjects.get(i))
					.getRectangle());
		}

		for (int j = 0; j < collisionNormal.getObjects().getCount(); j++) {

			MapObjects MapObjects = collision.getObjects();
			rectangleList.add(((RectangleMapObject) MapObjects.get(j))
					.getRectangle());
		}

		return rectangleList;

	}

	public ArrayList<Rectangle> getCollisionNormalWorld() {
		return getCollision("coliNormal");
	}

	public ArrayList<Rectangle> getCollisionDarkWorld() {
		return getCollision("coliDark");

	}

	public void addCollisionEdges(TiledMapTileLayer layer) {
		int width = layer.getWidth();
		int height = layer.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				extendCell(x, y, layer);
			}
		}
	}

	private void extendCell(int x, int y, TiledMapTileLayer layer) {
		// Left
		if (shouldIAddEdge(x - 1, y, layer))
			createEdge(x, y, x, y + 1);

		// Right
		if (shouldIAddEdge(x + 1, y, layer))
			createEdge(x + 1, y, x + 1, y + 1);

		// Top
		if (shouldIAddEdge(x, y + 1, layer))
			createEdge(x, y + 1, x + 1, y + 1);

		// Bottom
		if (shouldIAddEdge(x, y - 1, layer))
			createEdge(x, y, x + 1, y);
	}
	
	private void createEdge(int x, int y, int i, int y2) {
		// TODO: Create the edge. :)
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
