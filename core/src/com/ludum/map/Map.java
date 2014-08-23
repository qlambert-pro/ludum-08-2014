package com.ludum.map;




import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;





public class Map extends ApplicationAdapter {

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,w,h);
		camera.update();
		tiledMap = new TmxMapLoader().load("stupidMap.tmx");
		changeWorld();
		ArrayList<Rectangle> test=  getCollisionNormalWorld();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}


	public void changeWorld(){
		String layerDark="foregroundDarK";
		String layerWhite="foregroundWhite";
		if(getLayers(layerDark).isVisible()){
			getLayers(layerDark).setVisible(false);
			getLayers(layerWhite).setVisible(true);
		}else{
			getLayers(layerDark).setVisible(true);
			getLayers(layerWhite).setVisible(false);

		}
	}

	private MapLayer getLayers(String layerName){
		return tiledMap.getLayers().get(layerName);

	}

	private ArrayList<Rectangle> getCollision(String layerName){
		MapLayer collision = getLayers(layerName);
		MapLayer collisionNormal = getLayers("coli");
		ArrayList<Rectangle> rectangleList=new ArrayList<Rectangle>();

		for (int i=0;i<collision.getObjects().getCount();i++){

			MapObjects MapObjects=collision.getObjects();
			rectangleList.add(((RectangleMapObject)MapObjects.get(i)).getRectangle());
		}

		for (int j=0;j<collisionNormal.getObjects().getCount();j++){

			MapObjects MapObjects=collision.getObjects();
			rectangleList.add(((RectangleMapObject)MapObjects.get(j)).getRectangle());
		}

		return rectangleList;

	}

	public ArrayList<Rectangle> getCollisionNormalWorld(){
		return getCollision("coliNormal");
	}



	public ArrayList<Rectangle> getCollisionDarkWorld(){
		return getCollision("coliDark");

	}



}


