package com.ludum.map;




import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;





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
		tiledMap.getLayers().get(3).setVisible(false);
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
		if(tiledMap.getLayers().get(3).isVisible()){
			tiledMap.getLayers().get(3).setVisible(false);
		}else{
			tiledMap.getLayers().get(3).setVisible(true);

		}
	}

}


