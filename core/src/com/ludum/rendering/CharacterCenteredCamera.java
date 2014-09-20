package com.ludum.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;
import com.ludum.entity.player.Player;
import com.ludum.map.Map;

public class CharacterCenteredCamera extends OrthographicCamera{
	private Player player;
	private Map map;

	public CharacterCenteredCamera(Map m, Player p) {
		super(ConfigManager.camWidth  * ConfigManager.minBlockSize,
			  ConfigManager.camHeight * ConfigManager.minBlockSize);
		this.player = p;
		this.map = m;
		//position.set(player.getPosition(), 5);
		computeCameraPosition();
		update();
	}

	public void changeCharacter(Player p) {
		this.player = p;
	}

	public void follow() {							
		position.set(computeCameraPosition(), 5);
		update();
	}
	
	private Vector2 computeCameraPosition() {
		Vector2 newPosition = player.getPosition().cpy();
		
		Vector2 marginSize = new Vector2(ConfigManager.camWidth  * ConfigManager.minBlockSize/2,
										 ConfigManager.camHeight * ConfigManager.minBlockSize/2 );
		
		if ( newPosition.x <  marginSize.x )
			newPosition.x = marginSize.x;
		else if  ( newPosition.x > map.getSize().x - marginSize.x )
			newPosition.x = map.getSize().x - marginSize.x;
		
		if ( newPosition.y < marginSize.y )
			newPosition.y = marginSize.y;
		else if  ( newPosition.y > map.getSize().y - marginSize.y )
			newPosition.y = map.getSize().y - marginSize.y;
		
		
		return newPosition;
	}
}
